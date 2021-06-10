package com.tower.smartline.factory.model.response.base;

/**
 * 响应错误码
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 6:43
 */
class ResponseCode {
    private ResponseCode() {
    }

    /**
     * 响应成功
     */
    static final int SUCCESS = 1;

    /**
     * 未知错误
     */
    static final int UNKNOWN_ERROR = -1;

    /**
     * 服务器异常
     */
    static final int SERVICE_ERROR = -2;

    /**
     * 认证错误 Token无效
     */
    static final int AUTH_TOKEN_INVALID = 901;

    /**
     * 参数非法
     */
    static final int PARAM_ILLEGAL = 1000;

    /**
     * 参数非法 手机号已存在
     */
    static final int PARAM_PHONE_EXIST = 1001;

    /**
     * 参数非法 用户名已存在
     */
    static final int PARAM_NAME_EXIST = 1002;

    /**
     * 参数非法 手机号或密码错误
     */
    static final int PARAM_ACCOUNT_INVALID = 1003;

    /**
     * 查询错误 该用户不存在
     */
    static final int SEARCH_NO_SUCH_USER = 2001;
}
