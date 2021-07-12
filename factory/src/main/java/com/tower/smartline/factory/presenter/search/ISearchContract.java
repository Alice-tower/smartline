package com.tower.smartline.factory.presenter.search;

import com.tower.smartline.factory.model.response.GroupCard;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.presenter.IBaseContract;

import java.util.List;

/**
 * ISearchContract
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/12 16:06
 */
public interface ISearchContract {
    /**
     * 综合搜索的界面
     */
    interface MainView extends UserView, GroupView {
    }

    /**
     * 搜索人的界面
     */
    interface UserView extends IBaseContract.View<Presenter> {
        /**
         * 搜索人成功
         *
         * @param userCards 用户信息集合
         */
        void searchUserSuccess(List<UserCard> userCards);
    }

    /**
     * 搜索群的界面
     */
    interface GroupView extends IBaseContract.View<Presenter> {
        /**
         * 搜索成功
         *
         * @param groupCards 群信息集合
         */
        void searchGroupSuccess(List<GroupCard> groupCards);
    }

    interface Presenter extends IBaseContract.Presenter {
        /**
         * 搜索内容
         *
         * @param content 用户键入的内容
         */
        void search(String content);
    }
}
