package com.tower.smartline.push.frags.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.common.app.PresenterFragment;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.presenter.search.ISearchContract;
import com.tower.smartline.factory.presenter.search.SearchUserPresenter;
import com.tower.smartline.push.activities.SearchActivity;
import com.tower.smartline.push.databinding.FragmentSearchUserBinding;

import java.util.List;

/**
 * SearchUserFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/12 16:05
 */
public class SearchUserFragment extends PresenterFragment<ISearchContract.Presenter>
        implements SearchActivity.ISearchFragment, ISearchContract.UserView {
    private FragmentSearchUserBinding mBinding;

    @Override
    protected ISearchContract.Presenter initPresenter() {
        return new SearchUserPresenter(this);
    }

    @NonNull
    @Override
    protected View initBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        if (mBinding == null) {
            mBinding = FragmentSearchUserBinding.inflate(inflater, container, false);
        }
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        // 设置空布局
        setEmptyView(mBinding.empty);
    }

    @Override
    public void searchUserSuccess(List<UserCard> userCards) {

    }

    @Override
    public void search(@Nullable String content) {

    }

    @Override
    protected void destroyBinding() {
        mBinding = null;
    }
}
