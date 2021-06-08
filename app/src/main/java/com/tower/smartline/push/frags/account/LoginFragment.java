package com.tower.smartline.push.frags.account;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.tower.smartline.common.app.PresenterFragment;
import com.tower.smartline.factory.presenter.account.ILoginContract;
import com.tower.smartline.factory.presenter.account.LoginPresenter;
import com.tower.smartline.push.R;
import com.tower.smartline.push.activities.MainActivity;
import com.tower.smartline.push.databinding.FragmentLoginBinding;

/**
 * 登录Fragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/7 3:25
 */
public class LoginFragment extends PresenterFragment<ILoginContract.Presenter>
        implements ILoginContract.View, View.OnClickListener {
    private static final String TAG = LoginFragment.class.getName();

    private FragmentLoginBinding mBinding;

    private boolean mIsLogin = true;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected ILoginContract.Presenter initPresenter() {
        return new LoginPresenter(this);
    }

    @NonNull
    @Override
    protected View initBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        if (mBinding == null) {
            mBinding = FragmentLoginBinding.inflate(inflater, container, false);
        }
        return mBinding.getRoot();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        // 点击监听初始化
        mBinding.layGo.setOnClickListener(this);
        mBinding.btnSubmit.setOnClickListener(this);
    }

    private void onGoClick() {
        Log.i(TAG, "onGoClick");
        if (mIsLogin) {
            // 界面切换为注册模式
            mBinding.toolbar.setTitle(R.string.label_login_register);
            mBinding.viewSeparator2.setVisibility(View.VISIBLE);
            mBinding.layUsername.setVisibility(View.VISIBLE);
            mBinding.txtGo.setText(R.string.label_login_go_login);
            mBinding.btnSubmit.setText(R.string.label_login_register);
        } else {
            // 界面切换为登录模式
            mBinding.toolbar.setTitle(R.string.label_login_title);
            mBinding.viewSeparator2.setVisibility(View.GONE);
            mBinding.layUsername.setVisibility(View.GONE);
            mBinding.txtGo.setText(R.string.label_login_go_register);
            mBinding.btnSubmit.setText(R.string.label_login_title);
        }
        mIsLogin = !mIsLogin;
    }

    private void onSubmitClick() {
        Log.i(TAG, "onSubmitClick");
        String phone = mBinding.editPhone.getText().toString();
        String password = mBinding.editPassword.getText().toString();
        if (mIsLogin) {
            // Presenter登录逻辑
            getPresenter().login(phone, password);
        } else {
            String username = mBinding.editUsername.getText().toString();

            // Presenter注册逻辑
            getPresenter().register(phone, password, username);
        }
    }

    @Override
    public void submitSuccess() {
        // 跳转到MainActivity
        MainActivity.show(requireContext());

        // 关闭当前界面
        requireActivity().finish();
    }

    @Override
    public void showError(int str) {
        // 显示错误提示 界面恢复操作
        super.showError(str);
        mBinding.loading.stop();
        mBinding.btnSubmit.setEnabled(true);
        mBinding.layGo.setEnabled(true);
        mBinding.editPhone.setEnabled(true);
        mBinding.editPassword.setEnabled(true);
        if (!mIsLogin) {
            mBinding.editUsername.setEnabled(true);
        }
    }

    @Override
    public void showLoading() {
        // Loading 界面不可操作
        super.showLoading();
        mBinding.loading.start();
        mBinding.btnSubmit.setEnabled(false);
        mBinding.layGo.setEnabled(false);
        mBinding.editPhone.setEnabled(false);
        mBinding.editPassword.setEnabled(false);
        if (!mIsLogin) {
            mBinding.editUsername.setEnabled(false);
        }
    }

    @Override
    protected void destroyBinding() {
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBinding.layGo.getId()) {
            // 去注册/去登录点击
            onGoClick();
        } else if (id == mBinding.btnSubmit.getId()) {
            // 登录/注册点击
            onSubmitClick();
        } else {
            Log.w(TAG, "onClick: illegal param");
        }
    }
}
