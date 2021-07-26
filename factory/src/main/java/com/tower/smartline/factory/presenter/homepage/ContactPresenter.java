package com.tower.smartline.factory.presenter.homepage;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.presenter.BasePresenter;

import java.util.List;

/**
 * ContactPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/25 10:26
 */
public class ContactPresenter extends BasePresenter<IHomepageContract.View>
        implements IHomepageContract.Presenter, IDataSource.SuccessCallback<List<UserEntity>> {
    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public ContactPresenter(@NonNull IHomepageContract.View view) {
        super(view);
    }

    @Override
    public void refreshData() {
        super.start();
    }

    @Override
    public void onSuccess(List<UserEntity> users) {

    }
}
