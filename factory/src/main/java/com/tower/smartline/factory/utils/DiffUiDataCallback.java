package com.tower.smartline.factory.utils;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * DiffUiDataCallback
 *
 * @param <E> RecyclerView数据集合中需要被比较的类型
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/25 10:52
 */
public class DiffUiDataCallback<E extends DiffUiDataCallback.UiDataDiffer<E>> extends DiffUtil.Callback {
    private List<E> mOldList, mNewList;

    public DiffUiDataCallback(List<E> mOldList, List<E> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        E oldItem = mOldList.get(oldItemPosition);
        E newItem = mNewList.get(newItemPosition);
        return newItem.isSame(oldItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        E oldItem = mOldList.get(oldItemPosition);
        E newItem = mNewList.get(newItemPosition);
        return newItem.isUiContentSame(oldItem);
    }

    /**
     * 需要被比较的类型的接口
     *
     * @param <E> 需要被比较的类型，即实现该接口的类本身
     */
    public interface UiDataDiffer<E> {
        /**
         * 指向是否为同一数据
         *
         * @param old 旧数据
         * @return True为相同，False为不同
         */
        boolean isSame(E old);

        /**
         * Ui相关的数据内容是否相同
         *
         * @param old 旧数据
         * @return True为相同，False为不同
         */
        boolean isUiContentSame(E old);
    }
}
