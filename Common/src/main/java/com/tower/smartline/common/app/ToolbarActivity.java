package com.tower.smartline.common.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.tower.smartline.common.R;

/**
 * ToolbarActivity
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/7 15:18
 */
public abstract class ToolbarActivity extends BaseActivity {
    protected Toolbar mToolbar;

    private ActionBar mActionBar;

    @Override
    protected void initWidget() {
        super.initWidget();
        mToolbar = findViewById(R.id.toolbar);
        initToolbar();
    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        initTitleNeedBack();
    }

    /**
     * 设置左上角的返回按钮为实际的返回效果
     */
    protected void initTitleNeedBack() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        }
    }

    /**
     * 去除默认Title显示
     */
    protected void hideToolbarTitle() {
        if (mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(false);
        }
    }
}
