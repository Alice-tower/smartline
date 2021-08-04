package com.tower.smartline.factory.data.Dispatcher;

import com.tower.smartline.factory.model.response.GroupCard;
import com.tower.smartline.factory.model.response.GroupMemberCard;
import com.tower.smartline.factory.model.response.MessageCard;
import com.tower.smartline.factory.model.response.UserCard;

/**
 * DataCenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/4 19:15
 */
public class DataCenter {
    /**
     * 推送分发
     *
     * @param msg 透传消息
     */
    public static void dispatchPush(String msg) {
        // TODO 推送到
    }

    /**
     * 用户数据分发
     *
     * @param cards UserCard数组
     */
    public static void dispatchUser(UserCard... cards) {
        UserDispatcher.getInstance().dispatch(cards);
    }

    /**
     * 群组数据分发
     *
     * @param cards GroupCard数组
     */
    public static void dispatchGroup(GroupCard... cards) {
        GroupDispatcher.getInstance().dispatch(cards);
    }

    /**
     * 群成员数据分发
     *
     * @param cards GroupMemberCard数组
     */
    public static void dispatchGroupMember(GroupMemberCard... cards) {
        GroupDispatcher.getInstance().dispatch(cards);
    }

    /**
     * 消息数据分发
     *
     * @param cards MessageCard数组
     */
    public static void dispatchMessage(MessageCard... cards) {
        MessageDispatcher.getInstance().dispatch(cards);
    }
}
