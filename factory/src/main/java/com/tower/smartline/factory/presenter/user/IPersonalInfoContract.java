package com.tower.smartline.factory.presenter.user;

import com.tower.smartline.factory.model.IUserInfo;
import com.tower.smartline.factory.presenter.IBaseContract;

/**
 * IPersonalInfoContract
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/30 15:46
 */
public interface IPersonalInfoContract {
    /**
     * 用户本人
     */
    int TYPE_SELF = 1;

    /**
     * 用户还未关注的人
     */
    int TYPE_FOLLOW = 2;

    /**
     * 用户已关注的人 可以发起聊天
     */
    int TYPE_MESSAGE = 3;

    interface View extends IBaseContract.View<Presenter> {
        /**
         * 用户个人信息加载成功 (关注成功时也会触发部分逻辑)
         *
         * @param user 用户信息
         * @param type 界面类型 (根据用户关系决定)
         */
        void loadSuccess(IUserInfo user, int type);
    }

    interface Presenter extends IBaseContract.Presenter {
        /**
         * 加载用户个人信息
         *
         * @param userId 用户Id
         */
        void loadPersonalInfo(String userId);

        /**
         * 关注该用户 (加好友)
         *
         * @param id 被关注者的用户Id
         */
        void follow(String id);
    }
}
