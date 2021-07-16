package com.tower.smartline.push.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.common.app.BaseActivity;
import com.tower.smartline.factory.persistence.Account;
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
public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer>, View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();

    // 当前页为 首页
    private static final int TYPE_HOME = 0;

    // 当前页为 群组
    private static final int TYPE_GROUP = 1;

    // 当前页为 联系人
    private static final int TYPE_CONTACT = 2;

    // 悬浮按钮旋转弹出动画初始值
    private static final int DEFAULT_VALUE = 0;

    // 悬浮按钮旋转动画处理值
    private static final float ROTATION_VALUE = 360;

    private ActivityMainBinding mBinding;

    private NavHelper<Integer> mNavHelper;

    // 当前页面类型
    private int mType = TYPE_HOME;

    /**
     * MainActivity拉起入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (!Account.isComplete()) {
            // 用户信息不完整
            UserActivity.show(this);
            return false;
        }
        return super.initArgs(bundle);
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
    }

    private void onSearchClick() {
        Log.i(TAG, "onSearchClick");
        SearchActivity.show(this, SearchActivity.TYPE_MAIN);
    }

    private void onActionClick() {
        Log.i(TAG, "onActionClick: type: " + mType);
        if (mType == TYPE_GROUP) {
            SearchActivity.show(this, SearchActivity.TYPE_GROUP);
        } else if (mType == TYPE_CONTACT) {
            SearchActivity.show(this, SearchActivity.TYPE_USER);
        }
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
        String title = getString(newTab.getExtra());
        Log.w(TAG, "onTabChanged: " + title);

        // 更改标题栏文字
        mBinding.txtTitle.setText(title);

        // 浮动按钮动画
        if (oldTab == null) {
            return;
        }
        float translationValue = DEFAULT_VALUE;
        float rotationValue = DEFAULT_VALUE;
        if (Objects.equals(newTab.getClx(), HomeFragment.class)) {
            mType = TYPE_HOME; // 记录当前页面类型
            translationValue = Ui.dipToPx(getResources(), getResources().getDimension(R.dimen.len_84));
        } else if (Objects.equals(newTab.getClx(), GroupFragment.class)) {
            mType = TYPE_GROUP; // 记录当前页面类型
            mBinding.btnAction.setImageResource(R.drawable.ic_group_add);
            mBinding.btnAction.setRotation(ROTATION_VALUE);
            rotationValue = -ROTATION_VALUE;
        } else if (Objects.equals(newTab.getClx(), ContactFragment.class)) {
            mType = TYPE_CONTACT; // 记录当前页面类型
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