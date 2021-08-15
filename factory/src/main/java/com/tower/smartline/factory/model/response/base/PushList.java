package com.tower.smartline.factory.model.response.base;

import android.util.Log;

import androidx.annotation.Nullable;

import com.tower.smartline.factory.Factory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 推送集合
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/15 14:06
 */
public class PushList {
    private static final String TAG = PushList.class.getName();

    private List<PushModel> pushModels;

    private PushList(List<PushModel> pushModels) {
        this.pushModels = pushModels;
    }

    public List<PushModel> getPushModels() {
        return pushModels;
    }

    public void setPushModels(List<PushModel> pushModels) {
        this.pushModels = pushModels;
    }

    /**
     * 将推送的Json字符串转化为PushModel数组
     *
     * @param json Json字符串
     * @return PushList
     */
    @Nullable
    public static PushList decode(String json) {
        Gson gson = Factory.getGson();
        Type type = new TypeToken<List<PushModel>>() {
        }.getType();
        try {
            List<PushModel> pushModels = gson.fromJson(json, type);
            if (pushModels.size() > 0) {
                return new PushList(pushModels);
            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "decode: Exception");
        }
        return null;
    }
}
