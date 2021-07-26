package com.tower.smartline.factory.utils;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * DiffUiDataCallback
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/25 10:52
 */
public class DiffUiDataCallback<T extends DiffUiDataCallback.UiDataDiffer<T>> extends DiffUtil.Callback {
    private List<T> mOldList, mNewList;

    public DiffUiDataCallback(List<T> mOldList, List<T> mNewList) {
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
        T oldItem = mOldList.get(oldItemPosition);
        T newItem = mNewList.get(newItemPosition);
        return newItem.isSame(oldItem);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = mOldList.get(oldItemPosition);
        T newItem = mNewList.get(newItemPosition);
        return newItem.isUiContentSame(oldItem);
    }

    /**
     * 需要被比较的类型的接口
     *
     * @param <T> 需要被比较的类型，即实现该接口的类本身
     */
    public interface UiDataDiffer<T> {
        /**
         * 指向是否为同一数据
         *
         * @param old 旧数据
         * @return True为相同，False为不同
         */
        boolean isSame(T old);

        /**
         * Ui相关的数据内容是否相同
         *
         * @param old 旧数据
         * @return True为相同，False为不同
         */
        boolean isUiContentSame(T old);
    }
}
