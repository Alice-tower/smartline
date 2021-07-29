package com.tower.smartline.push.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.tower.smartline.common.app.ToolbarActivity;
import com.tower.smartline.push.R;
import com.tower.smartline.push.databinding.ActivityPersonalBinding;

import com.bumptech.glide.Glide;

/**
 * PersonalActivity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/29 13:30
 */
public class PersonalActivity extends ToolbarActivity
        implements View.OnClickListener {
    private static final String TAG = PersonalActivity.class.getName();

    private static final String KEY_USER_ID = "KEY_USER_ID";

    private ActivityPersonalBinding mBinding;

    private String mUserId;

    /**
     * 个人信息Activity拉起入口
     *
     * @param context 上下文
     * @param userId  目标用户Id
     */
    public static void show(Context context, String userId) {
        if (context == null || TextUtils.isEmpty(userId)) {
            return;
        }
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra(KEY_USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (bundle != null) {
            mUserId = bundle.getString(KEY_USER_ID);
            if (!TextUtils.isEmpty(mUserId)) {
                Log.i(TAG, "initArgs: userId: " + mUserId);
                return super.initArgs(bundle);
            }
        }
        return false;
    }

    @NonNull
    @Override
    protected View initBinding() {
        mBinding = ActivityPersonalBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        // 去除Toolbar默认Title显示
        hideToolbarTitle();

        // 头部背景图初始化
        Glide.with(this)
                .asBitmap()
                .load(R.drawable.bg_src_night)
                .centerCrop()
                .into(mBinding.imHeader);

        // 点击监听初始化
        mBinding.btnFollowOrChat.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        // TODO 转Presenter
    }

    private void onFollowOrChatClick() {
        Log.i(TAG, "onFollowOrChatClick");
        // TODO 三种点击事件
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        int id = v.getId();
        if (id == mBinding.btnFollowOrChat.getId()) {
            // 下方多功能按键点击
            onFollowOrChatClick();
        } else {
            Log.w(TAG, "onClick: illegal param: " + id);
        }
    }
}
