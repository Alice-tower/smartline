package com.tower.smartline.factory.presenter.search;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.presenter.BasePresenter;

/**
 * SearchGroupPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/12 16:14
 */
public class SearchGroupPresenter extends BasePresenter<ISearchContract.GroupView>
        implements ISearchContract.Presenter {
    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public SearchGroupPresenter(@NonNull ISearchContract.GroupView view) {
        super(view);
    }

    @Override
    public void search(String content) {

    }
}
