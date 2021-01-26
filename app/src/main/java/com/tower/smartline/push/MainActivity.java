package com.tower.smartline.push;

import android.widget.TextView;

import com.tower.smartline.common.app.Activity;

import butterknife.BindView;

/**
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/10/27 4:00
 */
public class MainActivity extends Activity {
    @BindView(R.id.text_test)
    TextView mTestText;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mTestText.setText("ButterKinfe Test!");
    }
}