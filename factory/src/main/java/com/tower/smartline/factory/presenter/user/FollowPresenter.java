package com.tower.smartline.factory.presenter.user;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.data.helper.UserHelper;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;

/**
 * FollowPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/15 20:50
 */
public class FollowPresenter extends BasePresenter<IFollowContract.View>
        implements IFollowContract.Presenter, IDataSource.Callback<UserCard> {
    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public FollowPresenter(@NonNull IFollowContract.View view) {
        super(view);
    }

    @Override
    public void follow(String id) {
        start();
        UserHelper.follow(id, this);
    }

    @Override
    public void onSuccess(UserCard userCard) {
        Run.onUiAsync(() -> {
            if (getView() != null) {
                getView().followSuccess(userCard);
            }
        });
    }

    @Override
    public void onFailure(int strRes) {
        Run.onUiAsync(() -> {
            if (getView() != null) {
                getView().showError(strRes);
            }
        });
    }
}
