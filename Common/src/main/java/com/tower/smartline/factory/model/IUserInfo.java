package com.tower.smartline.factory.model;

import java.util.Date;

/**
 * 获取用户信息的基础接口
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/25 7:01
 */
public interface IUserInfo {
    String getId();

    String getName();

    String getPortrait();

    String getDescription();

    int getSex();

    boolean isFollow();

    int getFollowingNum();

    int getFollowersNum();

    Date getUpdateAt();
}
