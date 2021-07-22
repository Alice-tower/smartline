package com.tower.smartline.factory.presenter;

import androidx.annotation.StringRes;

import com.tower.smartline.common.widget.recycler.BaseRecyclerAdapter;

/**
 * MVP模式基本约定
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/8 3:28
 */
public interface IBaseContract {
    /**
     * View基本职责
     *
     * @param <P> Presenter
     */
    interface View<P extends Presenter> {
        /**
         * 提示错误Toast
         *
         * @param str 字符串资源
         */
        void showError(@StringRes int str);

        /**
         * 显示Loading
         */
        void showLoading();

        /**
         * 设置Presenter
         *
         * @param presenter Presenter
         */
        void setPresenter(P presenter);
    }

    /**
     * Presenter基本职责
     */
    interface Presenter {
        // 开始触发
        void start();

        // 释放引用 解除关联
        void destroy();
    }

    /**
     * 列表View基本职责
     *
     * @param <P> Presenter
     * @param <D> BaseRecyclerAdapter存储的数据类型<Data>
     */
    interface RecyclableView<P extends Presenter, D> extends View<P> {
        /**
         * 获取RecyclerView适配器
         *
         * @return 适配器基类BaseRecyclerAdapter
         */
        BaseRecyclerAdapter<D> getRecyclerAdapter();

        /**
         * 适配器数据更改时触发
         */
        void onAdapterDataChanged();
    }
}
