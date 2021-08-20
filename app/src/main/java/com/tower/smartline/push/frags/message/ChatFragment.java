package com.tower.smartline.push.frags.message;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.tower.smartline.common.app.BaseFragment;
import com.tower.smartline.push.R;
import com.tower.smartline.push.activities.MessageActivity;
import com.tower.smartline.push.databinding.FragmentChatBinding;

import com.google.android.material.appbar.AppBarLayout;

/**
 * ChatFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/19 8:56
 */
public abstract class ChatFragment extends BaseFragment
        implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {
    private static final String TAG = ChatFragment.class.getName();

    protected FragmentChatBinding mBinding;

    protected String mReceiverId;

    private MenuItem mMenuItem;

    private boolean mIsMessageBlank = true;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        mReceiverId = bundle.getString(MessageActivity.KEY_RECEIVER_ID);
    }

    @NonNull
    @Override
    protected View initBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        if (mBinding == null) {
            mBinding = FragmentChatBinding.inflate(inflater, container, false);
        }
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        // Toolbar初始化
        mBinding.toolbar.setNavigationIcon(R.drawable.ic_back);
        mBinding.toolbar.setNavigationOnClickListener(v -> requireActivity().finish());
        mMenuItem = initToolbarMenu(mBinding.toolbar);

        // AppBar初始化
        mBinding.layAppbar.addOnOffsetChangedListener(this);

        // 消息编辑栏初始化
        mBinding.editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String message = s.toString().trim();
                if (mIsMessageBlank == TextUtils.isEmpty(message)) {
                    return;
                }
                mIsMessageBlank = !mIsMessageBlank;
                int imgRes = mIsMessageBlank ? R.drawable.ic_more : R.drawable.ic_send;
                mBinding.btnSubmit.setImageResource(imgRes);
            }
        });

        // 点击监听初始化
        mBinding.imPortrait.setOnClickListener(this);
        mBinding.btnRecord.setOnClickListener(this);
        mBinding.btnEmoji.setOnClickListener(this);
        mBinding.btnSubmit.setOnClickListener(this);
    }


    /**
     * 初始化Toolbar菜单
     *
     * @param toolbar Toolbar
     * @return MenuItem
     */
    @Nullable
    protected abstract MenuItem initToolbarMenu(Toolbar toolbar);

    @Override
    protected void destroyBinding() {
        mBinding = null;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        // TODO
    }

    /**
     * 顶部头像点击事件
     */
    protected abstract void onPortraitClick();

    private void onRecordClick() {
        Log.i(TAG, "onRecordClick");
        // TODO 录音模块
    }

    private void onEmojiClick() {
        Log.i(TAG, "onEmojiClick");
        // TODO QQ表情模块
    }

    private void onSubmitClick() {
        Log.i(TAG, "onSubmitClick: mIsMessageBlank: " + mIsMessageBlank);
        if (mIsMessageBlank) {
            // TODO 更多按钮
        } else {
            // TODO 发送按钮
        }
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        int id = v.getId();
        if (id == mBinding.imPortrait.getId()) {
            // 头像点击
            onPortraitClick();
        } else if (id == mBinding.btnRecord.getId()) {
            // 语音点击
            onRecordClick();
        } else if (id == mBinding.btnEmoji.getId()) {
            // 表情点击
            onEmojiClick();
        } else if (id == mBinding.btnSubmit.getId()) {
            // 更多或发送点击
            onSubmitClick();
        } else {
            Log.w(TAG, "onClick: illegal param: " + id);
        }
    }
}
