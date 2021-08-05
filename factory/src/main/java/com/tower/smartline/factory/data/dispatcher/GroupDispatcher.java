package com.tower.smartline.factory.data.dispatcher;

import android.text.TextUtils;

import com.tower.smartline.factory.data.helper.GroupHelper;
import com.tower.smartline.factory.data.helper.UserHelper;
import com.tower.smartline.factory.data.db.DbPortal;
import com.tower.smartline.factory.model.db.GroupEntity;
import com.tower.smartline.factory.model.db.GroupMemberEntity;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.model.response.GroupCard;
import com.tower.smartline.factory.model.response.GroupMemberCard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * GroupDispatcher
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/4 20:08
 */
public class GroupDispatcher {
    private static GroupDispatcher sInstance;

    // 单线程池
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    private GroupDispatcher() {
    }

    /**
     * 获取单例
     *
     * @return GroupDispatcher
     */
    static GroupDispatcher getInstance() {
        if (sInstance == null) {
            synchronized (GroupDispatcher.class) {
                if (sInstance == null)
                    sInstance = new GroupDispatcher();
            }
        }
        return sInstance;
    }

    /**
     * 对接收的数据进行存储和通知
     *
     * @param cards GroupCard数组
     */
    void dispatch(GroupCard... cards) {
        if (cards == null || cards.length == 0) {
            return;
        }
        mExecutor.execute(new GroupCardHandler(cards));
    }

    /**
     * 对接收的数据进行存储和通知
     *
     * @param cards GroupMemberCard数组
     */
    void dispatch(GroupMemberCard... cards) {
        if (cards == null || cards.length == 0) {
            return;
        }
        mExecutor.execute(new GroupMemberCardHandler(cards));
    }

    /**
     * 构建数据库模型
     */
    private static class GroupCardHandler implements Runnable {
        private final GroupCard[] mCards;

        GroupCardHandler(GroupCard[] cards) {
            this.mCards = cards;
        }

        @Override
        public void run() {
            List<GroupEntity> groups = new ArrayList<>();
            for (GroupCard card : mCards) {
                if (card == null || TextUtils.isEmpty(card.getId())
                        || TextUtils.isEmpty(card.getOwnerId())) {
                    continue;
                }
                UserEntity owner = UserHelper.infoFirstOfLocal(card.getOwnerId());
                groups.add(card.toGroupEntity(owner));
            }
            if (groups.size() > 0) {
                // 进行数据库存储并通知
                DbPortal.save(GroupEntity.class, groups.toArray(new GroupEntity[0]));
            }
        }
    }

    /**
     * 构建数据库模型
     */
    private static class GroupMemberCardHandler implements Runnable {
        private final GroupMemberCard[] mCards;

        GroupMemberCardHandler(GroupMemberCard[] cards) {
            this.mCards = cards;
        }

        @Override
        public void run() {
            List<GroupMemberEntity> groupMembers = new ArrayList<>();
            for (GroupMemberCard card : mCards) {
                if (card == null || TextUtils.isEmpty(card.getId())
                        || TextUtils.isEmpty(card.getUserId())
                        || TextUtils.isEmpty(card.getGroupId())) {
                    continue;
                }
                UserEntity user = UserHelper.infoFirstOfLocal(card.getUserId());
                GroupEntity group = GroupHelper.infoFirstOfLocal(card.getGroupId());
                groupMembers.add(card.toGroupMemberEntity(user, group));
            }
            if (groupMembers.size() > 0) {
                // 进行数据库存储并通知
                DbPortal.save(GroupMemberEntity.class, groupMembers.toArray(new GroupMemberEntity[0]));
            }
        }
    }
}
