package com.tower.smartline.common.app;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tower.smartline.common.widget.EmptyView;

import java.util.List;

/**
 * 自定义Activity基类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/10/28 4:38
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 在界面未初始化之前调用的初始化窗口
        initWindows();

        if (initArgs(getIntent().getExtras())) {
            // 将界面Id设置到界面中
            View root = initBinding();
            setContentView(root);

            initWidget();
            initData();
        } else {
            finish();
        }
    }

    /**
     * 初始化窗口
     */
    protected void initWindows() {
    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数Bundle
     * @return 如果参数正确返回True
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 初始化视图绑定
     *
     * @return 当前界面根布局View
     */
    @NonNull
    protected abstract View initBinding();

    /**
     * 初始化控件
     */
    protected void initWidget() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 点击界面导航返回时 Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // 得到当前Activity下的所有Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                // 判断是否为自己封装的Fragment
                if (fragment instanceof BaseFragment) {
                    if (((BaseFragment) fragment).onBackPressed()) {
                        // 判断是Fragment是否自己拦截了返回按钮，若是则直接Return
                        return;
                    }
                }
            }
        }
        finish();
    }

    /**
     * 设置空布局
     *
     * @param emptyView 空布局
     * @param views     空布局绑定的数据布局
     */
    public void setEmptyView(EmptyView emptyView, View... views) {
        if (emptyView != null) {
            emptyView.bind(views);
        }
        this.mEmptyView = emptyView;
    }
}
