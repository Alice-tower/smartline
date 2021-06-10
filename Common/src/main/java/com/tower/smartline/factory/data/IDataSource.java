package com.tower.smartline.factory.data;

import androidx.annotation.StringRes;

/**
 * IDataSource
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 4:07
 */
public interface IDataSource {

    /**
     * 回调接口 (包括成功和失败两种回调)
     *
     * @param <T> 任意类型
     */
    interface Callback<T> extends SuccessCallback<T>, FailureCallback {
    }

    /**
     * 成功的回调接口
     *
     * @param <T> 任意类型
     */
    interface SuccessCallback<T> {
        /**
         * 数据加载成功 网络请求成功
         * 网络请求则注意Ui操作需要强制在主线程中执行
         *
         * @param t 数据结构
         */
        void onSuccess(T t);
    }

    /**
     * 失败的回调接口
     */
    interface FailureCallback {
        /**
         * 数据加载失败 网络请求失败
         * 网络请求则注意Ui操作需要强制在主线程中执行
         *
         * @param strRes 建议显示的Toast提示资源
         */
        void onFailure(@StringRes int strRes);
    }
}
