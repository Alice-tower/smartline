package com.tower.smartline.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件流工具类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/8 22:09
 */
public class StreamUtil {
    private static final String TAG = StreamUtil.class.getName();

    private StreamUtil() {
    }

    /**
     * 复制 File -> OutputStream
     * 转 InputStream -> OutputStream
     *
     * @param in           File
     * @param outputStream OutputStream
     * @return 操作是否成功
     */
    public static boolean copy(File in, OutputStream outputStream) {
        if (in == null || outputStream == null || !in.exists()) {
            return false;
        }
        InputStream stream;
        try {
            stream = new FileInputStream(in);
            return copy(stream, outputStream);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "copy: Exception");
        }
        return false;
    }

    /**
     * 复制 File1 -> File2
     * 转 InputStream -> File
     * 转 InputStream -> OutputStream
     *
     * @param in  File1
     * @param out File2
     * @return 操作是否成功
     */
    public static boolean copy(File in, File out) {
        if (in == null || out == null || !in.exists()) {
            return false;
        }
        InputStream stream;
        try {
            stream = new FileInputStream(in);
            return copy(stream, out);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "copy: Exception");
        }
        return false;
    }

    /**
     * 复制 InputStream -> File
     * 转 InputStream -> OutputStream
     *
     * @param inputStream InputStream
     * @param out         File
     * @return 操作是否成功
     */
    public static boolean copy(InputStream inputStream, File out) {
        if (inputStream == null || out == null) {
            return false;
        }
        try {
            if (!out.exists()) {
                File fileParentDir = out.getParentFile();
                if (fileParentDir == null) {
                    return false;
                }
                if (!fileParentDir.exists()) {
                    // 父目录不存在
                    if (!fileParentDir.mkdirs()) {
                        // 创建目录失败
                        return false;
                    }
                }
                if (!out.createNewFile()) {
                    return false;
                }
            }
            OutputStream outputStream;
            outputStream = new FileOutputStream(out);
            return copy(inputStream, outputStream);
        } catch (IOException e) {
            Log.e(TAG, "copy: Exception");
        }
        return false;
    }

    /**
     * 复制 InputStream -> OutputStream
     *
     * @param inputStream  InputStream
     * @param outputStream OutputStream
     * @return 操作是否成功
     */
    public static boolean copy(InputStream inputStream, OutputStream outputStream) {
        if (inputStream == null || outputStream == null) {
            return false;
        }
        byte[] buffer = new byte[1024];
        int realLength;
        try {
            while ((realLength = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, realLength);
            }
            return true;
        } catch (IOException e) {
            Log.e(TAG, "copy: Exception");
        } finally {
            close(inputStream);
            close(outputStream);
        }
        return false;
    }

    /**
     * 对流进行close操作
     *
     * @param closeables Closeable
     */
    public static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    Log.e(TAG, "close: Exception");
                }
            }
        }
    }

    /**
     * 删除某路径的文件
     *
     * @param path 文件路径
     * @return 操作是否成功
     */
    public static boolean delete(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        return file.exists() && file.delete();
    }
}
