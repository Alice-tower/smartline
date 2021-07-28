package com.tower.smartline.factory.presenter.homepage;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.data.helper.UserHelper;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.presenter.BaseRecyclerPresenter;

import net.qiujuer.genius.kit.handler.Run;

import java.util.List;

/**
 * ContactPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/25 10:26
 */
public class ContactPresenter extends BaseRecyclerPresenter<UserEntity, IHomepageContract.View>
        implements IHomepageContract.Presenter, IDataSource.Callback<List<UserEntity>> {
    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public ContactPresenter(@NonNull IHomepageContract.View view) {
        super(view);
    }

    @Override
    public void initData() {
        super.start();

        UserHelper.getContacts(this);
    }

    @Override
    public void onSuccess(List<UserEntity> users) {
        // TODO 本地数据库与比较刷新
        refreshData(users);
    }

    @Override
    public void onFailure(int strRes) {
        // TODO 首页初始化网络请求 考虑做无感知 不设计失败回调
        Run.onUiAsync(() -> {
            if (getView() != null) {
                getView().showError(strRes);
            }
        });
    }
}
