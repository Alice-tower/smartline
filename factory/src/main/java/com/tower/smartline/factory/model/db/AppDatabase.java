package com.tower.smartline.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * 数据库基本信息
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/27 6:49
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    /**
     * 名称
     */
    public static final String NAME = "AppDatabase";

    /**
     * 版本号
     */
    public static final int VERSION = 1;
}
