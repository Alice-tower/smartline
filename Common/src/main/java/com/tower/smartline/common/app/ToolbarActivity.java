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
public abstract class ToolbarActivity extends Activity {
    protected Toolbar mToolbar;

    @Override
    protected void initWidget() {
        super.initWidget();

        mToolbar = findViewById(R.id.toolbar);
        initToolbar();
    }

    /**
     * 初始化toolbar
     */
    public void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        initTitleNeedBack();
    }

    protected void initTitleNeedBack() {
        // 设置左上角的返回按钮为实际的返回效果
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
