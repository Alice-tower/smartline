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
 * GroupEntity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/2 21:15
 */
@Table(database = AppDatabase.class)
public class GroupEntity extends BaseEntity<GroupEntity> {
    /**
     * 接受消息且提示 (默认类型)
     */
    public static final int NOTIFY_TYPE_DEFAULT = 0;

    /**
     * 接收消息但不提示
     */
    public static final int NOTIFY_TYPE_CLOSE = 1;

    /**
     * 不接收消息
     */
    public static final int NOTIFY_TYPE_NONE = 2;

    // Id
    @PrimaryKey
    private String id;

    // 群名称
    @Column
    private String name;

    // 群描述
    @Column
    private String description;

    // 群头像
    @Column
    private String portrait;

    // 群主对应的用户外键
    @ForeignKey(tableClass = UserEntity.class, stubbedRelationship = true)
    private UserEntity owner;

    // 当前用户的消息提醒模式
    @Column
    private int notifyType;

    // 创建时间
    @Column
    private Date createAt;

    // 更新时间
    @Column
    private Date updateAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupEntity that = (GroupEntity) o;
        if (notifyType != that.notifyType) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (portrait != null ? !portrait.equals(that.portrait) : that.portrait != null)
            return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null)
            return false;
        return updateAt != null ? updateAt.equals(that.updateAt) : that.updateAt == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean isSame(GroupEntity old) {
        // 是否指向同一数据 主要关注 Id
        return this == old || Objects.equals(id, old.id);
    }

    @Override
    public boolean isUiContentSame(GroupEntity old) {
        // 显示的内容是否一致 主要关注 群名称，群描述，群头像
        return this == old || (Objects.equals(this.name, old.name)
                && Objects.equals(this.description, old.description)
                && Objects.equals(this.portrait, old.portrait));
    }
}
