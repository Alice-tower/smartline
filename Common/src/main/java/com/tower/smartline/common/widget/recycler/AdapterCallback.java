package com.tower.smartline.common.widget.recycler;

/**
 * RecyclerView适配器回调接口
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/11/24 3:41
 */
public interface AdapterCallback<Data> {
    void update(Data data, RecyclerAdapter.MyViewHolder<Data> holder);
}
