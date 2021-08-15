package com.tower.smartline.factory.data.dispatcher;

import android.util.Log;

import com.tower.smartline.factory.Factory;
import com.tower.smartline.factory.model.response.GroupCard;
import com.tower.smartline.factory.model.response.GroupMemberCard;
import com.tower.smartline.factory.model.response.MessageCard;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.model.response.base.PushCode;
import com.tower.smartline.factory.model.response.base.PushList;
import com.tower.smartline.factory.model.response.base.PushModel;
import com.tower.smartline.factory.persistence.Account;

/**
 * DataCenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/4 19:15
 */
public class DataCenter {
    private static final String TAG = DataCenter.class.getName();

    private DataCenter() {
    }

    /**
     * 推送分发
     *
     * @param msg Json透传消息
     */
    public static void dispatchPush(String msg) {
        if (!Account.isLogin()) {
            // 未登录则不处理推送
            return;
        }
        PushList pushList = PushList.decode(msg);
        if (pushList == null) {
            return;
        }
        for (PushModel pushModel : pushList.getPushModels()) {
            Log.i(TAG, "dispatchPush: " + pushModel);
            switch (pushModel.getType()) {
                case PushCode.PUSH_TYPE_LOGOUT: // 退出登录
                    // TODO 退出登录
                    return;
                case PushCode.PUSH_TYPE_MESSAGE: // 收到消息
                    MessageCard messageCard = Factory.getGson().fromJson(pushModel.getContent(), MessageCard.class);
                    dispatchMessage(messageCard);
                    break;
                case PushCode.PUSH_TYPE_ADD_USER: // 添加新好友
                    UserCard userCard = Factory.getGson().fromJson(pushModel.getContent(), UserCard.class);
                    dispatchUser(userCard);
                    break;
                case PushCode.PUSH_TYPE_ADD_GROUP: // 添加新群组
                    GroupCard groupCard = Factory.getGson().fromJson(pushModel.getContent(), GroupCard.class);
                    dispatchGroup(groupCard);
                    break;
                case PushCode.PUSH_TYPE_ADD_GROUP_MEMBERS: // 添加新群成员
                case PushCode.PUSH_TYPE_UPDATE_GROUP_MEMBERS: // 群成员信息更新
                    GroupMemberCard groupMemberCard = Factory.getGson().fromJson(pushModel.getContent(), GroupMemberCard.class);
                    dispatchGroupMember(groupMemberCard);
                    break;
                default:
                    break;
            }
        }
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
