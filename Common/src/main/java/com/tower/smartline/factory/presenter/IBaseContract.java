package com.tower.smartline.factory.presenter;

import androidx.annotation.StringRes;

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
}
