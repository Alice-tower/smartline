package com.tower.smartline.factory.data.repository;

import androidx.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.tower.smartline.common.Constants;
import com.tower.smartline.factory.data.repository.base.BaseDbRepository;
import com.tower.smartline.factory.model.db.UserEntity;
import com.tower.smartline.factory.model.db.UserEntity_Table;
import com.tower.smartline.factory.persistence.Account;

import java.util.List;

/**
 * ContactRepository
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/6 3:14
 */
public class ContactRepository extends BaseDbRepository<UserEntity> {
    @Override
    public void load(@NonNull SuccessCallback<List<UserEntity>> callback) {
        super.load(callback);

        // 加载一次用户信息表的数据
        SQLite.select()
                .from(UserEntity.class)
                .where(UserEntity_Table.isFollow.eq(true)) // 已关注的人
                .and(UserEntity_Table.id.notEq(Account.getUserId())) // 不为自己
                .orderBy(UserEntity_Table.name, true) // 按用户名排序
                .limit(Constants.MAX_RESULTS_EACH_PAGE) // 上限100
                .async() // 异步执行
                .queryListResultCallback(this) // 设置回调
                .execute();
    }

    @Override
    protected boolean isRequired(UserEntity data) {
        // 已关注的人，不为自己
        return data.isFollow() && !data.getId().equals(Account.getUserId());
    }
}
