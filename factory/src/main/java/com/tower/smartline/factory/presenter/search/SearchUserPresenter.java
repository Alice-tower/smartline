package com.tower.smartline.factory.presenter.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.data.helper.UserHelper;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;

import java.util.List;

import retrofit2.Call;

/**
 * SearchUserPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/12 16:14
 */
public class SearchUserPresenter extends BasePresenter<ISearchContract.UserView>
        implements ISearchContract.Presenter, IDataSource.Callback<List<UserCard>> {
    @SuppressWarnings("rawtypes")
    private Call mSearchCall;

    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public SearchUserPresenter(@NonNull ISearchContract.UserView view) {
        super(view);
    }

    @Override
    public void search(@Nullable String content) {
        start();

        if (mSearchCall != null && !mSearchCall.isCanceled()) {
            // 如果存在上一次请求且没有被取消
            // 则取消请求
            mSearchCall.cancel();
        }
        mSearchCall = UserHelper.search(content, this);
    }

    @Override
    public void onSuccess(List<UserCard> userCards) {
        Run.onUiAsync(() -> {
            if (getView() != null) {
                getView().searchUserSuccess(userCards);
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
