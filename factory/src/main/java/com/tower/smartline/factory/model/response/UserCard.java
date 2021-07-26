package com.tower.smartline.factory.model.response;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.model.IUserInfo;
import com.tower.smartline.factory.model.db.UserEntity;

import java.util.Date;

/**
 * UserCard
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 6:41
 */
public class UserCard implements IUserInfo {
    // Id
    private String id;

    // 用户名
    private String name;

    // 头像
    private String portrait;

    // 个性签名
    private String description;

    // 性别
    private int sex;

    // 是否已关注该用户
    private boolean isFollow;

    // 关注数
    private int followingNum;

    // 粉丝数
    private int followersNum;

    // 最后更新用户信息时间
    private Date updateAt;

    /**
     * 将当前UserCard内数据转化为UserEntity
     *
     * @return UserEntity
     */
    @NonNull
    public UserEntity toUserEntity() {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setName(name);
        user.setPortrait(portrait);
        user.setDescription(description);
        user.setSex(sex);
        user.setFollow(isFollow);
        user.setFollowersNum(followersNum);
        user.setFollowingNum(followingNum);
        user.setUpdateAt(updateAt);

        // UserEntity内nickname未被填充
        // user.setNickname(null);

        return user;
    }

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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public int getFollowingNum() {
        return followingNum;
    }

    public void setFollowingNum(int followingNum) {
        this.followingNum = followingNum;
    }

    public int getFollowersNum() {
        return followersNum;
    }

    public void setFollowersNum(int followersNum) {
        this.followersNum = followersNum;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserCard{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", portrait='" + portrait + '\'' +
                ", description='" + description + '\'' +
                ", sex=" + sex +
                ", updateAt=" + updateAt +
                ", followingNum=" + followingNum +
                ", followersNum=" + followersNum +
                ", isFollow=" + isFollow +
                '}';
    }
}
