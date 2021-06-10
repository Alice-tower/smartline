package com.tower.smartline.factory.model.db;

import java.util.Date;

/**
 * UserEntity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 5:17
 */
// TODO 数据库
public class UserEntity {
    /**
     * 性别_未知
     */
    public static final int SEX_TYPE_UNKNOWN = 0;

    /**
     * 性别_男
     */
    public static final int SEX_TYPE_MALE = 1;

    /**
     * 性别_女
     */
    public static final int SEX_TYPE_FEMALE = 2;

    // Id
    private String id;

    // 用户名
    private String name;

    // 头像
    private String portrait;

    // 个性签名
    private String description;

    // 性别
    private int sex = SEX_TYPE_UNKNOWN;

    // 关注数
    private int following;

    // 粉丝数
    private int followers;

    // 我对当前User的备注名
    private String nickname;

    // 我是否关注了当前User
    private boolean isFollow;

    // 修改时间
    private Date modifyAt;

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

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public Date getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
