package com.tower.smartline.push.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.tower.smartline.common.app.MyApplication;
import com.tower.smartline.common.app.PresenterToolbarActivity;
import com.tower.smartline.factory.model.IUserInfo;
import com.tower.smartline.factory.presenter.user.IPersonalInfoContract;
import com.tower.smartline.factory.presenter.user.PersonalInfoPresenter;
import com.tower.smartline.push.R;
import com.tower.smartline.push.databinding.ActivityPersonalBinding;

import com.bumptech.glide.Glide;

/**
 * PersonalActivity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/29 13:30
 */
public class PersonalActivity extends PresenterToolbarActivity<IPersonalInfoContract.Presenter>
        implements IPersonalInfoContract.View, View.OnClickListener {
    private static final String TAG = PersonalActivity.class.getName();

    private static final String KEY_USER_ID = "KEY_USER_ID";

    private static final String KEY_FROM_MESSAGE = "KEY_FROM_MESSAGE";

    private ActivityPersonalBinding mBinding;

    private String mUserId;

    private IUserInfo mUser;

    // 与该用户的关系类型 (主要决定底部按钮)
    private int mType;

    // 是否从消息页面拉起
    private boolean mIsShowFromMessage = false;

    /**
     * 个人信息Activity拉起入口 (非消息页面拉起)
     *
     * @param context 上下文
     * @param userId  目标用户Id
     */
    public static void show(Context context, String userId) {
        show(context, userId, false);
    }

    /**
     * 个人信息Activity拉起入口
     *
     * @param context           上下文
     * @param userId            目标用户Id
     * @param isShowFromMessage 是否从消息页面拉起
     */
    public static void show(Context context, String userId, boolean isShowFromMessage) {
        if (context == null || TextUtils.isEmpty(userId)) {
            return;
        }
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra(KEY_USER_ID, userId);
        intent.putExtra(KEY_FROM_MESSAGE, isShowFromMessage);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (bundle != null) {
            mUserId = bundle.getString(KEY_USER_ID);
            mIsShowFromMessage = bundle.getBoolean(KEY_FROM_MESSAGE);
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
    public IPersonalInfoContract.Presenter initPresenter() {
        return new PersonalInfoPresenter(this);
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

        // 设置空布局
        setEmptyView(mBinding.empty);

        // 点击监听初始化
        mBinding.btnBottom.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        getPresenter().loadPersonalInfo(mUserId);
    }

    @Override
    public void showError(int str) {
        super.showError(str);
        if (mEmptyView == null) {
            hideLoading();
        }
    }

    @Override
    public void showLoading() {
        super.showLoading();
        if (mEmptyView == null) {
            mBinding.btnBottom.setClickable(false);
            mBinding.loading.start();
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        if (mEmptyView == null) {
            mBinding.loading.stop();
            mBinding.btnBottom.setClickable(true);
        }
    }

    @Override
    public void loadSuccess(IUserInfo user, int type) {
        mBinding.txtFollowing.setText(String.format(
                getString(R.string.label_personal_info_following), user.getFollowingNum()));
        mBinding.txtFollowers.setText(String.format(
                getString(R.string.label_personal_info_followers), user.getFollowersNum()));
        if (mUser == null) {
            // 部分数据仅首次初始化时刷新
            mBinding.txtName.setText(user.getName());
            mBinding.txtDesc.setText(user.getDescription());
            mBinding.imPortrait.setup(Glide.with(this), user);
            mUser = user;
        }

        // 设置底部按钮状态
        setBottomButtonState(type);

        hideLoading();
    }

    private void setBottomButtonState(int type) {
        mType = type;
        Log.i(TAG, "setBottomButtonState: type: " + mType);
        switch (mType) {
            case IPersonalInfoContract.TYPE_SELF:
                mBinding.btnBottom.setText(R.string.label_personal_info_edit);
                break;
            case IPersonalInfoContract.TYPE_FOLLOW:
                mBinding.btnBottom.setText(R.string.label_personal_info_follow);
                break;
            case IPersonalInfoContract.TYPE_MESSAGE:
                mBinding.btnBottom.setText(R.string.label_personal_info_message);
                break;
            default:
                break;
        }
        mBinding.btnBottom.setVisibility(View.VISIBLE);
    }

    private void onBottomClick() {
        Log.i(TAG, "onFollowOrChatClick: type: " + mType);
        switch (mType) {
            case IPersonalInfoContract.TYPE_SELF:
                // TODO UserActivity改造 拉起编辑个人信息界面
                MyApplication.showToast(R.string.hello_fragment);
                break;
            case IPersonalInfoContract.TYPE_FOLLOW:
                // 清除空布局
                setEmptyView(null);
                getPresenter().follow(mUserId);
                break;
            case IPersonalInfoContract.TYPE_MESSAGE:
                if (mIsShowFromMessage) {
                    // 如果从消息页面拉起直接关闭该页面
                    finish();
                } else {
                    MessageActivity.show(this, mUser);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        int id = v.getId();
        if (id == mBinding.btnBottom.getId()) {
            // 下方多功能按键点击
            onBottomClick();
        } else {
            Log.w(TAG, "onClick: illegal param: " + id);
        }
    }
}
