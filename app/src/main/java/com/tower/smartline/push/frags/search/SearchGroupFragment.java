package com.tower.smartline.push.frags.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.tower.smartline.common.app.PresenterFragment;
import com.tower.smartline.factory.model.response.GroupCard;
import com.tower.smartline.factory.presenter.search.ISearchContract;
import com.tower.smartline.push.activities.SearchActivity;

import java.util.List;

/**
 * SearchGroupFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/12 16:05
 */
public class SearchGroupFragment extends PresenterFragment<ISearchContract.Presenter>
        implements SearchActivity.ISearchFragment, ISearchContract.GroupView, View.OnClickListener {
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
    protected void destroyBinding() {

    }

    @Override
    public void searchGroupSuccess(List<GroupCard> groupCards) {

    }

    @Override
    public void search(String content) {

    }

    @Override
    public void onClick(View v) {

    }
}
