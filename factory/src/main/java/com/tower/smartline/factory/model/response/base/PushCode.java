package com.tower.smartline.factory.model.response.base;

/**
 * 推送类型
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/15 14:22
 */
public class PushCode {
    /**
     * 退出登录
     */
    public static final int PUSH_TYPE_LOGOUT = -199;

    /**
     * 收到消息
     */
    public static final int PUSH_TYPE_MESSAGE = 200;

    /**
     * 添加新好友
     */
    public static final int PUSH_TYPE_ADD_USER = 210;

    /**
     * 添加新群组
     */
    public static final int PUSH_TYPE_ADD_GROUP = 220;

    /**
     * 添加新群成员
     */
    public static final int PUSH_TYPE_ADD_GROUP_MEMBERS = 221;

    /**
     * 群成员信息更新
     */
    public static final int PUSH_TYPE_UPDATE_GROUP_MEMBERS = 222;
}
