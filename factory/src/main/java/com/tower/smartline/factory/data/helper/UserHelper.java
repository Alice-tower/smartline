package com.tower.smartline.factory.data.helper;

import android.util.Log;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.model.api.user.UpdateInfoModel;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.model.response.base.ResponseModel;
import com.tower.smartline.factory.net.Network;

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
        if (model == null || callback == null) {
            Log.w(TAG, "update: model == null || callback == null");
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
}
