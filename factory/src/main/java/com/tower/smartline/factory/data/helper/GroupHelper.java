package com.tower.smartline.factory.data.helper;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.tower.smartline.factory.model.db.GroupEntity;

/**
 * GroupHelper
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/5 3:27
 */
public class GroupHelper {
    private static final String TAG = GroupHelper.class.getName();

    private GroupHelper() {
    }

    /**
     * 查询指定Id的群组信息 (优先本地，其次网络) (同步返回)
     *
     * @param id 群组Id
     * @return GroupEntity
     */
    @Nullable
    public static GroupEntity infoFirstOfLocal(String id) {
        if (TextUtils.isEmpty(id)) {
            Log.w(TAG, "infoFirstOfLocal: id == null");
            return null;
        }

        // TODO 群组模块
        // GroupEntity groupEntity = infoFromLocal(id);
        // if (groupEntity == null) {
        //     // 网络查询
        //     return info(id);
        // }
        // return groupEntity;

        return null;
    }
}
