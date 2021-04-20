package com.tower.smartline.push;

import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.common.app.Activity;
import com.tower.smartline.common.widget.PortraitView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/10/27 4:00
 */
public class MainActivity extends Activity {
    @BindView(R.id.appbar)
    AppBarLayout mLayAppbar;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;

    @BindView(R.id.btn_action)
    FloatActionButton mAction;

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        // TitleBar加载背景图
        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new CustomViewTarget<AppBarLayout, Drawable>(mLayAppbar) {
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

    @OnClick(R.id.im_portrait)
    void onPortraitClick() {
        Toast.makeText(this, "onPortraitClick", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.im_search)
    void onSearchClick() {
        Toast.makeText(this, "onSearchClick", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_action)
    void onActionClick() {
        Toast.makeText(this, "onActionClick", Toast.LENGTH_SHORT).show();
    }
}