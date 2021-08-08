package com.tower.smartline.common;

/**
 * 公共常量类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/17 7:25
 */
public class Constants {
    private Constants() {
    }

    /**
     * 每页显示数据上限
     */
    public static final int MAX_RESULTS_EACH_PAGE = 100;

    /**
     * 手机号正则表达式 11位
     */
    public static final String REGEX_PHONE = "^1[3-9]\\d{9}$";

    /**
     * 密码正则表达式 6-16位
     */
    public static final String REGEX_PASSWORD = "^.{6,16}$";

    /**
     * 用户名正则表达式 1-10位
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{1,10}$";
}
