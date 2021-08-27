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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.tower.smartline.common.app.PresenterFragment;
import com.tower.smartline.common.widget.PortraitView;
import com.tower.smartline.common.widget.recycler.BaseRecyclerAdapter;
import com.tower.smartline.factory.model.db.MessageEntity;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.persistence.Account;
import com.tower.smartline.factory.presenter.message.IChatContract;
import com.tower.smartline.push.R;
import com.tower.smartline.push.activities.MessageActivity;
import com.tower.smartline.push.activities.PersonalActivity;
import com.tower.smartline.push.databinding.FragmentChatBinding;

import com.google.android.material.appbar.AppBarLayout;

import com.bumptech.glide.Glide;

import net.qiujuer.genius.ui.widget.Loading;

/**
 * ChatFragment
 *
 * @param <ReceiverEntity> 接受者类型 (UserEntity / GroupEntity)
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/19 8:56
 */
public abstract class ChatFragment<ReceiverEntity> extends PresenterFragment<IChatContract.Presenter>
        implements IChatContract.View<ReceiverEntity>, AppBarLayout.OnOffsetChangedListener, View.OnClickListener {
    private static final String TAG = ChatFragment.class.getName();

    protected FragmentChatBinding mBinding;

    protected String mReceiverId;

    private MenuItem mMenuItem;

    private boolean mIsMessageBlank = true;

    private ChatRecyclerAdapter mAdapter;

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
        mBinding.editMessage.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mBinding.layAppbar.setExpanded(false, true);
            }
        });

        // RecyclerView初始化
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        mBinding.recycler.setAdapter(mAdapter = new ChatRecyclerAdapter());

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
    protected void initData() {
        super.initData();
        getPresenter().initReceiverData();
    }

    @Override
    protected void destroyBinding() {
        mBinding = null;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        float totalScrollRange = appBarLayout.getTotalScrollRange(); // 滚动范围
        Log.d(TAG, "onOffsetChanged: totalScrollRange: " + totalScrollRange
                + " verticalOffset: " + verticalOffset);
        float verticalOffsetAbs = Math.abs(verticalOffset); // 垂直偏移绝对值
        if (verticalOffsetAbs == 0) {
            // AppBar完全展开时
            // 头像可见 缩放100% 不透明度100%
            mBinding.imPortrait.setVisibility(View.VISIBLE);
            mBinding.imPortrait.setScaleX(1);
            mBinding.imPortrait.setScaleY(1);
            mBinding.imPortrait.setAlpha((float) 1);
            if (mMenuItem != null) {
                // 隐藏菜单
                mMenuItem.setVisible(false);
            }
        } else if (verticalOffsetAbs >= totalScrollRange) {
            // AppBar完全关闭时
            // 头像不可见
            mBinding.imPortrait.setVisibility(View.INVISIBLE);
            if (mMenuItem != null) {
                // 显示菜单 不透明度100%
                mMenuItem.setVisible(true);
                mMenuItem.getIcon().setAlpha(255);
            }
        } else {
            // AppBar伸缩时
            // 头像可见 调整缩放和不透明度
            float progress = 1 - verticalOffsetAbs / totalScrollRange;
            mBinding.imPortrait.setVisibility(View.VISIBLE);
            mBinding.imPortrait.setScaleX(progress);
            mBinding.imPortrait.setScaleY(progress);
            mBinding.imPortrait.setAlpha(progress);
            if (mMenuItem != null) {
                // 显示菜单 不透明度与头像相反
                mMenuItem.setVisible(true);
                mMenuItem.getIcon().setAlpha(255 - (int) (progress * 255));
            }
        }
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
            // 发送按钮
            String content = mBinding.editMessage.getText().toString();
            mBinding.editMessage.setText("");
            getPresenter().sendText(content);
        }
    }

    @Override
    public BaseRecyclerAdapter<MessageEntity> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
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

    private class ChatRecyclerAdapter extends BaseRecyclerAdapter<MessageEntity> {
        @Override
        protected int getItemViewType(int position, MessageEntity message) {
            if (message == null) {
                return 0;
            }
            boolean isRight = false;
            if (!TextUtils.isEmpty(Account.getUserId()) && message.getSender() != null
                    && Account.getUserId().equals(message.getSender().getId())) {
                isRight = true;
            }
            switch (message.getType()) {
                case MessageEntity.TYPE_TEXT: // 文本类型消息
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
                case MessageEntity.TYPE_IMAGE: // 图片类型消息
                    return isRight ? R.layout.cell_chat_image_right : R.layout.cell_chat_image_left;
                case MessageEntity.TYPE_AUDIO: // 语音类型消息
                    return isRight ? R.layout.cell_chat_audio_right : R.layout.cell_chat_audio_left;
                case MessageEntity.TYPE_FILE: // 文件类型消息
                    return isRight ? R.layout.cell_chat_file_right : R.layout.cell_chat_file_left;
                default:
                    return R.layout.cell_chat_text_left;
            }
        }

        @NonNull
        @Override
        protected BaseRecyclerViewHolder<MessageEntity> onCreateViewHolder(View root, int viewType) {
            switch (viewType) {
                case R.layout.cell_chat_image_right:
                case R.layout.cell_chat_image_left:
                    // TODO 图片类型消息ViewHolder
                case R.layout.cell_chat_audio_right:
                case R.layout.cell_chat_audio_left:
                    // TODO 语音类型消息ViewHolder
                case R.layout.cell_chat_file_right:
                case R.layout.cell_chat_file_left:
                    // TODO 文件类型消息ViewHolder
                default:
                    // 文本类型消息ViewHolder
                    return new TextHolder(root);
            }
        }
    }

    /**
     * 所有类型消息ViewHolder的基类
     */
    abstract class ChatViewHolder extends BaseRecyclerAdapter.BaseRecyclerViewHolder<MessageEntity>
            implements View.OnClickListener {
        private PortraitView mPortrait;

        @Nullable
        private Loading mLoading;

        public ChatViewHolder(View itemView) {
            super(itemView);
            mPortrait = itemView.findViewById(R.id.im_portrait);
            mLoading = itemView.findViewById(R.id.loading);
        }

        @Override
        protected void onBind(@NonNull MessageEntity message) {
            if (mPortrait == null) {
                return;
            }
            mPortrait.setOnClickListener(this);
            message.load();
            UserEntity sender = message.getSender();

            // 需要先进行数据加载
            sender.load();

            // 设置头像
            mPortrait.setup(Glide.with(ChatFragment.this), sender);

            if (mLoading != null) {
                // 右侧布局需要对Loading增加逻辑
                int state = message.getState();
                if (state == MessageEntity.STATE_DONE) {
                    // 发送成功的常态
                    mLoading.stop();
                    mLoading.setVisibility(View.GONE);
                } else if (state == MessageEntity.STATE_SENDING) {
                    // 发送中
                    mLoading.setProgress(0);
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.start();
                } else if (state == MessageEntity.STATE_FAILED) {
                    // 发送失败
                    mLoading.setVisibility(View.VISIBLE);
                    mLoading.stop();
                    mLoading.setProgress(1);
                }
            }
        }

        private void onPortraitClick() {
            Log.i(TAG, "onPortraitClick");
            if (getData() != null) {
                PersonalActivity.show(requireContext(), getData().getId(), true);
            }
        }

        @Override
        public void onClick(View v) {
            if (v == null) {
                return;
            }
            int id = v.getId();
            if (id == mPortrait.getId()) {
                // 头像点击
                onPortraitClick();
            } else {
                Log.w(TAG, "onClick: illegal param: " + id);
            }
        }
    }

    /**
     * 文本类型消息ViewHolder
     */
    class TextHolder extends ChatViewHolder {
        private TextView mContent;

        public TextHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.txt_content);
        }

        @Override
        protected void onBind(@NonNull MessageEntity message) {
            super.onBind(message);
            if (mContent == null) {
                return;
            }
            mContent.setText(message.getContent());
        }
    }
}
