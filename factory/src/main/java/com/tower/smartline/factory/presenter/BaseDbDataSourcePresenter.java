package com.tower.smartline.factory.presenter;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.data.repository.base.IDbDataSource;
import com.tower.smartline.factory.model.db.base.BaseEntity;

import java.util.List;

/**
 * BaseDbDataSourcePresenter
 *
 * @param <Data>       RecyclerView数据集合中的数据类型，亦是数据库表Entity类本身
 * @param <V>          MVP模式中的View
 * @param <Repository> 实现了数据库数据源接口的数据仓库
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/13 10:16
 */
public abstract class BaseDbDataSourcePresenter<Data extends BaseEntity<Data>,
        V extends IBaseContract.RecyclableView<? extends IBaseContract.Presenter, Data>,
        Repository extends IDbDataSource<Data>>
        extends BaseRecyclerPresenter<Data, V>
        implements IDataSource.SuccessCallback<List<Data>> {
    // 数据仓库
    private Repository mRepository;

    /**
     * 构造方法
     *
     * @param view       Presenter需要绑定的View层实例
     * @param repository 数据仓库
     */
    public BaseDbDataSourcePresenter(@NonNull V view, Repository repository) {
        super(view);
        this.mRepository = repository;
    }

    @Override
    public void start() {
        super.start();
        if (mRepository != null) {
            mRepository.load(this);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        mRepository.release();
        mRepository = null;
    }
}
