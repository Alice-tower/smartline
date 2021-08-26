package com.tower.smartline.factory.data.dispatcher;

import android.text.TextUtils;

import com.tower.smartline.factory.data.helper.GroupHelper;
import com.tower.smartline.factory.data.helper.MessageHelper;
import com.tower.smartline.factory.data.helper.UserHelper;
import com.tower.smartline.factory.data.db.DbPortal;
import com.tower.smartline.factory.model.db.GroupEntity;
import com.tower.smartline.factory.model.db.MessageEntity;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.model.response.MessageCard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * MessageDispatcher
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/4 20:08
 */
public class MessageDispatcher {
    private static MessageDispatcher sInstance;

    // 单线程池
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    private MessageDispatcher() {
    }

    /**
     * 获取单例
     *
     * @return MessageDispatcher
     */
    static MessageDispatcher getInstance() {
        if (sInstance == null) {
            synchronized (MessageDispatcher.class) {
                if (sInstance == null)
                    sInstance = new MessageDispatcher();
            }
        }
        return sInstance;
    }

    /**
     * 对接收的数据进行存储和通知
     *
     * @param cards MessageCard数组
     */
    void dispatch(MessageCard... cards) {
        if (cards == null || cards.length == 0) {
            return;
        }
        mExecutor.execute(new MessageCardHandler(cards));
    }

    /**
     * 构建数据库模型
     */
    private static class MessageCardHandler implements Runnable {
        private final MessageCard[] mCards;

        MessageCardHandler(MessageCard[] cards) {
            this.mCards = cards;
        }

        @Override
        public void run() {
            List<MessageEntity> messages = new ArrayList<>();
            for (MessageCard card : mCards) {
                if (card == null || TextUtils.isEmpty(card.getId())
                        || TextUtils.isEmpty(card.getSenderId())
                        || (TextUtils.isEmpty(card.getReceiverId())
                        && TextUtils.isEmpty(card.getGroupId()))) {
                    continue;
                }

                // 发消息流程：客户端创建消息->存储本地->发送至服务端->服务端返回->刷新本地状态
                MessageEntity message = MessageHelper.findFromLocal(card.getId());
                if (message == null) {
                    // 本地无此消息，初次在数据库存储
                    UserEntity sender = UserHelper.infoFirstOfLocal(card.getSenderId());
                    UserEntity receiver = null;
                    GroupEntity group = null;
                    if (!TextUtils.isEmpty(card.getReceiverId())) {
                        receiver = UserHelper.infoFirstOfLocal(card.getReceiverId());
                    } else if (!TextUtils.isEmpty(card.getGroupId())) {
                        group = GroupHelper.infoFirstOfLocal(card.getGroupId());
                    }
                    if (sender == null || (receiver == null && group == null)) {
                        continue;
                    }
                    message = card.toMessageEntity(sender, receiver, group);
                } else {
                    // 本地有此消息，若本地消息不为完成态则刷新该消息状态
                    if (message.getState() == MessageEntity.STATE_DONE) {
                        continue;
                    }

                    // 更新一些可能会变化的内容
                    message.setContent(card.getContent());
                    message.setAttachment(card.getAttachment());
                    message.setState(card.getState());
                    if (card.getState() == MessageEntity.STATE_DONE) {
                        // 消息发送成功时需要修改创建时间为服务器时间
                        message.setCreateAt(card.getCreateAt());
                    }
                }
                messages.add(message);
            }
            if (messages.size() > 0) {
                // 进行数据库存储并通知
                DbPortal.save(MessageEntity.class, messages.toArray(new MessageEntity[0]));
            }
        }
    }
}
