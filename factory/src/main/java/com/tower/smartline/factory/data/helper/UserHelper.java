package com.tower.smartline.factory.data.helper;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.factory.data.dispatcher.DataCenter;
import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.data.helper.base.MyCallback;
import com.tower.smartline.factory.model.api.user.UpdateInfoModel;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.model.db.UserEntity_Table;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.model.response.base.ResponseModel;
import com.tower.smartline.factory.net.Network;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

/**
 * UserHelper
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/6 3:43
 */
public class UserHelper {
    private static final String TAG = UserHelper.class.getName();

    private UserHelper() {
    }

    /**
     * 用户个人信息更新
     *
     * @param model    UpdateInfoModel
     * @param callback 回调
     */
    public static void update(UpdateInfoModel model, IDataSource.Callback<UserCard> callback) {
        Log.i(TAG, "update: start");
        if (model == null) {
            Log.w(TAG, "update: model == null");
            return;
        }
        Network.remote()
                .userUpdate(model)
                .enqueue(new MyCallback<UserCard>(callback) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<UserCard>> call,
                                           @NonNull Response<ResponseModel<UserCard>> response) {
                        super.onResponse(call, response);
                        UserCard result = getResultOrHandled();
                        if (result == null) {
                            return;
                        }
                        DataCenter.dispatchUser(result);
                        callback.onSuccess(result);
                    }
                });
    }

    /**
     * 根据用户名模糊搜索
     *
     * @param name     被关注者的用户Id
     * @param callback 回调
     * @return Call 当前接口的调度者
     */
    @SuppressWarnings("rawtypes")
    public static Call search(@Nullable String name, IDataSource.Callback<List<UserCard>> callback) {
        Log.i(TAG, "search: start");
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        Call<ResponseModel<List<UserCard>>> call = Network.remote().userSearch(name);
        call.enqueue(new MyCallback<List<UserCard>>(callback) {
            @Override
            public void onResponse(@NonNull Call<ResponseModel<List<UserCard>>> call,
                                   @NonNull Response<ResponseModel<List<UserCard>>> response) {
                super.onResponse(call, response);
                List<UserCard> result = getResultOrHandled();
                if (result == null) {
                    return;
                }
                callback.onSuccess(result);
            }
        });
        return call;
    }

    /**
     * 关注 (加好友)
     *
     * @param id       被关注者的用户Id
     * @param callback 回调
     */
    public static void follow(String id, IDataSource.Callback<UserCard> callback) {
        Log.i(TAG, "follow: start");
        if (TextUtils.isEmpty(id)) {
            Log.w(TAG, "follow: id == null");
            return;
        }
        Network.remote()
                .userFollow(id)
                .enqueue(new MyCallback<UserCard>(callback) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<UserCard>> call,
                                           @NonNull Response<ResponseModel<UserCard>> response) {
                        super.onResponse(call, response);
                        UserCard result = getResultOrHandled();
                        if (result == null) {
                            return;
                        }

                        // 保存并通知联系人列表刷新
                        DataCenter.dispatchUser(result);
                        callback.onSuccess(result);
                    }
                });
    }

    /**
     * 获取联系人列表
     */
    public static void refreshContacts() {
        Log.i(TAG, "getContacts: start");
        Network.remote().userContacts().enqueue(
                new MyCallback<List<UserCard>>(null) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<List<UserCard>>> call, @NonNull Response<ResponseModel<List<UserCard>>> response) {
                        super.onResponse(call, response);
                        List<UserCard> result = getResultWithoutCallback();
                        if (result == null) {
                            return;
                        }
                        UserCard[] cards = result.toArray(new UserCard[0]);
                        DataCenter.dispatchUser(cards);
                    }
                }
        );
    }

    /**
     * 查询指定Id的用户信息 (网络) (异步回调)
     *
     * @param id       用户Id
     * @param callback 回调
     */
    public static void info(String id, IDataSource.Callback<UserEntity> callback) {
        Log.i(TAG, "info: start");
        if (TextUtils.isEmpty(id)) {
            Log.w(TAG, "info: id == null");
            return;
        }
        Network.remote()
                .userInfo(id)
                .enqueue(new MyCallback<UserCard>(callback) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<UserCard>> call,
                                           @NonNull Response<ResponseModel<UserCard>> response) {
                        super.onResponse(call, response);
                        UserCard result = getResultOrHandled();
                        if (result == null) {
                            return;
                        }
                        DataCenter.dispatchUser(result);
                        callback.onSuccess(result.toUserEntity());
                    }
                });
    }

    /**
     * 查询指定Id的用户信息 (网络) (同步返回)
     *
     * @param id 用户Id
     */
    @Nullable
    public static UserEntity info(String id) {
        Log.i(TAG, "info: start");
        if (TextUtils.isEmpty(id)) {
            Log.w(TAG, "info: id == null");
            return null;
        }
        try {
            Response<ResponseModel<UserCard>> response = Network.remote().userInfo(id).execute();
            Log.i(TAG, "info: " + response.body());
            if (response.body() != null) {
                UserCard result = response.body().getResult();
                DataCenter.dispatchUser(result);
                return result.toUserEntity();
            }
        } catch (IOException e) {
            Log.e(TAG, "info: Exception");
        }
        return null;
    }

    /**
     * 查询指定Id的用户信息 (优先本地，其次网络) (同步返回)
     *
     * @param id 用户Id
     * @return UserEntity
     */
    @Nullable
    public static UserEntity infoFirstOfLocal(String id) {
        if (TextUtils.isEmpty(id)) {
            Log.w(TAG, "infoFirstOfLocal: id == null");
            return null;
        }
        UserEntity userEntity = infoFromLocal(id);
        if (userEntity == null) {
            // 网络查询
            return info(id);
        }
        return userEntity;
    }

    /**
     * 查询指定Id的用户信息 (本地)
     *
     * @param id 用户Id
     */
    @Nullable
    private static UserEntity infoFromLocal(String id) {
        return SQLite.select()
                .from(UserEntity.class)
                .where(UserEntity_Table.id.eq(id))
                .querySingle();
    }
}
