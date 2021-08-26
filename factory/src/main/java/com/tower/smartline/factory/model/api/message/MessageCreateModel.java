package com.tower.smartline.factory.model.api.message;

import com.tower.smartline.factory.model.db.MessageEntity;
import com.tower.smartline.factory.model.response.MessageCard;
import com.tower.smartline.factory.persistence.Account;

import java.util.Date;
import java.util.UUID;

/**
 * 消息创建Model
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/26 21:36
 */
public class MessageCreateModel {
    /**
     * 对用户发送的消息
     */
    public static final int RECEIVER_TYPE_USER = 1;

    /**
     * 对群组发送的消息
     */
    public static final int RECEIVER_TYPE_GROUP = 2;

    // Id 由发送客户端生成
    private String id;

    // 消息内容
    private String content;

    // 附件
    private String attachment;

    // 消息类型
    private int type = MessageEntity.TYPE_TEXT;

    // 消息接收者Id
    private String receiverId;

    // 接受者类型
    private int receiverType = RECEIVER_TYPE_USER;

    private MessageCreateModel() {
        // 随机生成一个UUID作为Id
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAttachment() {
        return attachment;
    }

    public int getType() {
        return type;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public int getReceiverType() {
        return receiverType;
    }

    /**
     * 将当前MessageCreateModel内数据转化为MessageCard
     *
     * @return MessageCard
     */
    public MessageCard toMessageCard() {
        MessageCard card = new MessageCard();
        card.setId(id);
        card.setContent(content);
        card.setAttachment(attachment);
        card.setType(type);
        card.setSenderId(Account.getUserId());
        if (receiverType == RECEIVER_TYPE_GROUP) {
            card.setGroupId(receiverId);
        } else {
            card.setReceiverId(receiverId);
        }
        card.setState(MessageEntity.STATE_SENDING); // 转化出的Card为发送中状态
        card.setCreateAt(new Date());
        return card;
    }

    /**
     * 建造者模式，快速创建一个消息
     */
    public static class Builder {
        private MessageCreateModel mModel;

        public Builder() {
            this.mModel = new MessageCreateModel();
        }

        /**
         * 设置接收者/接收群
         *
         * @param receiverId   目标Id
         * @param receiverType 接收类型
         * @return Builder
         */
        public Builder receiver(String receiverId, int receiverType) {
            this.mModel.receiverId = receiverId;
            this.mModel.receiverType = receiverType;
            return this;
        }

        /**
         * 设置消息内容
         *
         * @param content 消息内容
         * @param type    消息类型
         * @return Builder
         */
        public Builder content(String content, int type) {
            this.mModel.content = content;
            this.mModel.type = type;
            return this;
        }

        /**
         * 设置附件
         *
         * @param attachment 附件
         * @return Builder
         */
        public Builder attachment(String attachment) {
            this.mModel.attachment = attachment;
            return this;
        }

        /**
         * 返回设置好属性的消息创建Model
         *
         * @return MessageCreateModel
         */
        public MessageCreateModel build() {
            return this.mModel;
        }
    }
}
