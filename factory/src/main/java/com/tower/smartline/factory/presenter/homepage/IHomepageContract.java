package com.tower.smartline.factory.presenter.homepage;

import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.presenter.IBaseContract;

/**
 * IContactContract
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/24 13:03
 */
public interface IHomepageContract {
    interface View extends IBaseContract.RecyclableView<Presenter, UserEntity> {
        /**
         * 首次
         */
        void firstInitDataSuccess();
    }

    interface Presenter extends IBaseContract.Presenter {
        /**
         * 刷新数据
         */
        void initData();
    }
}
