package com.tower.smartline.push.activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.tower.smartline.common.app.BaseActivity;
import com.tower.smartline.factory.model.IUserInfo;
import com.tower.smartline.push.databinding.ActivityMessageBinding;

/**
 * 消息Activity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/22 7:00
 */
public class MessageActivity extends BaseActivity {
    /**
     * 接受者Id (用户Id 或 群Id)
     */
    public static final String KEY_RECEIVER_ID = "KEY_RECEIVER_ID";

    // 是否为群聊天
    private static final String KEY_RECEIVER_IS_GROUP = "KEY_RECEIVER_IS_GROUP";

    private ActivityMessageBinding mBinding;

    /**
     * 消息Activity拉起入口 (一对一用户聊天)
     *
     * @param context 上下文
     * @param user    对话的用户信息
     */
    public static void show(Context context, IUserInfo user) {
        if (context == null || user == null || TextUtils.isEmpty(user.getId())) {
            return;
        }
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(KEY_RECEIVER_ID, user.getId());
        intent.putExtra(KEY_RECEIVER_IS_GROUP, false);
        context.startActivity(intent);
    }

    // TODO 通过群列表拉起
    // public static void show() {
    //
    // }

    // TODO 通过近期会话列表拉起
    // public static void show() {
    //
    // }

    @NonNull
    @Override
    protected View initBinding() {
        mBinding = ActivityMessageBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }
}