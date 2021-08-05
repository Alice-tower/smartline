package com.tower.smartline.factory.model.db;

import com.tower.smartline.factory.data.db.AppDatabase;
import com.tower.smartline.factory.model.db.base.BaseEntity;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;
import java.util.Objects;

/**
 * SessionEntity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/3 3:58
 */
@Table(database = AppDatabase.class)
public class SessionEntity extends BaseEntity<SessionEntity> {
    /**
     * 消息接收者类型 用户
     */
    public static final int RECEIVER_TYPE_USER = 0;

    /**
     * 消息接受者类型 群组
     */
    public static final int RECEIVER_TYPE_GROUP = 1;

    // Id 规定为消息的接收人Id或接收群Id
    @PrimaryKey
    private String id;

    // 消息接收者类型 对应人或群
    @Column
    private int receiverType = RECEIVER_TYPE_USER;

    // 用户名或群名
    @Column
    private String name;

    // 用户头像或群头像
    @Column
    private String portrait;

    // 消息内容简化版
    @Column
    private String content;

    // 未读消息数量
    @Column
    private int unReadCount;

    // 对应的消息外键
    @ForeignKey(tableClass = MessageEntity.class)
    private MessageEntity message;

    // 修改时间
    @Column
    private Date updateAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionEntity that = (SessionEntity) o;
        if (receiverType != that.receiverType) return false;
        if (unReadCount != that.unReadCount) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (portrait != null ? !portrait.equals(that.portrait) : that.portrait != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (updateAt != null ? !updateAt.equals(that.updateAt) : that.updateAt != null)
            return false;
        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + receiverType;
        return result;
    }

    @Override
    public boolean isSame(SessionEntity old) {
        // 是否指向同一数据 主要关注 Id，接收者类型
        return this == old || (Objects.equals(id, old.id)
                && receiverType == old.receiverType);
    }

    @Override
    public boolean isUiContentSame(SessionEntity old) {
        // 显示的内容是否一致 主要关注 群名称，群头像，最新消息，未读消息数量
        return this == old || (Objects.equals(this.name, old.name)
                && Objects.equals(this.portrait, old.portrait)
                && Objects.equals(this.content, old.content)
                && unReadCount == old.unReadCount);
    }
}
