package com.tower.smartline.factory.presenter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.tower.smartline.common.widget.recycler.BaseRecyclerAdapter;

import net.qiujuer.genius.kit.handler.Run;

import java.util.List;

/**
 * BaseRecyclerPresenter
 *
 * @param <Data> RecyclerView数据集合中的数据类型
 * @param <V>    MVP模式中的View
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/26 18:50
 */
public abstract class BaseRecyclerPresenter<Data, V extends IBaseContract.RecyclableView<? extends IBaseContract.Presenter, Data>>
        extends BasePresenter<V> {
    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public BaseRecyclerPresenter(@NonNull V view) {
        super(view);
    }

    /**
     * 刷新一组新的数据集合到界面中
     *
     * @param dataList 新数据
     */
    protected void refreshData(List<Data> dataList) {
        Run.onUiAsync(() -> {
            if (getView() == null) {
                return;
            }
            BaseRecyclerAdapter<Data> adapter = getView().getRecyclerAdapter();
            if (adapter == null) {
                return;
            }

            // 替换数据 通知界面刷新
            adapter.replace(dataList);
            getView().onAdapterDataChanged();
        });
    }

    /**
     * 刷新界面
     *
     * @param diffResult 一个差异的结果集
     * @param dataList   具体的新数据
     */
    protected void refreshData(DiffUtil.DiffResult diffResult, List<Data> dataList) {
        Run.onUiAsync(() -> {
            if (getView() == null) {
                return;
            }
            BaseRecyclerAdapter<Data> adapter = getView().getRecyclerAdapter();
            if (adapter == null) {
                return;
            }

            // 替换数据 不通知界面刷新
            adapter.getItems().clear();
            adapter.getItems().addAll(dataList);

            // 通知界面数据更改 仅判断是否展示空布局
            getView().onAdapterDataChanged();

            // 通知界面增量刷新
            diffResult.dispatchUpdatesTo(adapter);
        });
    }
}
