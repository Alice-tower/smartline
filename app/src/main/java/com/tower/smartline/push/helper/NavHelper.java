package com.tower.smartline.push.helper;

import android.content.Context;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * BottomNavigationView辅助类
 * 解决对Fragment的调度与重用
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/4/23 1:53
 */
public class NavHelper<T> {
    // 所有Tab集合
    private final SparseArray<Tab<T>> mTabs = new SparseArray<>();

    private final Context mContext;

    private final int mContainerId;

    private final FragmentManager mFragmentManager;

    private final OnTabChangedListener<T> mListener;

    // 当前选中的Tab
    private Tab<T> mCurrentTab;

    // 点击事件是否处理中
    private boolean mIsPerform = false;

    /**
     * 构造方法
     *
     * @param context         上下文
     * @param containerId     容器Id
     * @param fragmentManager fragmentManager
     * @param listener        Tab切换监听
     */
    public NavHelper(Context context, int containerId, FragmentManager fragmentManager,
                     OnTabChangedListener<T> listener) {
        this.mContext = context;
        this.mContainerId = containerId;
        this.mFragmentManager = fragmentManager;
        this.mListener = listener;
    }

    /**
     * 添加Tab
     *
     * @param menuId Tab对应的菜单Id
     * @param tab    Tab
     * @return 当前实例
     */
    public NavHelper<T> add(int menuId, Tab<T> tab) {
        mTabs.put(menuId, tab);
        return this;
    }

    /**
     * 执行点击菜单的操作
     *
     * @param menuId 菜单ID
     * @return 是否处理了点击
     */
    public boolean performClickMenu(int menuId) {
        if (mIsPerform) {
            return false;
        }
        mIsPerform = true;
        Tab<T> tab = mTabs.get(menuId);
        if (tab != null) {
            doSelect(tab);
            mIsPerform = false;
            return true;
        }
        mIsPerform = false;
        return false;
    }

    /**
     * 判断是否切换Tab
     *
     * @param tab 点击的Tab
     */
    private void doSelect(Tab<T> tab) {
        Tab<T> oldTab = null;
        if (mCurrentTab != null) {
            oldTab = mCurrentTab;
            if (oldTab == tab) {
                notifyReselect(tab);
                return;
            }
        }
        mCurrentTab = tab;
        doTabChanged(tab, oldTab);
    }

    /**
     * 相同Tab二次点击
     *
     * @param tab 点击的Tab
     */
    private void notifyReselect(Tab<T> tab) {
        // TODO Tab二次点击
    }

    /**
     * 切换Tab逻辑
     *
     * @param newTab 新Tab
     * @param oldTab 旧Tab
     */
    private void doTabChanged(Tab<T> newTab, Tab<T> oldTab) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (oldTab != null && oldTab.fragment != null) {
            // 从界面移除旧Fragment，仍保存在缓存中
            ft.detach(oldTab.fragment);
        }

        if (newTab.fragment == null) {
            // 新建Fragment
            Fragment fragment = mFragmentManager.getFragmentFactory()
                    .instantiate(mContext.getClassLoader(), newTab.clx.getName());
            newTab.fragment = fragment;
            ft.add(mContainerId, fragment, newTab.clx.getName());
        } else {
            // 从缓存中加载Fragment到界面
            ft.attach(newTab.fragment);
        }

        // 提交事务
        ft.commit();

        // 监听器回调
        if (mListener != null) {
            mListener.onTabChanged(newTab, oldTab);
        }
    }

    /**
     * 获取当前选中的Tab
     *
     * @return 当前选中的Tab
     */
    public Tab<T> getCurrentTab() {
        return mCurrentTab;
    }

    /**
     * Tab基础属性
     *
     * @param <T> 泛型额外参数，可任意指定
     */
    public static class Tab<T> {
        public Tab(Class<? extends Fragment> clx, T extra) {
            this.clx = clx;
            this.extra = extra;
        }

        // 内部缓存的对应的fragment
        private Fragment fragment;

        // Fragment对应Class信息
        private Class<? extends Fragment> clx;

        // 额外字段，任意指定
        private T extra;

        public T getExtra() {
            return extra;
        }
    }

    /**
     * Tab切换结束回调接口
     */
    public interface OnTabChangedListener<T> {
        void onTabChanged(@NonNull Tab<T> newTab, Tab<T> oldTab);
    }
}
