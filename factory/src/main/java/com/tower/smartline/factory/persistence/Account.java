package com.tower.smartline.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.tower.smartline.common.app.Application;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.model.db.UserEntity_Table;

import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Account
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/24 0:26
 */
public class Account {
    private static final String TAG = Account.class.getName();

    private static final String KEY_PUSH_ID = "PUSH_ID";

    private static final String KEY_IS_BIND = "IS_BIND";

    private static final String KEY_TOKEN = "TOKEN";

    private static final String KEY_USER_ID = "USER_ID";

    // 设备的推送Id
    private static String pushId;

    // 设备Id是否已经绑定到了服务器
    private static boolean isBind;

    // 登录状态的Token 用于接口请求
    private static String token;

    // 登录的用户ID
    private static String userId;

    /**
     * 存储数据到XML 数据持久化
     */
    private static void save() {
        SharedPreferences sp = Application.getInstance()
                .getSharedPreferences(TAG, Context.MODE_PRIVATE);
        sp.edit()
                .putString(KEY_PUSH_ID, pushId)
                .putBoolean(KEY_IS_BIND, isBind)
                .putString(KEY_TOKEN, token)
                .putString(KEY_USER_ID, userId)
                .apply();
    }

    /**
     * 加载数据
     */
    public static void load() {
        SharedPreferences sp = Application.getInstance()
                .getSharedPreferences(TAG, Context.MODE_PRIVATE);
        pushId = sp.getString(KEY_PUSH_ID, "");
        isBind = sp.getBoolean(KEY_IS_BIND, false);
        token = sp.getString(KEY_TOKEN, "");
        userId = sp.getString(KEY_USER_ID, "");
    }

    /**
     * 用户登录时 存储用户Id和Token
     *
     * @param id    登录用户的Id
     * @param token 登录用户的Token
     */
    public static void loginSave(String id, String token) {
        Account.userId = id;
        Account.token = token;
        save();
    }

    /**
     * 返回当前账户是否登录
     * 即用户Id和Token是否皆不为空
     *
     * @return 是否登录
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(token);
    }

    /**
     * 是否已经完善了用户信息
     * 校验头像和性别
     *
     * @return True 是完成了
     */
    public static boolean isComplete() {
        if (isLogin()) {
            UserEntity self = getUser();
            return !TextUtils.isEmpty(self.getPortrait())
                    && self.getSex() != UserEntity.SEX_TYPE_UNKNOWN;
        }
        Log.w(TAG, "isComplete: isLogin == false");
        return false;
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return UserEntity
     */
    public static UserEntity getUser() {
        // TODO 该方法考虑迁移到DB事务包下
        if (TextUtils.isEmpty(userId)) {
            return new UserEntity();
        }

        // 不为空则从数据库中查询
        return SQLite.select()
                .from(UserEntity.class)
                .where(UserEntity_Table.id.eq(userId))
                .querySingle();
    }

    public static String getPushId() {
        return pushId;
    }

    public static void setPushId(String pushId) {
        Account.pushId = pushId;
        save();
    }

    public static boolean isBind() {
        return isBind;
    }

    public static void setBind(boolean isBind) {
        Account.isBind = isBind;
        save();
    }

    public static String getToken() {
        return token;
    }

    public static String getUserId() {
        return userId;
    }
}
