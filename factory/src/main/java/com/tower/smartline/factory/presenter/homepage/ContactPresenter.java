package com.tower.smartline.factory.presenter.homepage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.tower.smartline.common.widget.recycler.BaseRecyclerAdapter;
import com.tower.smartline.factory.data.helper.UserHelper;
import com.tower.smartline.factory.data.repository.ContactRepository;
import com.tower.smartline.factory.data.repository.base.IUserSource;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.presenter.BaseDbDataSourcePresenter;
import com.tower.smartline.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * ContactPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/25 10:26
 */
public class ContactPresenter extends BaseDbDataSourcePresenter<UserEntity, IHomepageContract.View, IUserSource>
        implements IHomepageContract.Presenter {
    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public ContactPresenter(@NonNull IHomepageContract.View view) {
        super(view, new ContactRepository());
    }

    @Override
    public void initData() {
        super.start();
        UserHelper.refreshContacts();
    }

    @Override
    public void onSuccess(List<UserEntity> users) {
        if (getView() == null || getView().getRecyclerAdapter() == null) {
            return;
        }
        List<UserEntity> old = getView().getRecyclerAdapter().getItems();

        // 与当前显示的数据进行比较刷新
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        refreshData(result, users);
        getView().firstInitDataSuccess();
    }
}
