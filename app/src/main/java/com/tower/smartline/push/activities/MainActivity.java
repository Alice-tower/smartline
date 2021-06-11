package com.tower.smartline.push.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.common.app.Activity;
import com.tower.smartline.push.R;
import com.tower.smartline.push.databinding.ActivityMainBinding;
import com.tower.smartline.push.frags.main.ContactFragment;
import com.tower.smartline.push.frags.main.GroupFragment;
import com.tower.smartline.push.frags.main.HomeFragment;
import com.tower.smartline.push.helper.NavHelper;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

import net.qiujuer.genius.ui.Ui;

import java.util.Objects;

/**
 * App主界面
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/10/27 4:00
 */
public class MainActivity extends Activity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer>, View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();

    private static final int DEFAULT_VALUE = 0;

    private static final float ROTATION_VALUE = 360;

    private ActivityMainBinding mBinding;

    private NavHelper<Integer> mNavHelper;

    /**
     * MainActivity拉起入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @NonNull
    @Override
    protected View initBinding() {
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        // 底部导航栏初始化
        mNavHelper = new NavHelper<>(this, R.id.lay_container,
                getSupportFragmentManager(), this);
        mNavHelper.add(R.id.action_home, new NavHelper.Tab<>(HomeFragment.class, R.string.title_home))
                .add(R.id.action_group, new NavHelper.Tab<>(GroupFragment.class, R.string.title_group))
                .add(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.title_contact));
        mBinding.navigation.setOnNavigationItemSelectedListener(this);

        // 顶部TitleBar加载背景图
        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new CustomViewTarget<AppBarLayout, Drawable>(mBinding.appbar) {
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

        // 点击监听初始化
        mBinding.imPortrait.setOnClickListener(this);
        mBinding.imSearch.setOnClickListener(this);
        mBinding.btnAction.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();

        // 底部导航栏默认选中首页
        Menu menu = mBinding.navigation.getMenu();
        menu.performIdentifierAction(R.id.action_home, 0);
    }

    private void onPortraitClick() {
        Log.i(TAG, "onPortraitClick");
        UserActivity.show(this);
    }

    private void onSearchClick() {
        Log.i(TAG, "onSearchClick");
    }

    private void onActionClick() {
        Log.i(TAG, "onActionClick");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getTitle() != null) {
            Log.i(TAG, "onNavigationItemSelected: " + item.getTitle());
        }

        // 转接事件流到辅助类中
        return mNavHelper.performClickMenu(item.getItemId());
    }

    @Override
    public void onTabChanged(@NonNull NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        if (getResources() == null) {
            return;
        }

        // 更改标题栏文字
        String title = getString(newTab.getExtra());
        mBinding.txtTitle.setText(title);

        // 浮动按钮动画
        if (oldTab == null) {
            return;
        }
        float translationValue = DEFAULT_VALUE;
        float rotationValue = DEFAULT_VALUE;
        if (Objects.equals(newTab.getClx(), HomeFragment.class)) {
            translationValue = Ui.dipToPx(getResources(), getResources().getDimension(R.dimen.len_84));
        }
        if (Objects.equals(newTab.getClx(), GroupFragment.class)) {
            mBinding.btnAction.setImageResource(R.drawable.ic_group_add);
            mBinding.btnAction.setRotation(ROTATION_VALUE);
            rotationValue = -ROTATION_VALUE;
        }
        if (Objects.equals(newTab.getClx(), ContactFragment.class)) {
            mBinding.btnAction.setImageResource(R.drawable.ic_contact_add);
            mBinding.btnAction.setRotation(-ROTATION_VALUE);
            rotationValue = ROTATION_VALUE;
        }
        if (Objects.equals(newTab.getClx(), HomeFragment.class)
                || Objects.equals(oldTab.getClx(), HomeFragment.class)) {
            mBinding.btnAction.animate().translationY(translationValue).start();
        } else {
            mBinding.btnAction.animate().rotation(rotationValue).start();
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
        } else if (id == mBinding.imSearch.getId()) {
            // 搜索点击
            onSearchClick();
        } else if (id == mBinding.btnAction.getId()) {
            // 浮动按钮点击
            onActionClick();
        } else {
            Log.w(TAG, "onClick: illegal param: " + id);
        }
    }
}