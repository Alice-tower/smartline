package com.tower.smartline.factory.presenter.account;

import com.tower.smartline.factory.presenter.IBaseContract;

/**
 * 登录MVP约定
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/8 0:59
 */
public interface ILoginContract {
    interface View extends IBaseContract.View<Presenter> {
        /**
         * 登录/注册成功
         */
        void submitSuccess();
    }

    interface Presenter extends IBaseContract.Presenter {
        /**
         * 发起一个登录
         *
         * @param phone    手机号
         * @param password 密码
         */
        void login(String phone, String password);

        /**
         * 发起一个注册
         *
         * @param phone    手机号
         * @param password 密码
         * @param username 用户名
         */
        void register(String phone, String password, String username);

        /**
         * 检查手机号和密码数据格式是否合法
         *
         * @param phone    手机号
         * @param password 密码
         * @return 数据是否合法
         */
        boolean checkString(String phone, String password);

        /**
         * 检查用户名数据格式是否合法 (注册用)
         *
         * @param username 用户名
         * @return 数据是否合法
         */
        boolean checkString(String username);
    }
}
