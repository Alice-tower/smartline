package com.tower.smartline.factory.data.db;

import com.tower.smartline.factory.model.db.MessageEntity;

import com.tower.smartline.factory.model.db.base.BaseEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * DbSubject
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/5 17:07
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DbSubject {
    private static final DbSubject INSTANCE = new DbSubject();

    // 数据库观察者集合
    // Key: 观察的表
    // Value: 每个表对应的观察者集合
    private final Map<Class<?>, Set<IDbObserver>> mObservers = new HashMap<>();

    private DbSubject() {
    }

    /**
     * 获取单例
     *
     * @return DbSubject
     */
    public static DbSubject getInstance() {
        return INSTANCE;
    }

    /**
     * 对某一数据库表注册一个新的观察者
     *
     * @param eClass   被添加观察的数据库表Class信息
     * @param observer 观察者
     * @param <E>      被添加观察的数据库表
     */
    public <E extends BaseEntity<E>> void registerDbObserver(Class<E> eClass, IDbObserver<E> observer) {
        Set<IDbObserver> observers = getObservers(eClass);
        if (observers == null) {
            // 该表还没有建立过观察者 进行一次初始化
            observers = new HashSet<>();
            mObservers.put(eClass, observers);
        }
        observers.add(observer);
    }

    /**
     * 对某一数据库表移除指定的某一观察者
     *
     * @param eClass   被移除观察的数据库表Class信息
     * @param observer 观察者
     * @param <E>      被移除观察的数据库表
     */
    public <E extends BaseEntity<E>> void removeDbObserver(Class<E> eClass, IDbObserver<E> observer) {
        Set<IDbObserver> observers = getObservers(eClass);
        if (observers == null) {
            // 该表还没有建立过观察者 直接Return
            return;
        }
        observers.remove(observer);
    }

    /**
     * 保存数据的通知
     *
     * @param eClass   数据库表Class
     * @param entities 通知数据的Entity数组
     * @param <E>      数据库表类型
     */
    <E extends BaseEntity<E>> void notifySave(Class<E> eClass, E... entities) {
        Set<IDbObserver> observers = getObservers(eClass);
        if (observers != null && observers.size() > 0) {
            for (IDbObserver<E> observer : observers) {
                observer.onDataSave(entities);
            }
        }

        // 特定表的一些数据需要额外处理
        if (Objects.equals(eClass, MessageEntity.class)) {
            // TODO 通知最近会话模块更新
        }
    }

    /**
     * 删除数据的通知
     *
     * @param eClass   数据库表Class
     * @param entities 通知数据的Entity数组
     * @param <E>      数据库表类型
     */
    <E extends BaseEntity<E>> void notifyDelete(Class<E> eClass, E... entities) {
        Set<IDbObserver> observers = getObservers(eClass);
        if (observers != null && observers.size() > 0) {
            for (IDbObserver<E> observer : observers) {
                observer.onDataDelete(entities);
            }
        }

        // 特定表的一些数据需要额外处理
        if (Objects.equals(eClass, MessageEntity.class)) {
            // TODO 通知最近会话模块更新
        }
    }

    private <E extends BaseEntity<E>> Set<IDbObserver> getObservers(Class<E> eClass) {
        if (mObservers.containsKey(eClass)) {
            return mObservers.get(eClass);
        }
        return null;
    }
}
