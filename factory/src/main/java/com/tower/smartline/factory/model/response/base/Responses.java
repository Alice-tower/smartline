package com.tower.smartline.factory.model.response.base;

import android.util.Log;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.R;
import com.tower.smartline.factory.data.IDataSource;

/**
 * ResponseModel工具类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/10 11:31
 */
@SuppressWarnings("rawtypes")
public class Responses {
    private static final String TAG = Responses.class.getName();

    private Responses() {
    }

    public static boolean isSuccess(ResponseModel rsp, @NonNull IDataSource.FailureCallback callback) {
        if (rsp == null) {
            Log.w(TAG, "isSuccess: rsp == null");
            callback.onFailure(R.string.toast_net_service_exception);
            return false;
        }

        // 打印错误码 错误说明 服务器响应时间 安全方面考虑 不含result
        Log.i(TAG, "isSuccess: " + rsp);

        // 错误码为不成功 调用失败回调显示Toast
        if (rsp.getCode() != ResponseCode.SUCCESS) {
            decodeRspCode(rsp.getCode(), callback);
            return false;
        }
        return true;
    }

    /**
     * 根据不同的错误码 传递给失败回调相应的Toast
     *
     * @param rspCode  失败错误码
     * @param callback 失败的回调
     */
    private static void decodeRspCode(int rspCode, @NonNull IDataSource.FailureCallback callback) {
        switch (rspCode) {
            case ResponseCode.UNKNOWN_ERROR:
                callback.onFailure(R.string.toast_net_unknown_error);
                break;
            case ResponseCode.SERVICE_ERROR:
                callback.onFailure(R.string.toast_net_service_exception);
                break;
            case ResponseCode.AUTH_TOKEN_INVALID:
                callback.onFailure(R.string.toast_net_auth_token_invalid);
                break;
            case ResponseCode.PARAM_ILLEGAL:
                callback.onFailure(R.string.toast_net_param_illegal);
                break;
            case ResponseCode.PARAM_PHONE_EXIST:
                callback.onFailure(R.string.toast_net_param_phone_exist);
                break;
            case ResponseCode.PARAM_NAME_EXIST:
                callback.onFailure(R.string.toast_net_param_name_exist);
                break;
            case ResponseCode.PARAM_ACCOUNT_INVALID:
                callback.onFailure(R.string.toast_net_param_account_invalid);
                break;
            default:
                callback.onFailure(R.string.toast_net_something_wrong);
                break;
        }
    }
}
