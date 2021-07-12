package com.tower.smartline.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tower.smartline.common.R;

import net.qiujuer.genius.ui.widget.Loading;

/**
 * EmptyView
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/11 0:20
 */
public class EmptyView extends LinearLayout {
    private ImageView mEmptyImg;

    private TextView mStatusText;

    private Loading mLoading;

    private int[] mDrawableIds = new int[]{
            // 空数据 错误
            0, 0};

    private int[] mTextIds = new int[]{
            // 空数据 错误 加载中
            0, 0, 0};

    private View[] mDataViews;

    public EmptyView(Context context) {
        super(context);
        init(null, 0);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.lay_empty, this);
        mEmptyImg = findViewById(R.id.im_empty);
        mStatusText = findViewById(R.id.txt_empty);
        mLoading = findViewById(R.id.loading);

        // 获取自定义属性的值
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.EmptyView, defStyle, 0);
        mDrawableIds[0] = typedArray.getInt(R.styleable.EmptyView_comEmptyDrawable, R.drawable.status_empty);
        mDrawableIds[1] = typedArray.getInt(R.styleable.EmptyView_comErrorDrawable, R.drawable.status_empty);
        mTextIds[0] = typedArray.getInt(R.styleable.EmptyView_comEmptyText, R.string.label_empty_empty);
        mTextIds[1] = typedArray.getInt(R.styleable.EmptyView_comErrorText, R.string.label_empty_error);
        mTextIds[2] = typedArray.getInt(R.styleable.EmptyView_comLoadingText, R.string.label_empty_loading);
        typedArray.recycle();
    }

    /**
     * 绑定显示数据的View
     * 当前数据加载完成且不为空时 显示这些数据布局
     *
     * @param views 数据显示的布局
     */
    public void bind(View... views) {
        this.mDataViews = views;
    }

    /**
     * 显示空数据界面
     */
    public void showEmpty() {
        // 图片 有
        mEmptyImg.setImageResource(mDrawableIds[0]);
        mEmptyImg.setVisibility(VISIBLE);

        // 加载圈 无
        mLoading.setVisibility(GONE);
        mLoading.stop();

        // 状态文本
        mStatusText.setText(mTextIds[0]);

        changeBindViewVisibility(GONE);
        setVisibility(VISIBLE);
    }

    /**
     * 显示网络错误界面
     */
    public void showNetError() {
        // TODO
    }

    /**
     * 显示加载错误界面
     */
    public void showError() {
        // 图片 有
        mEmptyImg.setImageResource(mDrawableIds[1]);
        mEmptyImg.setVisibility(VISIBLE);

        // 加载圈 无
        mLoading.setVisibility(GONE);
        mLoading.stop();

        // 状态文本
        mStatusText.setText(mTextIds[1]);

        changeBindViewVisibility(GONE);
        setVisibility(VISIBLE);
    }

    /**
     * 显示加载中界面
     */
    public void showLoading() {
        // 图片 无
        mEmptyImg.setVisibility(GONE);

        // 加载圈 有
        mLoading.setVisibility(VISIBLE);
        mLoading.start();

        // 状态文本
        mStatusText.setText(mTextIds[2]);

        changeBindViewVisibility(GONE);
        setVisibility(VISIBLE);
    }

    /**
     * 数据加载成功
     * 显示绑定的数据布局
     */
    public void showOk() {
        setVisibility(GONE);
        changeBindViewVisibility(VISIBLE);
    }

    /**
     * 显示绑定的数据布局或空布局
     *
     * @param isOk 是否成功加载不为空的数据
     */
    public void showOkOrEmpty(boolean isOk) {
        if (isOk) {
            showOk();
        } else {
            showEmpty();
        }
    }

    /**
     * 更改绑定布局的显示状态
     *
     * @param visible 显示的状态
     */
    private void changeBindViewVisibility(int visible) {
        if (mDataViews == null || mDataViews.length <= 0) {
            return;
        }
        for (View view : mDataViews) {
            view.setVisibility(visible);
        }
    }
}
