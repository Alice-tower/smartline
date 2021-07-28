package com.tower.smartline.factory.model.db;

import com.tower.smartline.factory.utils.DiffUiDataCallback;

import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 数据库Entity基础类
 *
 * @param <E> Entity类本身 用于实现RecyclerView的比较接口
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/25 14:38
 */
public abstract class BaseDbModel<E> extends BaseModel
        implements DiffUiDataCallback.UiDataDiffer<E> {
}
