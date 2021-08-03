package com.tower.smartline.factory.model.db;

import com.tower.smartline.factory.model.db.base.AppDatabase;
import com.tower.smartline.factory.model.db.base.BaseEntity;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;
import java.util.Objects;

/**
 * MessageEntity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/3 3:33
 */
@Table(database = AppDatabase.class)
public class MessageEntity extends BaseEntity<MessageEntity> {
    /**
     * 文本类型消息
     */
    public static final int TYPE_TEXT = 1;

    /**
     * 图片类型消息
     */
    public static final int TYPE_IMAGE = 2;

    /**
     * 语音类型消息
     */
    public static final int TYPE_AUDIO = 3;

    /**
     * 文件类型消息
     */
    public static final int TYPE_FILE = 4;

    // Id
    @PrimaryKey
    private String id;

    // 消息内容
    @Column
    private String content;

    // 附件
    @Column
    private String attachment;

    // 消息类型
    @Column
    private int type;

    // 消息发送者外键
    @ForeignKey(tableClass = UserEntity.class, stubbedRelationship = true)
    private UserEntity sender;

    // 消息接收人外键
    @ForeignKey(tableClass = UserEntity.class, stubbedRelationship = true)
    private UserEntity receiver;

    // 消息接收群外键
    @ForeignKey(tableClass = GroupEntity.class, stubbedRelationship = true)
    private GroupEntity group;

    // 创建时间
    @Column
    private Date createAt;

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

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
        this.receiver = receiver;
    }

    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        if (type != that.type) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (attachment != null ? !attachment.equals(that.attachment) : that.attachment != null)
            return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null)
            return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        return createAt != null ? createAt.equals(that.createAt) : that.createAt == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean isSame(MessageEntity old) {
        // 是否指向同一数据 主要关注 Id
        return this == old || Objects.equals(id, old.id);
    }

    @Override
    public boolean isUiContentSame(MessageEntity old) {
        // 显示的内容是否一致 消息设计上目前不可更改 直接返回True
        return true;
    }
}
