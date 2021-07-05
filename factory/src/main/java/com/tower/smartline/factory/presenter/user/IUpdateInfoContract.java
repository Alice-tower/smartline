package com.tower.smartline.factory.presenter.user;

import android.net.Uri;

import com.tower.smartline.factory.presenter.IBaseContract;

/**
 * IUpdateInfoContract
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/6 1:53
 */
public interface IUpdateInfoContract {
    interface View extends IBaseContract.View<Presenter> {
        /**
         * 个人信息更新成功
         */
        void submitSuccess();
    }

    interface Presenter extends IBaseContract.Presenter {
        /**
         * 更新个人信息
         *
         * @param portraitUri 头像路径（外网链接）
         * @param desc 个性签名
         * @param isMale 性别 True为男性 False为女性
         */
        void update(Uri portraitUri, String desc, boolean isMale);
    }
}
