package com.tower.smartline.factory.model.response;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.model.db.GroupEntity;
import com.tower.smartline.factory.model.db.UserEntity;

import java.util.Date;

/**
 * GroupCard
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/12 16:08
 */
public class GroupCard {
    // Id
    private String id;

    // 群名称
    private String name;

    // 群描述
    private String description;

    // 群头像
    private String portrait;

    // 群主Id
    private String ownerId;

    // 当前用户的消息提醒模式
    private int notifyType;

    // 创建时间
    private Date createAt;

    // 更新时间
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    /**
     * 将当前GroupCard内数据转化为GroupEntity
     *
     * @param owner 该群群主
     * @return GroupEntity
     */
    @NonNull
    public GroupEntity toGroupEntity(UserEntity owner) {
        GroupEntity group = new GroupEntity();
        group.setId(id);
        group.setName(name);
        group.setDescription(description);
        group.setPortrait(portrait);
        group.setNotifyType(notifyType);
        group.setCreateAt(createAt);
        group.setUpdateAt(updateAt);
        group.setOwner(owner);
        return group;
    }

    @NonNull
    @Override
    public String toString() {
        return "GroupCard{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", portrait='" + portrait + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", notifyType=" + notifyType +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
