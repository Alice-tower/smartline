package com.tower.smartline.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * UserEntity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 5:17
 */
@Table(database = AppDatabase.class)
public class UserEntity extends BaseModel {
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
    @PrimaryKey
    private String id;

    // 用户名
    @Column
    private String name;

    // 头像
    @Column
    private String portrait;

    // 个性签名
    @Column
    private String description;

    // 性别
    @Column
    private int sex = SEX_TYPE_UNKNOWN;

    // 关注数
    @Column
    private int following;

    // 粉丝数
    @Column
    private int followers;

    // 我对当前User的备注名
    @Column
    private String nickname;

    // 我是否关注了当前User
    @Column
    private boolean isFollow;

    // 修改时间
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
        UserEntity that = (UserEntity) o;
        if (sex != that.sex) return false;
        if (following != that.following) return false;
        if (followers != that.followers) return false;
        if (isFollow != that.isFollow) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (portrait != null ? !portrait.equals(that.portrait) : that.portrait != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (nickname != null ? !nickname.equals(that.nickname) : that.nickname != null)
            return false;
        return updateAt != null ? updateAt.equals(that.updateAt) : that.updateAt == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
