package com.tower.smartline.factory.data.helper;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.R;
import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.model.api.account.LoginModel;
import com.tower.smartline.factory.model.api.account.RegisterModel;
import com.tower.smartline.factory.model.response.AccountRspModel;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.model.response.base.ResponseModel;
import com.tower.smartline.factory.net.Network;
import com.tower.smartline.factory.persistence.Account;

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
                    public void onResponse(@NonNull Call<ResponseModel<AccountRspModel>> call,
                                           @NonNull Response<ResponseModel<AccountRspModel>> response) {
                        super.onResponse(call, response);
                        AccountRspModel result = getResultOrHandled();
                        if (result == null) {
                            return;
                        }
                        handleData(result, callback);
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
                    public void onResponse(@NonNull Call<ResponseModel<AccountRspModel>> call,
                                           @NonNull Response<ResponseModel<AccountRspModel>> response) {
                        super.onResponse(call, response);
                        AccountRspModel result = getResultOrHandled();
                        if (result == null) {
                            return;
                        }
                        handleData(result, callback);
                    }
                });
    }

    /**
     * 绑定设备Id
     *
     * @param callback 回调
     */
    public static void bindPush(IDataSource.Callback<UserCard> callback) {
        Log.i(TAG, "bindPush: start");
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId)) {
            Log.w(TAG, "bindPush: pushId == null");
            return;
        }
        Network.remote()
                .accountBind(pushId)
                .enqueue(new MyCallback<AccountRspModel>(callback) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<AccountRspModel>> call,
                                           @NonNull Response<ResponseModel<AccountRspModel>> response) {
                        super.onResponse(call, response);
                        AccountRspModel result = getResultOrHandled();
                        if (result == null) {
                            return;
                        }
                        if (!result.isBind()) {
                            // 向服务器请求绑定的结果仍为未绑定 防止死循环
                            Log.w(TAG, "onResponse: isBind == false");
                            callback.onFailure(R.string.toast_net_service_exception);
                            return;
                        }
                        handleData(result, callback);
                    }
                });
    }

    /**
     * 处理注册、登录和绑定返回的数据
     *
     * @param rsp      AccountRspModel
     * @param callback 回调
     */
    private static void handleData(@NonNull AccountRspModel rsp,
                                   @NonNull IDataSource.Callback<UserCard> callback) {
        UserCard userCard = rsp.getUserCard();
        if (userCard == null) {
            Log.w(TAG, "userCard == null");
            callback.onFailure(R.string.toast_net_service_exception);
            return;
        }

        // 存储用户信息到数据库
        userCard.toUserEntity().save();

        // 存储登录信息到SharedPreferences
        Account.loginSave(userCard.getId(), rsp.getToken());

        // 云侧返回的绑定状态 是否已绑定PushId
        if (rsp.isBind()) {
            Account.setBind(true);
            callback.onSuccess(userCard);
        } else {
            bindPush(callback);
        }
    }
}
