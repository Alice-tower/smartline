package com.tower.smartline.factory.data.helper;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.model.api.user.UpdateInfoModel;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.model.response.base.ResponseModel;
import com.tower.smartline.factory.net.Network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * UserHelper
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/6 3:43
 */
public class UserHelper {
    private static final String TAG = UserHelper.class.getName();

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
                        result.toUserEntity().save();
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

                        // TODO 通知联系人列表刷新
                        result.toUserEntity().save();
                        callback.onSuccess(result);
                    }
                });
    }

    /**
     * 获取联系人列表
     *
     * @param callback 回调
     */
    public static void getContacts(IDataSource.Callback<List<UserEntity>> callback) {
        Log.i(TAG, "getContacts: start");
        Network.remote().userContacts().enqueue(
                new MyCallback<List<UserCard>>(callback) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<List<UserCard>>> call, @NonNull Response<ResponseModel<List<UserCard>>> response) {
                        super.onResponse(call, response);
                        List<UserCard> result = getResultOrHandled();
                        if (result == null) {
                            return;
                        }

                        // TODO 数据库处理 待完善
                        List<UserEntity> entities = new ArrayList<>();
                        for (UserCard card : result) {
                            entities.add(card.toUserEntity());
                        }

                        callback.onSuccess(entities);
                    }
                }
        );
    }
}
