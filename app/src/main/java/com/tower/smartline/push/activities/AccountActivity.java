package com.tower.smartline.push.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.tower.smartline.common.app.BaseActivity;
import com.tower.smartline.push.R;
import com.tower.smartline.push.databinding.ActivityAccountBinding;
import com.tower.smartline.push.frags.account.LoginFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import net.qiujuer.genius.ui.compat.UiCompat;

/**
 * 账户Activity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/4/30 7:00
 */
public class AccountActivity extends BaseActivity {
    private ActivityAccountBinding mBinding;

    // 当前的Fragment
    private Fragment mCurFragment;

    /**
     * 账户Activity拉起入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        if (context == null) {
            return;
        }
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

        // 初始化Fragment
        mCurFragment = new LoginFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(mBinding.layContainer.getId(), mCurFragment)
                .commit();

        // 初始化背景
        Glide.with(this)
                .load(R.drawable.bg_src_noon)
                .centerCrop() //居中剪切
                .into(new DrawableImageViewTarget(mBinding.imBg) {
                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        if (resource == null) {
                            super.setResource(null);
                            return;
                        }

                        // 使用适配类进行包装
                        Drawable drawable = DrawableCompat.wrap(resource);

                        // 设置着色的效果和颜色，蒙板模式
                        int color = UiCompat.getColor(getResources(), R.color.colorTranslucentAccent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SCREEN));
                        } else {
                            drawable.setColorFilter(color, PorterDuff.Mode.SCREEN);
                        }

                        // 设置给ImageView
                        super.setResource(drawable);
                    }
                });
    }
}