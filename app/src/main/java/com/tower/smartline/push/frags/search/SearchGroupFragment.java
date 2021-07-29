package com.tower.smartline.push.frags.search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.common.app.PresenterFragment;
import com.tower.smartline.factory.model.response.GroupCard;
import com.tower.smartline.factory.presenter.search.ISearchContract;
import com.tower.smartline.factory.presenter.search.SearchGroupPresenter;
import com.tower.smartline.push.activities.SearchActivity;
import com.tower.smartline.push.databinding.FragmentSearchGroupBinding;

import java.util.List;

/**
 * SearchGroupFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/12 16:05
 */
public class SearchGroupFragment extends PresenterFragment<ISearchContract.Presenter>
        implements SearchActivity.ISearchFragment, ISearchContract.GroupView, View.OnClickListener {
    private static final String TAG = SearchGroupFragment.class.getName();

    FragmentSearchGroupBinding mBinding;

    public SearchGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public ISearchContract.Presenter initPresenter() {
        return new SearchGroupPresenter(this);
    }

    @NonNull
    @Override
    protected View initBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        if (mBinding == null) {
            mBinding = FragmentSearchGroupBinding.inflate(inflater, container, false);
        }
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        // 设置空布局 TODO 绑定数据
        setEmptyView(mBinding.empty);

        // 点击监听初始化
        mBinding.layCreate.setOnClickListener(this);
    }

    @Override
    public void searchGroupSuccess(List<GroupCard> groupCards) {

    }

    @Override
    public void search(@Nullable String content) {

    }

    private void onCreateClick() {
        Log.i(TAG, "onCreateClick");
    }

    @Override
    protected void destroyBinding() {
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        int id = v.getId();
        if (id == mBinding.layCreate.getId()) {
            // 创建群聊点击
            onCreateClick();
        } else {
            Log.w(TAG, "onClick: illegal param: " + id);
        }
    }
}
