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
 * GroupMemberEntity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/2 21:41
 */
@Table(database = AppDatabase.class)
public class GroupMemberEntity extends BaseEntity<GroupMemberEntity> {
    /**
     * 普通成员权限 (默认权限)
     */
    public static final int PERMISSION_DEFAULT = 0;

    /**
     * 管理员权限
     */
    public static final int PERMISSION_MANAGER = 1;

    /**
     * 创建者权限
     */
    public static final int PERMISSION_OWNER = 2;

    // Id
    @PrimaryKey
    private String id;

    // 群名片
    @Column
    private String nickName;

    // 成员权限
    @Column
    private int permission;

    // 群成员对应的用户外键
    @ForeignKey(tableClass = UserEntity.class, stubbedRelationship = true)
    private UserEntity user;

    // 所在群对应的群外键
    @ForeignKey(tableClass = GroupEntity.class, stubbedRelationship = true)
    private GroupEntity group;

    // 入群时间 (即群成员实体的创建时间)
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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
        GroupMemberEntity that = (GroupMemberEntity) o;
        if (permission != that.permission) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null)
            return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null)
            return false;
        return updateAt != null ? updateAt.equals(that.updateAt) : that.updateAt == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean isSame(GroupMemberEntity old) {
        // 是否指向同一数据 主要关注 Id
        return this == old || Objects.equals(id, old.id);
    }

    @Override
    public boolean isUiContentSame(GroupMemberEntity old) {
        // 显示的内容是否一致 主要关注 群名片，成员权限
        return this == old || (Objects.equals(this.nickName, old.nickName)
                && this.permission == old.permission);
    }
}
