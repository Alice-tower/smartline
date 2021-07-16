package com.tower.smartline.factory.presenter.user;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tower.smartline.common.widget.PortraitView;
import com.tower.smartline.factory.Factory;
import com.tower.smartline.factory.R;
import com.tower.smartline.factory.data.IDataSource;
import com.tower.smartline.factory.data.helper.UserHelper;
import com.tower.smartline.factory.model.api.user.UpdateInfoModel;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.net.UploadHelper;
import com.tower.smartline.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;

/**
 * UpdateInfoPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/6 2:03
 */
public class UpdateInfoPresenter extends BasePresenter<IUpdateInfoContract.View>
        implements IUpdateInfoContract.Presenter, IDataSource.Callback<UserCard> {
    private static final String TAG = UpdateInfoPresenter.class.getName();

    /**
     * 构造方法
     *
     * @param view Presenter需要绑定的View层实例
     */
    public UpdateInfoPresenter(@NonNull IUpdateInfoContract.View view) {
        super(view);
    }

    @Override
    public void update(Uri portraitUri, String desc, boolean isMale) {
        start();
        int sex = isMale ? UserEntity.SEX_TYPE_MALE : UserEntity.SEX_TYPE_FEMALE;
        if (portraitUri == null) {
            // 用户使用了默认头像 使用性别标识头像构建Model
            String portraitType = isMale ? PortraitView.DEFAULT_MALE : PortraitView.DEFAULT_FEMALE;

            // 向云侧发送修改请求
            UserHelper.update(new UpdateInfoModel("", portraitType, desc, sex),
                    UpdateInfoPresenter.this);
        } else {
            // 用户使用了本地图片 上传头像
            Factory.runOnAsync(() -> {
                // 获取图片上传后的外网链接
                String portraitUrl = UploadHelper.uploadPortrait(portraitUri);

                if (TextUtils.isEmpty(portraitUrl)) {
                    // 上传失败
                    Log.w(TAG, "update: portraitUrl == null");
                    onFailure(R.string.toast_oss_upload_error);
                } else {
                    // 上传成功 向云侧发送修改请求
                    Log.i(TAG, "update: portraitUrl: " + portraitUrl);
                    UserHelper.update(new UpdateInfoModel("", portraitUrl, desc, sex),
                            UpdateInfoPresenter.this);
                }
            });
        }
    }

    @Override
    public void onSuccess(UserCard userCard) {
        Run.onUiAsync(() -> {
            if (getView() != null) {
                getView().submitSuccess();
            }
        });
    }

    @Override
    public void onFailure(int strRes) {
        Run.onUiAsync(() -> {
            if (getView() != null) {
                getView().showError(strRes);
            }
        });
    }
}
