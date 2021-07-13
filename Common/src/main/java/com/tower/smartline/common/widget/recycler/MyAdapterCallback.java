package com.tower.smartline.common.widget.recycler;

/**
 * ViewHolder数据更新回调给Adapter处理的接口
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/11/24 3:41
 */
public interface MyAdapterCallback<Data> {
    /**
     * 更新指定ViewHolder的数据，通知界面刷新
     *
     * @param data 更新的数据
     * @param holder 更新数据的ViewHolder
     */
    void update(Data data, BaseRecyclerAdapter.BaseRecyclerViewHolder<Data> holder);
}
