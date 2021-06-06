package com.tower.smartline.push.frags.account;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.tower.smartline.common.app.Fragment;
import com.tower.smartline.push.R;
import com.tower.smartline.push.databinding.FragmentLoginBinding;

/**
 * LoginFragment
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/7 3:25
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getName();

    private FragmentLoginBinding mBinding;

    private boolean mIsLogin = true;

    public LoginFragment() {
        // Required empty public constructor
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
        if (mIsLogin) {
            // TODO Presenter实现 登录逻辑
        } else {
            // TODO Presenter实现 注册逻辑
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
