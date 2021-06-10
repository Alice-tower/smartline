package com.tower.smartline.factory;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Factory
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/14 11:34
 */
public class Factory {
    // 新建线程池线程数
    private static final int THREADS_NUM = 4;

    // Factory单例
    private static final Factory INSTANCE = new Factory();

    // 全局线程池
    private final Executor executor;

    // 全局Gson
    private final Gson gson;

    private Factory() {
        // 新建全局线程池
        executor = Executors.newFixedThreadPool(THREADS_NUM);

        // 新建全局Gson 设置时间格式 设置过滤器
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        // TODO 数据库待完善
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }

    /**
     * 线程池异步运行方法
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        INSTANCE.executor.execute(runnable);
    }


    /**
     * 获取全局Gson
     *
     * @return Gson
     */
    public static Gson getGson() {
        return INSTANCE.gson;
    }
}
