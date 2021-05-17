package com.tower.smartline.factory;

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

    private static final Factory sInstance = new Factory();

    private final Executor executor;

    private Factory() {
        // 新建线程池
        executor = Executors.newFixedThreadPool(THREADS_NUM);
    }

    /**
     * 线程池异步运行方法
     *
     * @param runnable Runnable
     */
    public static void runOnAsync(Runnable runnable) {
        sInstance.executor.execute(runnable);
    }
}
