package com.tower.smartline.push.frags.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.tower.smartline.common.app.PresenterFragment;
import com.tower.smartline.factory.model.response.GroupCard;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.presenter.search.ISearchContract;
import com.tower.smartline.push.activities.SearchActivity;

import java.util.List;

/**
 * SearchMainFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/13 2:39
 */
public class SearchMainFragment extends PresenterFragment<ISearchContract.Presenter>
        implements SearchActivity.ISearchFragment, ISearchContract.MainView {
    @Override
    protected ISearchContract.Presenter initPresenter() {
        return null;
    }

    @NonNull
    @Override
    protected View initBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void search(String content) {

    }

    @Override
    public void searchUserSuccess(List<UserCard> userCards) {

    }

    @Override
    public void searchGroupSuccess(List<GroupCard> groupCards) {

    }

    @Override
    protected void destroyBinding() {

    }
}
