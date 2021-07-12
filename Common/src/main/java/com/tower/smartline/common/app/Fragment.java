package com.tower.smartline.common.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.common.widget.EmptyView;

/**
 * 自定义Fragment基类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/10/28 4:39
 */
public abstract class Fragment extends androidx.fragment.app.Fragment {
    protected View mRoot;

    protected EmptyView mEmptyView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRoot == null) {
            // 子类通过ViewBinding初始化根布局
            mRoot = initBinding(inflater, container);
            initWidget();
        } else {
            if (mRoot.getParent() != null) {
                // 把当前Root从其父控件中移除
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
            mRoot = initBinding(inflater, container);
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 当View创建完成后初始化数据
        initData();
    }

    /**
     * 初始化相关参数
     *
     * @param bundle 参数Bundle
     */
    protected void initArgs(Bundle bundle) {
    }

    /**
     * 初始化视图绑定
     *
     * @return 当前界面根布局View
     */
    @NonNull
    protected abstract View initBinding(@NonNull LayoutInflater inflater, ViewGroup container);

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

    /**
     * 返回按键触发时调用
     *
     * @return 返回True代表已处理返回逻辑，Activity无需处理
     */
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyBinding();
    }

    /**
     * Fragment的存在时间比其视图长，需要清除对绑定类实例的所有引用
     * 即 mBinding = null
     */
    protected abstract void destroyBinding();

    /**
     * 设置空布局
     *
     * @param emptyView 空布局
     */
    public void setEmptyView(EmptyView emptyView) {
        this.mEmptyView = emptyView;
    }
}
