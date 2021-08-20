package com.tower.smartline.push.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.tower.smartline.common.app.BaseActivity;
import com.tower.smartline.factory.model.IUserInfo;
import com.tower.smartline.push.databinding.ActivityMessageBinding;
import com.tower.smartline.push.frags.message.ChatGroupFragment;
import com.tower.smartline.push.frags.message.ChatUserFragment;

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

    // 接受者Id
    private String mReceiverId;

    // 是否为群聊天
    private boolean mIsGroup;

    // 当前的Fragment
    private Fragment mCurFragment;

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

    @Override
    protected boolean initArgs(Bundle bundle) {
        mReceiverId = bundle.getString(KEY_RECEIVER_ID);
        mIsGroup = bundle.getBoolean(KEY_RECEIVER_IS_GROUP);
        return !TextUtils.isEmpty(mReceiverId) && super.initArgs(bundle);
    }

    @NonNull
    @Override
    protected View initBinding() {
        mBinding = ActivityMessageBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mCurFragment = mIsGroup
                ? new ChatGroupFragment()
                : new ChatUserFragment();

        // 从Activity传递参数到Fragment中去
        Bundle bundle = new Bundle();
        bundle.putString(KEY_RECEIVER_ID, mReceiverId);
        mCurFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(mBinding.layRoot.getId(), mCurFragment)
                .commit();
    }
}