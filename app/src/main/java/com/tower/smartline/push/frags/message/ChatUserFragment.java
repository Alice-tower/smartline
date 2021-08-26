package com.tower.smartline.push.frags.message;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.presenter.message.ChatUserPresenter;
import com.tower.smartline.factory.presenter.message.IChatContract;
import com.tower.smartline.push.R;
import com.tower.smartline.push.activities.PersonalActivity;

import com.bumptech.glide.Glide;

/**
 * ChatUserFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/19 15:24
 */
public class ChatUserFragment extends ChatFragment<UserEntity> {
    private static final String TAG = ChatUserFragment.class.getName();

    public ChatUserFragment() {
        // Required empty public constructor
    }

    @Override
    public IChatContract.Presenter initPresenter() {
        return new ChatUserPresenter(this, mReceiverId);
    }

    @Nullable
    @Override
    protected MenuItem initToolbarMenu(Toolbar toolbar) {
        if (toolbar == null) {
            return null;
        }
        toolbar.inflateMenu(R.menu.chat_user);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_person) {
                onPortraitClick();
                return true;
            }
            return false;
        });
        return toolbar.getMenu().findItem(R.id.action_person);
    }

    @Override
    protected void onPortraitClick() {
        Log.i(TAG, "onPortraitClick");
        PersonalActivity.show(requireContext(), mReceiverId, true);
    }

    @Override
    public void initReceiverSuccess(UserEntity data) {
        if (data != null) {
            // 初始化顶部头像和用户名
            mBinding.imPortrait.setup(Glide.with(this), data.getPortrait());
            mBinding.layCollapsing.setTitle(data.getName());
        }
    }
}
