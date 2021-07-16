package com.tower.smartline.factory.presenter.user;

import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.presenter.IBaseContract;

/**
 * IFollowContract
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/15 20:40
 */
public interface IFollowContract {
    interface View extends IBaseContract.View<Presenter> {
        /**
         * 关注成功
         *
         * @param userCard UserCard
         */
        void followSuccess(UserCard userCard);
    }

    interface Presenter extends IBaseContract.Presenter {
        /**
         * 关注该用户 (加好友)
         *
         * @param id 被关注者的用户Id
         */
        void follow(String id);
    }
}
