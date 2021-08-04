package com.tower.smartline.factory.data.Dispatcher;

import android.text.TextUtils;

import com.tower.smartline.factory.data.DbPortal;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.model.response.UserCard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * UserDispatcher
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/4 20:07
 */
public class UserDispatcher {
    private static UserDispatcher sInstance;

    // 单线程池
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    private UserDispatcher() {
    }

    /**
     * 获取单例
     *
     * @return UserDispatcher
     */
    static UserDispatcher getInstance() {
        if (sInstance == null) {
            synchronized (UserDispatcher.class) {
                if (sInstance == null)
                    sInstance = new UserDispatcher();
            }
        }
        return sInstance;
    }

    /**
     * 对接收的数据进行存储和通知
     *
     * @param cards UserCard数组
     */
    void dispatch(UserCard... cards) {
        if (cards == null || cards.length == 0) {
            return;
        }
        mExecutor.execute(new UserCardHandler(cards));
    }

    /**
     * 构建数据库模型
     */
    private static class UserCardHandler implements Runnable {
        private final UserCard[] mCards;

        UserCardHandler(UserCard[] cards) {
            this.mCards = cards;
        }

        @Override
        public void run() {
            List<UserEntity> users = new ArrayList<>();
            for (UserCard card : mCards) {
                if (card == null || TextUtils.isEmpty(card.getId())) {
                    continue;
                }
                users.add(card.toUserEntity());
            }

            // 进行数据库存储并通知
            if (users.size() > 0) {
                DbPortal.save(UserEntity.class, users.toArray(new UserEntity[0]));
            }
        }
    }
}
