package com.tower.smartline.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import de.hdodenhof.circleimageview.CircleImageView;

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

    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
