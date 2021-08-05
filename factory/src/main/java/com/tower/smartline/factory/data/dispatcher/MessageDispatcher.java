package com.tower.smartline.factory.data.dispatcher;

import android.text.TextUtils;

import com.tower.smartline.factory.data.helper.GroupHelper;
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

                // TODO 发消息时，应当先存储消息到数据库，再进行网络请求
                //  首次存储和服务器返回都在此逻辑中加工过滤
                //  考虑对比消息本地和服务器的创建时间或增加新的状态值

                UserEntity sender = UserHelper.infoFirstOfLocal(card.getSenderId());
                UserEntity receiver = UserHelper.infoFirstOfLocal(card.getReceiverId());
                GroupEntity group = GroupHelper.infoFirstOfLocal(card.getGroupId());
                messages.add(card.toMessageEntity(sender, receiver, group));
            }
            if (messages.size() > 0) {
                // 进行数据库存储并通知
                DbPortal.save(MessageEntity.class, messages.toArray(new MessageEntity[0]));
            }
        }
    }
}
