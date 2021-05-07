package com.tower.smartline.common.tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Ui工具类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/8 0:32
 */
public class UiTool {
    private static final String TAG = UiTool.class.getName();

    private static final String ANDROID = "android";

    private static final String DIMEN = "dimen";

    private static final String STATUS_BAR_HEIGHT = "status_bar_height";

    private static final String NAVIGATION_BAR_HEIGHT = "navigation_bar_height";

    private static final int ILLEGAL_VALUE = -1;

    private static int sStatusBarHeight = ILLEGAL_VALUE;

    /**
     * 获取顶部状态栏高度
     *
     * @param activity Activity
     * @return 状态栏高度像素值
     */
    public static int getStatusBarHeight(Activity activity) {
        if (sStatusBarHeight != ILLEGAL_VALUE || activity == null) {
            return sStatusBarHeight;
        }
        try {
            final Resources res = activity.getResources();
            if (res == null) {
                return sStatusBarHeight;
            }

            // 通过系统尺寸资源获取
            int resourceId = res.getIdentifier(STATUS_BAR_HEIGHT, DIMEN, ANDROID);

            // 未获取到则 通过R类反射获取
            if (resourceId <= 0) {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                resourceId = Integer.parseInt(clazz.getField(STATUS_BAR_HEIGHT)
                        .get(object).toString());
            }

            // 获取像素值
            if (resourceId > 0) {
                sStatusBarHeight = res.getDimensionPixelSize(resourceId);
            }

            // 未获取到则 如果还是未拿到
            if (sStatusBarHeight <= 0) {
                // 通过Window拿取
                Rect rectangle = new Rect();
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
                if (rectangle.top > 0) {
                    sStatusBarHeight = rectangle.top;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                | NoSuchFieldException | IllegalArgumentException | Resources.NotFoundException e) {
            Log.e(TAG, "getStatusBarHeight: Exception");
        }
        return sStatusBarHeight;
    }

    /**
     * 获取底部导航栏高度
     *
     * @param context Context
     * @return 导航栏高度像素值
     */
    public static int getNavigationBarHeight(Context context) {
        try {
            if (context == null || context.getResources() == null) {
                return ILLEGAL_VALUE;
            }
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier(NAVIGATION_BAR_HEIGHT, DIMEN, ANDROID);
            if (resourceId <= 0) {
                return ILLEGAL_VALUE;
            }
            return resources.getDimensionPixelSize(resourceId);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "getNavigationBarHeight: Exception");
        }
        return ILLEGAL_VALUE;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity Activity
     * @return 屏幕宽度像素值
     */
    public static int getScreenWidth(Activity activity) {
        if (activity == null || activity.getResources() == null) {
            return ILLEGAL_VALUE;
        }
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        if (displayMetrics == null) {
            return ILLEGAL_VALUE;
        }
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity Activity
     * @return 屏幕高度像素值
     */
    public static int getScreenHeight(Activity activity) {
        if (activity == null || activity.getResources() == null) {
            return ILLEGAL_VALUE;
        }
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        if (displayMetrics == null) {
            return ILLEGAL_VALUE;
        }
        return displayMetrics.heightPixels;
    }
}
