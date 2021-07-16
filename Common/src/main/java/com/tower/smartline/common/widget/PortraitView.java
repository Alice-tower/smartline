package com.tower.smartline.common.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.tower.smartline.common.R;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

import de.hdodenhof.circleimageview.CircleImageView;

import net.qiujuer.genius.ui.compat.UiCompat;

/**
 * 圆形头像View
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/4/19 20:32
 */
public class PortraitView extends CircleImageView {
    /**
     * 男性默认头像标识
     */
    public static final String DEFAULT_MALE = "1";

    /**
     * 女性默认头像标识
     */
    public static final String DEFAULT_FEMALE = "2";

    private String mUrl;

    private Drawable mPlaceDrawable =
            new ColorDrawable(UiCompat.getColor(getResources(), R.color.textSecond));

    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 填充圆形头像View的内容
     *
     * @param manager Glide.with()
     * @param url     头像路径 (外网链接)
     */
    public void setup(RequestManager manager, String url) {
        mUrl = url == null ? "" : url;
        RequestBuilder<Drawable> builder;
        if (mUrl.equals(DEFAULT_MALE)) {
            // 男性默认头像
            builder = manager.load(R.drawable.default_portrait_male);
        } else if (mUrl.equals(DEFAULT_FEMALE)) {
            // 女性默认头像
            builder = manager.load(R.drawable.default_portrait_female);
        } else {
            // 用户上传头像
            builder = manager.load(mUrl);
        }
        builder.placeholder(mPlaceDrawable)
                .centerCrop()
                .dontAnimate()
                .into(this);
    }
}
