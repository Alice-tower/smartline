package com.tower.smartline.factory.model.response;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.model.db.GroupEntity;
import com.tower.smartline.factory.model.db.MessageEntity;
import com.tower.smartline.factory.model.db.UserEntity;

import java.util.Date;

/**
 * MessageCard
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/3 3:30
 */
public class MessageCard {
    // Id
    private String id;

    // 消息内容
    private String content;

    // 附件
    private String attachment;

    // 消息类型
    private int type;

    // 消息发送者Id
    private String senderId;

    // 消息接收人Id
    private String receiverId;

    // 消息接收群Id
    private String groupId;

    // 创建时间
    private Date createAt;

    // 当前消息状态 (额外本地字段 不会被Gson序列化和反序列化)
    private transient int state = MessageEntity.STATE_DONE;

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

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * 将当前MessageCard内数据转化为MessageEntity
     *
     * @param sender   发送者
     * @param receiver 接收人
     * @param group    接收群
     * @return MessageEntity
     */
    @NonNull
    public MessageEntity toMessageEntity(UserEntity sender, UserEntity receiver, GroupEntity group) {
        MessageEntity message = new MessageEntity();
        message.setId(id);
        message.setContent(content);
        message.setAttachment(attachment);
        message.setType(type);
        message.setCreateAt(createAt);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setGroup(group);
        message.setState(state);
        return message;
    }

    @NonNull
    @Override
    public String toString() {
        return "MessageCard{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", attachment='" + attachment + '\'' +
                ", type=" + type +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
