package com.tower.smartline.push;

import android.content.Context;
import android.util.Log;

import com.tower.smartline.factory.data.dispatcher.DataCenter;
import com.tower.smartline.factory.data.helper.AccountHelper;
import com.tower.smartline.factory.persistence.Account;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

/**
 * 个推消息处理中心
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/24 3:31
 */
public class AppMessageReceiverService extends GTIntentService {
    private static final String TAG = AppMessageReceiverService.class.getName();

    /**
     * 个推进程启动成功回调该函数
     *
     * @param context Context
     * @param pid Push 进程 ID
     */
    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Log.d(TAG, "onReceiveServicePid -> " + pid);
    }

    /**
     * 个推初始化成功回调该函数并返回 cid
     *
     * @param context Context
     * @param pushId 个推唯一 ID，用于标识当前应用
     */
    @Override
    public void onReceiveClientId(Context context, String pushId) {
        Log.d(TAG, "onReceiveClientId -> " + pushId);

        // 设备Id持久化
        Account.setPushId(pushId);
        if (Account.isLogin()) {
            // 账号若为登录状态则绑定一次PushId 不设置回调
            AccountHelper.bindPush(null);
        }
    }

    /**
     * 接收到透传消息后回调该函数
     *
     * @param context Context
     * @param msg 透传消息封装类
     */
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        Log.d(TAG, "onReceiveMessageData -> " +
                "appid = " + msg.getAppid() +
                "\ntaskid = " + msg.getTaskId() +
                "\nmessageid = " + msg.getMessageId() +
                "\npkg = " + msg.getPkgName() +
                "\ncid = " + msg.getClientId()+
                "\nplayload = "+ new String(msg.getPayload()));

        // 透传消息转换为字符串后分发处理
        byte[] payload = msg.getPayload();
        if (payload != null) {
            String message = new String(payload);
            DataCenter.dispatchPush(message);
        }
    }

    /**
     * cid 在线状态变化时回调该函数
     *
     * @param context Context
     * @param online 当前 cid 是否在线，true 代表在线，false 代表离线，若由于网络原因离线，个推 SDK 内部会自动重连
     */
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        Log.d(TAG, "onReceiveOnlineState -> " + online);
    }

    /**
     * 调用设置标签、绑定别名、解绑别名、自定义回执操作的结果返回
     *
     * @param context Context
     * @param cmdMessage 通过cmdMessage.getAction()获取相应的状态值
     */
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        // 当个推Command命名返回时回调
        Log.d(TAG, "onReceiveCommandResult -> " + cmdMessage);

        // action 结果值说明
        // 10009：设置标签的结果回执
        // 10010：绑定别名的结果回执
        // 10011：解绑别名的结果回执
        // 10006：自定义回执的结果回执

        // int action = gtCmdMessage.getAction();
        // if (action == PushConsts.SET_TAG_RESULT) {
        //     setTagResult((SetTagCmdMessage) cmdMessage);
        // } else if (action == PushConsts.BIND_ALIAS_RESULT) {
        //     bindAliasResult((BindAliasCmdMessage) cmdMessage);
        // } else if (action == PushConsts.UNBIND_ALIAS_RESULT) {
        //     unbindAliasResult((UnBindAliasCmdMessage) cmdMessage);
        // } else if ((action == PushConsts.THIRDPART_FEEDBACK)) {
        //     feedbackResult((FeedbackCmdMessage) cmdMessage);
        // }
    }

    /**
     * 通知到达时回调该接口
     *
     * @param context Context
     * @param message 通知回调结果的消息封装类
     */
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage message) {
        // 当通知栏消息达到时回调
        Log.d(TAG, "onNotificationMessageArrived -> "
                + "appid = " + message.getAppid()
                + "\ntaskid = " + message.getTaskId()
                + "\nmessageid = " + message.getMessageId()
                + "\npkg = " + message.getPkgName()
                + "\ncid = " + message.getClientId()
                + "\ncontent = " + message.getContent()
                + "\ntitle = " + message.getTitle());
    }

    /**
     * 通知点击回调接口
     *
     * @param context Context
     * @param message 通知回调结果的消息封装类
     */
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage message) {
        // 当通知栏消息点击时回调
        Log.d(TAG, "onNotificationMessageClicked -> "
                + "appid = " + message.getAppid()
                + "\ntaskid = " + message.getTaskId()
                + "\nmessageid = " + message.getMessageId()
                + "\npkg = " + message.getPkgName()
                + "\ncid = " + message.getClientId()
                + "\ncontent = " + message.getContent()
                + "\ntitle = " + message.getTitle());
    }
}
