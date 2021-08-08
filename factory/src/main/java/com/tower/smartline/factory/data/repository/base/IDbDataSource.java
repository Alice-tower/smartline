package com.tower.smartline.factory.data.repository.base;

import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.model.db.base.BaseEntity;

import java.util.List;

/**
 * 数据库数据源接口
 *
 * @param <E> Entity类本身
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/13 9:15
 */
public interface IDbDataSource<E extends BaseEntity<E>> extends IDataSource {
    /**
     * 数据库数据源加载
     *
     * @param callback 数据源回调
     */
    void load(SuccessCallback<List<E>> callback);
}
