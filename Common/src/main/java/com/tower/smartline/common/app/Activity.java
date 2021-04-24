package com.tower.smartline.common.app;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 自定义Activity基类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/10/28 4:38
 */
public abstract class Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 在界面未初始化之前调用的初始化窗口
        initWindows();

        if (initArgs(getIntent().getExtras())) {
            // 将界面Id设置到界面中
            int layId = getContentLayoutId();
            setContentView(layId);

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
     * 得到当前界面的资源文件Id
     *
     * @return 资源文件Id
     */
    @LayoutRes
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
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
                if (fragment instanceof com.tower.smartline.common.app.Fragment) {
                    if (((com.tower.smartline.common.app.Fragment) fragment).onBackPressed()) {
                        // 判断是Fragment是否自己拦截了返回按钮，若是则直接Return
                        return;
                    }
                }
            }
        }
        finish();
    }
}
