package com.tower.smartline.factory.data.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.model.api.account.LoginModel;
import com.tower.smartline.factory.model.api.account.RegisterModel;
import com.tower.smartline.factory.model.response.AccountRspModel;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.model.response.base.ResponseModel;
import com.tower.smartline.factory.net.Network;

import retrofit2.Call;
import retrofit2.Response;

/**
 * AccountHelper
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 6:23
 */
public class AccountHelper {
    private static final String TAG = AccountHelper.class.getName();

    /**
     * 登录接口
     *
     * @param model    LoginModel
     * @param callback 回调
     */
    public static void login(LoginModel model, IDataSource.Callback<UserCard> callback) {
        Log.i(TAG, "login: start");
        if (model == null || callback == null) {
            Log.w(TAG, "login: model == null || callback == null");
            return;
        }
        Network.remote()
                .accountLogin(model)
                .enqueue(new MyCallback<AccountRspModel>(callback) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<AccountRspModel>> call, @NonNull Response<ResponseModel<AccountRspModel>> response) {
                        super.onResponse(call, response);
                        AccountRspModel result = getResultOrHandled();
                        if (result == null) {
                            return;
                        }
                        callback.onSuccess(result.getUserCard());
                    }
                });
    }

    /**
     * 注册接口
     *
     * @param model    RegisterModel
     * @param callback 回调
     */
    public static void register(RegisterModel model, IDataSource.Callback<UserCard> callback) {
        Log.i(TAG, "register: start");
        if (model == null || callback == null) {
            Log.w(TAG, "register: model == null || callback == null");
            return;
        }
        Network.remote()
                .accountRegister(model)
                .enqueue(new MyCallback<AccountRspModel>(callback) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<AccountRspModel>> call, @NonNull Response<ResponseModel<AccountRspModel>> response) {
                        super.onResponse(call, response);
                        AccountRspModel result = getResultOrHandled();
                        if (result == null) {
                            return;
                        }
                        callback.onSuccess(result.getUserCard());
                    }
                });
    }
}
