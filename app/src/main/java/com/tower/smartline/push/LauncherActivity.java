package com.tower.smartline.push;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.common.Config;
import com.tower.smartline.common.app.Activity;
import com.tower.smartline.factory.persistence.Account;
import com.tower.smartline.push.activities.AccountActivity;
import com.tower.smartline.push.databinding.ActivityLauncherBinding;
import com.tower.smartline.push.frags.assist.PermissionsFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Timer;
import java.util.TimerTask;

public class LauncherActivity extends Activity {
    private ActivityLauncherBinding mBinding;

    // 动画是否播放结束
    private boolean mIsAnimationOver = false;

    // Config参数是否可用
    private boolean mIsConfigAvailable = false;

    // 计时器是否已结束
    private boolean mIsTimerOver = false;

    private Timer mTimer;

    @NonNull
    @Override
    protected View initBinding() {
        mBinding = ActivityLauncherBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        // 显示版本号
        mBinding.txtVersion.setText(BuildConfig.VERSION_NAME);

        // 加载背景图
        Glide.with(this)
                .load(R.drawable.bg_launcher)
                .centerCrop()
                .into(new CustomViewTarget<LinearLayout, Drawable>(mBinding.layContainer) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        this.view.setBackground(resource);
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();

        // 遮罩渐变动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(mBinding.imgFg, "alpha", 1f, 0f);
        animator.setDuration(3000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mIsAnimationOver = true;
            }
        });
        animator.start();

        // 计时器初始化 循环周期500ms
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (!mIsTimerOver) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (mIsConfigAvailable && mIsAnimationOver
                            && !TextUtils.isEmpty(Account.getPushId())) {
                        // Config参数不为空 渐变动画结束 PushId准备就绪
                        mTimer.cancel();
                        checkPermission();
                        mIsTimerOver = true;
                    }
                }
            }, 0, 500);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 检查网络配置相关参数 引导开发者配置Config参数
        if (!checkConfig()) {
            return;
        }

        if (mIsTimerOver) {
            checkPermission();
        }
    }

    /**
     * 检查权限
     * 引导用户开启所需的权限
     * 权限就绪则跳转Activity
     */
    private void checkPermission() {
        if (PermissionsFragment.hasAllPermissions(this, getSupportFragmentManager())) {
            // TODO 通过检查PushId Token等相关持久化参数 实现免登录
            AccountActivity.show(this);
            finish();
        }
    }

    private boolean checkConfig() {
        if (Config.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.launcher_alert_title)
                    .setIcon(R.mipmap.ic_launcher)
                    .setMessage(R.string.launcher_alert_message)
                    .setPositiveButton(R.string.launcher_alert_confirm, (dialog, which) -> finish())
                    .create().show();
            return false;
        }

        // App无法运行 无需考虑计时器内存泄漏问题
        mIsConfigAvailable = true;
        return true;
    }
}