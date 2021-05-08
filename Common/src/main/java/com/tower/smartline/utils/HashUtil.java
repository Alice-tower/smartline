package com.tower.smartline.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hash工具类
 * 对字符串或文件进行Hash算法，返回MD5值
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/8 21:43
 */
public class HashUtil {
    private static final String TAG = HashUtil.class.getName();

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 获取字符串的MD5值
     *
     * @param str String
     * @return HashCode
     */
    public static String getMD5String(String str) {
        if (str == null) {
            return null;
        }
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            return convertToHexString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "getMD5String: Exception");
        }
        return null;
    }

    /**
     * 获取文件的MD5值
     *
     * @param file File
     * @return HashCode
     */
    public static String getMD5String(File file) {
        if (file == null) {
            return null;
        }
        MessageDigest md5;
        InputStream in = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int numRead;
            in = new FileInputStream(file);
            while ((numRead = in.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            return convertToHexString(md5.digest());
        } catch (IOException | NoSuchAlgorithmException e) {
            Log.e(TAG, "getMD5String: Exception");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, "getMD5String: Exception");
                }
            }
        }
        return null;
    }

    private static String convertToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte a : b) {
            sb.append(HEX_DIGITS[(a & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[a & 0x0f]);
        }
        return sb.toString();
    }
}
