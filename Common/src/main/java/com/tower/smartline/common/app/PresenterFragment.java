package com.tower.smartline.common.app;

import android.content.Context;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.presenter.IBaseContract;

/**
 * MVP模式 View Fragment抽象类
 *
 * @param <P> MVP模式中的Presenter
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/8 10:10
 */
public abstract class PresenterFragment<P extends IBaseContract.Presenter> extends BaseFragment
        implements IBaseContract.View<P> {
    private P mPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mPresenter = initPresenter();
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
            throw new IllegalStateException("PresenterFragment " + this + " didn't bind a presenter.");
        }
        return mPresenter;
    }

    @Override
    public final void setPresenter(P presenter) {
        mPresenter = presenter;
    }
}
