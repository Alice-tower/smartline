package com.tower.smartline.common.app;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.presenter.IBaseContract;

/**
 * MVP模式 View ToolbarActivity抽象类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>z
 * @since 2021/7/30 10:06
 */
public abstract class PresenterToolbarActivity<P extends IBaseContract.Presenter>
        extends ToolbarActivity implements IBaseContract.View<P> {
    private P mPresenter;

    @Override
    protected void initWidget() {
        // 在初始化控件之前初始化Presenter
        mPresenter = initPresenter();
        super.initWidget();
    }

    @Override
    public void showError(int str) {
        if (mEmptyView != null) {
            mEmptyView.showError();
        }
        MyApplication.showToast(str);
    }

    @Override
    public void showLoading() {
        if (mEmptyView != null) {
            mEmptyView.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        hideLoading(true);
    }

    /**
     * 隐藏Loading
     *
     * @param isOk 是否成功加载不为空的数据
     */
    public void hideLoading(boolean isOk) {
        if (mEmptyView != null) {
            mEmptyView.showOkOrEmpty(isOk);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }

    /**
     * 获取绑定的Presenter层实例
     *
     * @return Presenter
     */
    @NonNull
    protected final P getPresenter() {
        if (mPresenter == null) {
            throw new IllegalStateException("PresenterToolbarActivity " + this + " didn't bind a presenter.");
        }
        return mPresenter;
    }

    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }
}
