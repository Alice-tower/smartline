package com.tower.smartline.factory.model.response;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.model.db.GroupEntity;
import com.tower.smartline.factory.model.db.GroupMemberEntity;
import com.tower.smartline.factory.model.db.UserEntity;

import java.util.Date;

/**
 * GroupMemberCard
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/2 21:42
 */
public class GroupMemberCard {
    // Id
    private String id;

    // 群名片
    private String nickName;

    // 成员权限
    private int permission;

    // 对应的用户Id
    private String userId;

    // 对应的群Id
    private String groupId;

    // 入群时间 (即群成员实体的创建时间)
    private Date createAt;

    // 更新时间
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }


    /**
     * 将当前GroupMemberCard内数据转化为GroupMemberEntity
     *
     * @param group 该群成员对应的群
     * @param user  该群成员对应的用户
     * @return GroupMemberEntity
     */
    @NonNull
    public GroupMemberEntity toGroupMemberEntity(GroupEntity group, UserEntity user) {
        GroupMemberEntity member = new GroupMemberEntity();
        member.setId(this.id);
        member.setNickName(this.nickName);
        member.setPermission(this.permission);
        member.setCreateAt(this.createAt);
        member.setUpdateAt(this.updateAt);
        member.setGroup(group);
        member.setUser(user);
        return member;
    }

    @NonNull
    @Override
    public String toString() {
        return "GroupMemberCard{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", permission=" + permission +
                ", userId='" + userId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
