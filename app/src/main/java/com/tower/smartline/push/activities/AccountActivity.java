package com.tower.smartline.push.activities;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.common.app.Activity;
import com.tower.smartline.common.app.Fragment;
import com.tower.smartline.push.databinding.ActivityAccountBinding;
import com.tower.smartline.push.frags.account.UpdateInfoFragment;

/**
 * 账户Activity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/4/30 7:00
 */
public class AccountActivity extends Activity {
    private ActivityAccountBinding mBinding;

    // 当前的Fragment
    private Fragment mCurFragment;

    /**
     * 账户Activity拉起入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, AccountActivity.class));
    }

    @NonNull
    @Override
    protected View initBinding() {
        mBinding = ActivityAccountBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mCurFragment = new UpdateInfoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(mBinding.layContainer.getId(), mCurFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCurFragment != null) {
            // 回调事件向当前Fragment传递
            mCurFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}