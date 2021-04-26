package com.tower.smartline.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 正方形View
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/4/25 17:04
 */
public class SquareLayout extends FrameLayout {
    public SquareLayout(@NonNull Context context) {
        super(context);
    }

    public SquareLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // noinspection SuspiciousNameCombination 测量值规定为基于宽度的正方形
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
