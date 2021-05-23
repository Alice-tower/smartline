package com.tower.smartline.factory.net;

import android.net.Uri;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tower.smartline.common.Config;
import com.tower.smartline.common.app.Application;
import com.tower.smartline.utils.HashUtil;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.util.Date;

/**
 * 上传工具类
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/5/14 11:35
 */
public class UploadHelper {
    private static final String TAG = UploadHelper.class.getName();

    // 图片资源类型
    private static final int TYPE_IMAGE = 1;

    // 头像资源类型
    private static final int TYPE_PORTRAIT = 2;

    // 录音资源类型
    private static final int TYPE_AUDIO = 3;

    // 图片文件夹
    private static final String FOLDER_IMAGE = "image/";

    // 头像文件夹
    private static final String FOLDER_PORTRAIT = "portrait/";

    // 录音文件夹
    private static final String FOLDER_AUDIO = "audio/";

    // 斜杠
    private static final String CHAR_SLASH = "/";

    // 图片格式
    private static final String FORMAT_JPG = ".jpg";

    // 音频格式
    private static final String FORMAT_MP3 = ".mp3";

    private static OSS sOSSClient;

    private UploadHelper() {
    }

    /**
     * 初始化
     *
     * @return OSSClient
     */
    private static OSS getClient() {
        if (sOSSClient == null) {
            // 推荐使用OSSAuthCredentialsProvider token过期可以及时更新。
            // String stsServer = "STS应用服务器地址，例如http://abc.com";
            // OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);
            OSSCredentialProvider credentialProvider =
                    new OSSPlainTextAKSKCredentialProvider(Config.OSS_ACCESS_KEY_ID, Config.OSS_ACCESS_KEY_SECRET);
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(15 * 1000); // 连接超时
            conf.setSocketTimeout(15 * 1000); // socket超时
            conf.setMaxConcurrentRequest(5); // 最大并发请求数
            conf.setMaxErrorRetry(2); // 失败后最大重试次数
            sOSSClient = new OSSClient(Application.getInstance(), Config.OSS_ENDPOINT, credentialProvider, conf);
        }
        return sOSSClient;
    }

    /**
     * 上传
     *
     * @param objKey 上传后在服务器上的独立的KEY(MD5)
     * @param uri    上传文件的Uri
     * @return 存储的地址
     */
    private static String upload(String objKey, @NonNull Uri uri) {
        Log.d(TAG, "upload: uri: " + uri);
        if (TextUtils.isEmpty(objKey)) {
            return null;
        }

        // 构造上传请求
        PutObjectRequest request = new PutObjectRequest(Config.OSS_BUCKET_NAME, objKey, uri);
        try {
            OSS client = getClient();
            PutObjectResult putResult = client.putObject(request);
            Log.d(TAG, "upload: Success, ETag: " + putResult.getETag()
                    + " | RequestId: " + putResult.getRequestId());
            String url = client.presignPublicObjectURL(Config.OSS_BUCKET_NAME, objKey);
            if (!TextUtils.isEmpty(url)) {
                Log.d(TAG, "upload: url: " + url);
                return url;
            }
        } catch (ClientException e) {
            // 本地异常，如网络异常等
            Log.e(TAG, "upload: ClientException");
        } catch (ServiceException e) {
            // 服务异常
            Log.e(TAG, "upload: ServiceException, RequestId: " + e.getRequestId()
                    + " | ErrorCode: " + e.getErrorCode()
                    + " | HostId: " + e.getHostId()
                    + " | RawMessage" + e.getRawMessage());
        }
        return null;
    }

    /**
     * 上传图片
     *
     * @param uri 上传文件的Uri
     * @return 存储的地址
     */
    public static String uploadImage(Uri uri) {
        if (uri == null) {
            return null;
        }
        return upload(getObjKey(uri, TYPE_IMAGE), uri);
    }

    /**
     * 上传头像
     *
     * @param uri 上传文件的Uri
     * @return 存储的地址 如为null则出错
     */
    public static String uploadPortrait(Uri uri) {
        if (uri == null) {
            return null;
        }
        return upload(getObjKey(uri, TYPE_PORTRAIT), uri);
    }

    /**
     * 上传录音
     *
     * @param uri 上传文件的Uri
     * @return 存储的地址
     */
    public static String uploadAudio(Uri uri) {
        if (uri == null) {
            return null;
        }
        return upload(getObjKey(uri, TYPE_AUDIO), uri);
    }

    private static String getObjKey(@NonNull Uri uri, int fileType) {
        if (uri.getPath() == null) {
            return null;
        }

        // uri MD5
        String uriMd5 = HashUtil.getMD5String(new File(uri.getPath()));

        // 年月日
        String dateString = DateFormat.format("yyyyMMdd", new Date()).toString();

        String formatString = null;
        switch (fileType) {
            case TYPE_IMAGE:
                // image/20210517/...MD5.jpg
                formatString = FOLDER_IMAGE + dateString + CHAR_SLASH + uriMd5 + FORMAT_JPG;
                break;
            case TYPE_PORTRAIT:
                // portrait/20210517/...MD5.jpg
                formatString = FOLDER_PORTRAIT + dateString + CHAR_SLASH + uriMd5 + FORMAT_JPG;
                break;
            case TYPE_AUDIO:
                // audio/20210517/...MD5.mp3
                formatString = FOLDER_AUDIO + dateString + CHAR_SLASH + uriMd5 + FORMAT_MP3;
                break;
            default:
                Log.w(TAG, "getObjKey: illegal param");
        }
        return formatString;
    }
}
