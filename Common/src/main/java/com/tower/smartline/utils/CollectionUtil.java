package com.tower.smartline.utils;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/8 21:21
 */
public class CollectionUtil {
    private static final String TAG = CollectionUtil.class.getName();

    private CollectionUtil() {
    }

    /**
     * List集合转换为数组
     *
     * @param items  List
     * @param tClass 数据的类型class
     * @param <T>    Class
     * @return 转换完成后的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<T> items, Class<T> tClass) {
        if (items == null || items.size() == 0 || tClass == null) {
            return null;
        }
        try {
            T[] array = (T[]) Array.newInstance(tClass, items.size());
            return items.toArray(array);
        } catch (NegativeArraySizeException e) {
            Log.e(TAG, "toArray: Exception");
        }
        return null;
    }

    /**
     * Set集合转换为数组
     *
     * @param items  Set
     * @param tClass 数据的类型class
     * @param <T>    Class
     * @return 转换完成后的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Set<T> items, Class<T> tClass) {
        if (items == null || items.size() == 0 || tClass == null) {
            return null;
        }
        try {
            T[] array = (T[]) Array.newInstance(tClass, items.size());
            return items.toArray(array);
        } catch (NegativeArraySizeException e) {
            Log.e(TAG, "toArray: Exception");
        }
        return null;
    }

    /**
     * 数组转换为HashSet集合
     *
     * @param items 数组
     * @param <T>   Class
     * @return 转换完成后的HashSet集合
     */
    public static <T> HashSet<T> toHashSet(T[] items) {
        if (items == null || items.length == 0) {
            return null;
        }
        HashSet<T> set = new HashSet<>();
        Collections.addAll(set, items);
        return set;
    }

    /**
     * 数组转换为ArrayList集合
     *
     * @param items 数组
     * @param <T>   Class
     * @return 转换完成后的ArrayList集合
     */
    public static <T> ArrayList<T> toArrayList(T[] items) {
        if (items == null || items.length == 0) {
            return null;
        }
        ArrayList<T> list = new ArrayList<>();
        Collections.addAll(list, items);
        return list;
    }
}
