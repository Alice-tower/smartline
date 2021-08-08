package com.tower.smartline.factory.data.db;

import com.tower.smartline.factory.model.db.base.BaseEntity;

/**
 * 数据库观察者接口
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/5 16:42
 */
@SuppressWarnings({"unchecked"})
public interface IDbObserver<E extends BaseEntity<E>> {
    /**
     * 当数据库新增或修改时发送的通知
     *
     * @param list 被保存的Entity数组
     */
    void onDataSave(E... list);

    /**
     * 当数据库删除时发送的通知
     *
     * @param list 被删除的Entity数组
     */
    void onDataDelete(E... list);
}
