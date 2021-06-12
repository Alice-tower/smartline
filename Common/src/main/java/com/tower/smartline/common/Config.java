package com.tower.smartline.common;

import android.text.TextUtils;

/**
 * 参数配置类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2020/10/29 4:43
 */
public class Config {
    private Config() {
    }

    /**
     * SmartService 网络请求地址
     */
    public static final String API_URL = "";

    /**
     * 阿里云OSS 终端地址
     */
    public static final String OSS_ENDPOINT = "";

    /**
     * 阿里云OSS 访问秘钥Id
     */
    public static final String OSS_ACCESS_KEY_ID = "";

    /**
     * 阿里云OSS 访问秘钥密码
     */
    public static final String OSS_ACCESS_KEY_SECRET = "";

    /**
     * 阿里云OSS 仓库名
     */
    public static final String OSS_BUCKET_NAME = "";

    /**
     * 判断Config参数是否存在空值
     * 提醒没有阅读README的开发者配置参数
     *
     * @return Config参数是否存在空值
     */
    public static boolean isEmpty() {
        return TextUtils.isEmpty(API_URL)
                || TextUtils.isEmpty(OSS_ENDPOINT)
                || TextUtils.isEmpty(OSS_ACCESS_KEY_ID)
                || TextUtils.isEmpty(OSS_ACCESS_KEY_SECRET)
                || TextUtils.isEmpty(OSS_BUCKET_NAME);
    }
}
