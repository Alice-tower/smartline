package com.tower.smartline.common.widget.recycler;

/**
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/11/24 3:41
 */
public interface AdapterCallback<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
