package com.tower.smartline.factory.presenter.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.factory.presenter.BasePresenter;

/**
 * SearchMainPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/13 14:41
 */
public class SearchMainPresenter extends BasePresenter<ISearchContract.MainView>
        implements ISearchContract.Presenter {
    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public SearchMainPresenter(@NonNull ISearchContract.MainView view) {
        super(view);
    }

    @Override
    public void search(@Nullable String content) {

    }
}
