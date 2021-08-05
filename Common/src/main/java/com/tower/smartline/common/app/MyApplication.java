package com.tower.smartline.common.app;

import android.app.Application;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.annotation.StringRes;

import net.qiujuer.genius.kit.handler.Run;

import java.io.File;

/**
 * 自定义Application工具类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/8 2:33
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    /**
     * 获取单例
     *
     * @return Application
     */
    public static MyApplication getInstance() {
        return sInstance;
    }

    /**
     * 获取缓存文件夹
     *
     * @return 当前APP缓存文件夹地址
     */
    public static File getCacheDirFile() {
        return sInstance.getCacheDir();
    }

    /**
     * 获取头像的临时文件
     *
     * @return 头像的临时文件
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File getPortraitTmpFile() {
        File dir = new File(getCacheDirFile(), "portrait");
        dir.mkdirs();

        // 删除旧的缓存文件
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                file.delete();
            }
        }

        // 返回以开机时间节点为名的文件
        File path = new File(dir, SystemClock.uptimeMillis() + ".jpg");
        return path.getAbsoluteFile();
    }

    /**
     * 显示全局Toast
     *
     * @param msg 字符串
     */
    public static void showToast(final String msg) {
        // 确保在Ui线程执行
        Run.onUiAsync(() -> {
            Toast.makeText(sInstance, msg, Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * 显示全局Toast
     *
     * @param msgId 字符串资源Id
     */
    public static void showToast(@StringRes int msgId) {
        showToast(sInstance.getString(msgId));
    }
}
