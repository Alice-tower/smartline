package com.tower.smartline.factory.data;

import com.tower.smartline.factory.model.db.base.AppDatabase;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Arrays;

/**
 * 数据库基本功能封装
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/3 23:40
 */
public class DbPortal {
    private static final DbPortal INSTANCE = new DbPortal();

    private DbPortal() {
    }

    /**
     * 数据库新增或修改统一方法
     *
     * @param eClass   数据库表Class
     * @param entities 新增或修改数据的Entity数组
     * @param <E>      数据库表类型
     */
    @SafeVarargs
    public static <E extends BaseModel> void save(Class<E> eClass, E... entities) {
        if (entities == null || entities.length == 0) {
            return;
        }

        // 向数据库提交事务
        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(databaseWrapper -> {
                    // 保存数据，唤起通知
                    FlowManager.getModelAdapter(eClass).saveAll(Arrays.asList(entities));
                    INSTANCE.notifySave(eClass, entities);
                }).build().execute();
    }

    /**
     * 数据库删除统一方法
     *
     * @param eClass   数据库表Class
     * @param entities 删除数据的Entity数组
     * @param <E>      数据库表类型
     */
    @SafeVarargs
    public static <E extends BaseModel> void delete(Class<E> eClass, E... entities) {
        if (entities == null || entities.length == 0) {
            return;
        }

        // 向数据库提交事务
        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(databaseWrapper -> {
                    // 删除数据，唤起通知
                    FlowManager.getModelAdapter(eClass).deleteAll(Arrays.asList(entities));
                    INSTANCE.notifyDelete(eClass, entities);
                }).build().execute();
    }

    /**
     * 保存数据的通知
     *
     * @param eClass   数据库表Class
     * @param entities 通知数据的Entity数组
     * @param <E>      数据库表类型
     */
    @SafeVarargs
    private final <E extends BaseModel> void notifySave(final Class<E> eClass, final E... entities) {
        // TODO
    }

    /**
     * 删除数据的通知
     *
     * @param eClass   数据库表Class
     * @param entities 通知数据的Entity数组
     * @param <E>      数据库表类型
     */
    @SafeVarargs
    private final <E extends BaseModel> void notifyDelete(final Class<E> eClass, final E... entities) {
        // TODO
    }
}
