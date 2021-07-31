package com.tower.smartline.factory.presenter.user;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.data.helper.UserHelper;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.persistence.Account;
import com.tower.smartline.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;

import java.util.Objects;

/**
 * PersonalInfoPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/30 16:24
 */
public class PersonalInfoPresenter extends BasePresenter<IPersonalInfoContract.View>
        implements IPersonalInfoContract.Presenter {
    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public PersonalInfoPresenter(@NonNull IPersonalInfoContract.View view) {
        super(view);
    }

    @Override
    public void loadPersonalInfo(String userId) {
        start();
        UserHelper.info(userId, new loadInfoCallback());
    }

    @Override
    public void follow(String id) {
        start();
        UserHelper.follow(id, new FollowCallback());
    }

    private class loadInfoCallback implements IDataSource.Callback<UserCard> {
        @Override
        public void onSuccess(UserCard userEntity) {
            Run.onUiAsync(() -> {
                if (getView() != null && userEntity != null) {
                    int type;
                    if (Objects.equals(userEntity.getId(), Account.getUserId())) {
                        type = IPersonalInfoContract.TYPE_SELF;
                    } else if (!userEntity.isFollow()) {
                        type = IPersonalInfoContract.TYPE_FOLLOW;
                    } else {
                        type = IPersonalInfoContract.TYPE_MESSAGE;
                    }
                    getView().loadSuccess(userEntity, type);
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

    private class FollowCallback implements IDataSource.Callback<UserCard> {
        @Override
        public void onSuccess(UserCard userCard) {
            Run.onUiAsync(() -> {
                if (getView() != null) {
                    getView().loadSuccess(userCard, IPersonalInfoContract.TYPE_MESSAGE);
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
}
