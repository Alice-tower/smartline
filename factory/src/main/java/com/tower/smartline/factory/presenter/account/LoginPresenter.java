package com.tower.smartline.factory.presenter.account;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.tower.smartline.common.Constants;
import com.tower.smartline.factory.R;
import com.tower.smartline.factory.presenter.BasePresenter;

/**
 * 登录Presenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/8 0:54
 */
public class LoginPresenter extends BasePresenter<ILoginContract.View>
        implements ILoginContract.Presenter {
    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public LoginPresenter(@NonNull ILoginContract.View view) {
        super(view);
    }

    @Override
    public void login(String phone, String password) {
        start();
        if (!checkString(phone, password)) {
            return;
        }

        // TODO 登录逻辑
        getView().submitSuccess();
    }

    @Override
    public void register(String phone, String password, String username) {
        start();
        if (!checkString(phone, password) || !checkString(username)) {
            return;
        }

        // TODO 注册逻辑
        getView().submitSuccess();
    }

    @Override
    public boolean checkString(String phone, String password) {
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            // 手机号或密码为空
            getView().showError(R.string.data_account_parameters_empty);
            return false;
        }
        if (!phone.matches(Constants.REGEX_PHONE)) {
            // 手机号非法
            getView().showError(R.string.data_account_invalid_parameter_phone);
            return false;
        }
        if (!password.matches(Constants.REGEX_PASSWORD)) {
            // 密码非法
            getView().showError(R.string.data_account_invalid_parameter_password);
            return false;
        }
        return true;
    }

    @Override
    public boolean checkString(String username) {
        if (TextUtils.isEmpty(username)) {
            // 用户名为空
            getView().showError(R.string.data_account_parameter_empty);
            return false;
        }
        if (!username.matches(Constants.REGEX_USERNAME)) {
            // 用户名非法
            getView().showError(R.string.data_account_invalid_parameter_username);
            return false;
        }
        return true;
    }
}
