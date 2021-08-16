package com.tower.smartline.factory.data.helper;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.R;
import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.data.dispatcher.DataCenter;
import com.tower.smartline.factory.data.helper.base.MyCallback;
import com.tower.smartline.factory.model.api.account.LoginModel;
import com.tower.smartline.factory.model.api.account.RegisterModel;
import com.tower.smartline.factory.model.response.AccountCard;
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

    // 防止推送初始化频繁向服务器绑定设备Id
    public static boolean sFirstBind = false;

    private AccountHelper() {
    }

    /**
     * 登录
     *
     * @param model    LoginModel
     * @param callback 回调
     */
    public static void login(LoginModel model, IDataSource.Callback<UserCard> callback) {
        Log.i(TAG, "login: start");
        if (model == null) {
            Log.w(TAG, "login: model == null");
            return;
        }
        Network.remote()
                .accountLogin(model)
                .enqueue(new MyCallback<AccountCard>(callback) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<AccountCard>> call,
                                           @NonNull Response<ResponseModel<AccountCard>> response) {
                        super.onResponse(call, response);
                        AccountCard result = getResultOrHandled();
                        if (result == null) {
                            return;
                        }
                        handleData(result, callback);
                    }
                });
    }

    /**
     * 注册
     *
     * @param model    RegisterModel
     * @param callback 回调
     */
    public static void register(RegisterModel model, IDataSource.Callback<UserCard> callback) {
        Log.i(TAG, "register: start");
        if (model == null) {
            Log.w(TAG, "register: model == null");
            return;
        }
        Network.remote()
                .accountRegister(model)
                .enqueue(new MyCallback<AccountCard>(callback) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<AccountCard>> call,
                                           @NonNull Response<ResponseModel<AccountCard>> response) {
                        super.onResponse(call, response);
                        AccountCard result = getResultOrHandled();
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
                .enqueue(new MyCallback<AccountCard>(callback) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<AccountCard>> call,
                                           @NonNull Response<ResponseModel<AccountCard>> response) {
                        super.onResponse(call, response);
                        AccountCard result = getResultWithoutCallback();
                        if (result == null) {
                            return;
                        }
                        if (callback == null) {
                            sFirstBind = true;
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
    private static void handleData(@NonNull AccountCard rsp,
                                   @NonNull IDataSource.Callback<UserCard> callback) {
        UserCard userCard = rsp.getUserCard();
        if (userCard == null) {
            Log.w(TAG, "userCard == null");
            callback.onFailure(R.string.toast_net_service_exception);
            return;
        }

        // 存储用户信息到数据库
        DataCenter.dispatchUser(userCard);

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
