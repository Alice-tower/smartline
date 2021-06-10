package com.tower.smartline.factory.net;

import androidx.annotation.NonNull;

import com.tower.smartline.common.Config;
import com.tower.smartline.factory.Factory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 网络请求的封装
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 9:40
 */
public class Network {
    // Client超时秒数
    private static final int CLIENT_TIMEOUT_SECONDS = 20;

    // Network单例
    private static final Network INSTANCE = new Network();

    // Client初始化锁
    private static final Object CLIENT_LOCK = new Object();

    // Retrofit初始化锁
    private static final Object RETROFIT_LOCK = new Object();

    private OkHttpClient mClient;

    private Retrofit mRetrofit;

    private Network() {
    }

    /**
     * 返回一个请求代理
     *
     * @return RemoteService
     */
    public static IRemoteService remote() {
        return INSTANCE.getRetrofit().create(IRemoteService.class);
    }

    private Retrofit getRetrofit() {
        if (mRetrofit != null) {
            return mRetrofit;
        }
        synchronized (RETROFIT_LOCK) {
            if (mRetrofit == null) {
                // 初始化Retrofit 设置服务端请求地址 设置Client 设置Json转换器
                mRetrofit = new Retrofit.Builder()
                        .baseUrl(Config.API_URL)
                        .client(getClient())
                        .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                        .build();
            }
        }
        return mRetrofit;
    }

    private OkHttpClient getClient() {
        if (mClient != null) {
            return mClient;
        }
        synchronized (CLIENT_LOCK) {
            if (mClient == null) {
                // 初始化OkHttpClient 设置超时时间 设置拦截器
                mClient = new OkHttpClient.Builder()
                        .connectTimeout(CLIENT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                        .writeTimeout(CLIENT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                        .readTimeout(CLIENT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                        .addInterceptor(new Interceptor() {
                            @NonNull
                            @Override
                            public Response intercept(@NonNull Chain chain) throws IOException {
                                // 拦截请求添加Header TODO 数据库待完善 添加Token
                                Request original = chain.request();
                                Request.Builder newBuilder = original.newBuilder();
                                newBuilder.addHeader("Content-Type", "application/json");
                                Request newRequest = newBuilder.build();
                                return chain.proceed(newRequest);
                            }
                        })
                        .build();
            }
        }
        return mClient;
    }
}
