package com.tower.smartline.factory.model.api.message;

import com.tower.smartline.factory.model.db.MessageEntity;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }
}
