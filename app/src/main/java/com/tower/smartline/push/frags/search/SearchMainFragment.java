package com.tower.smartline.push.frags.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.common.app.PresenterFragment;
import com.tower.smartline.factory.model.response.GroupCard;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.presenter.search.ISearchContract;
import com.tower.smartline.factory.presenter.search.SearchMainPresenter;
import com.tower.smartline.push.activities.SearchActivity;
import com.tower.smartline.push.databinding.FragmentSearchMainBinding;

import java.util.List;

/**
 * SearchMainFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/13 2:39
 */
public class SearchMainFragment extends PresenterFragment<ISearchContract.Presenter>
        implements SearchActivity.ISearchFragment, ISearchContract.MainView {
    private FragmentSearchMainBinding mBinding;

    public SearchMainFragment() {
        // Required empty public constructor
    }

    @Override
    public ISearchContract.Presenter initPresenter() {
        return new SearchMainPresenter(this);
    }

    @NonNull
    @Override
    protected View initBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        if (mBinding == null) {
            mBinding = FragmentSearchMainBinding.inflate(inflater, container, false);
        }
        return mBinding.getRoot();
    }

    @Override
    public void search(@Nullable String content) {

    }

    @Override
    public void searchUserSuccess(List<UserCard> userCards) {

    }

    @Override
    public void searchGroupSuccess(List<GroupCard> groupCards) {

    }

    @Override
    protected void destroyBinding() {
        mBinding = null;
    }
}
