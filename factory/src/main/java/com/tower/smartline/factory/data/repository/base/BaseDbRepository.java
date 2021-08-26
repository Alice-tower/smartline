package com.tower.smartline.factory.data.repository.base;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.data.db.DbSubject;
import com.tower.smartline.factory.data.db.IDbObserver;
import com.tower.smartline.factory.model.db.base.BaseEntity;
import com.tower.smartline.utils.CollectionUtil;

import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 数据仓库基类
 *
 * @param <E> Entity类本身
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/8 13:49
 */
public abstract class BaseDbRepository<E extends BaseEntity<E>> implements IDbDataSource<E>,
        IDbObserver<E>,
        QueryTransaction.QueryResultListCallback<E> {
    /**
     * 当前缓存的数据
     */
    protected final LinkedList<E> mDataList = new LinkedList<>();

    // 和Presenter交互的回调
    private SuccessCallback<List<E>> mCallback;

    // 当前泛型Entity的类型信息
    private Class<E> mEClass;

    @SuppressWarnings("unchecked")
    public BaseDbRepository() {
        // 运行时根据泛型初始化mEClass参数
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if (types.length > 0) {
                mEClass = (Class<E>) types[0];
            }
        }
    }

    @Override
    public void load(@NonNull SuccessCallback<List<E>> callback) {
        this.mCallback = callback;
        if (mEClass != null) {
            DbSubject.getInstance().registerDbObserver(mEClass, this);
        }
        loadData();
    }

    /**
     * 加载数据源时加载一次数据
     */
    protected abstract void loadData();

    @Override
    public void release() {
        // 资源释放
        this.mCallback = null;
        DbSubject.getInstance().removeDbObserver(mEClass, this);
        mDataList.clear();
    }

    @Override
    public void onDataSave(E[] list) {
        boolean isChanged = false;
        for (E data : list) {
            // 是否是逻辑上需要的数据
            if (isRequired(data)) {
                addOrUpdate(data);
                isChanged = true;
            }
        }
        if (isChanged) {
            notifyDataChange();
        }
    }

    @Override
    public void onDataDelete(E[] list) {
        boolean isChanged = false;
        for (E data : list) {
            if (mDataList.remove(data)) {
                // 移除成功则通知数据变更
                isChanged = true;
            }
        }
        if (isChanged) {
            notifyDataChange();
        }
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<E> tResult) {
        // 数据库回调的处理
        if (tResult.size() == 0) {
            mDataList.clear();
            notifyDataChange();
            return;
        }
        E[] users = CollectionUtil.toArray(tResult, mEClass);
        onDataSave(users);
    }

    /**
     * 新增或更新数据
     *
     * @param data 数据
     */
    private void addOrUpdate(E data) {
        int index = indexOf(data);
        if (index < 0) {
            add(data);
        } else {
            update(index, data);
        }
    }

    /**
     * 更新数据
     *
     * @param index 数组下标
     * @param data  数据
     */
    protected void update(int index, E data) {
        mDataList.set(index, data);
    }

    /**
     * 新增数据
     *
     * @param data 数据
     */
    protected void add(E data) {
        mDataList.add(data);
    }

    /**
     * 查询数据对应的数组下标
     *
     * @param newData 数据
     * @return 数组下标，为-1则表示不存在该数据
     */
    protected int indexOf(E newData) {
        int index = -1;
        for (E data : mDataList) {
            index++;
            if (data.isSame(newData)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * 检查数据在逻辑中是否需要
     *
     * @param data 被检查的数据
     * @return 是否需要
     */
    protected abstract boolean isRequired(E data);

    /**
     * 向回调通知数据变更
     */
    private void notifyDataChange() {
        if (mCallback != null) {
            mCallback.onSuccess(mDataList);
        }
    }
}
