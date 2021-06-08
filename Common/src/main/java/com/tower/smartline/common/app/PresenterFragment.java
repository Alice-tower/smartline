package com.tower.smartline.common.app;

import android.content.Context;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.presenter.IBaseContract;

/**
 * MVP模式 View Fragment抽象类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/8 10:10
 */
public abstract class PresenterFragment<P extends IBaseContract.Presenter> extends Fragment
        implements IBaseContract.View<P> {
    private P mPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mPresenter = initPresenter();
    }

    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    protected abstract P initPresenter();

    @Override
    public void showError(int str) {
        // TODO 占位布局 全屏错误提示
        Application.showToast(str);
    }

    @Override
    public void showLoading() {
        // TODO 占位布局 全屏加载提示
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
