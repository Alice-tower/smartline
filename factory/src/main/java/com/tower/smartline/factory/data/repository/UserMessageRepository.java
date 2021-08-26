package com.tower.smartline.factory.data.repository;

import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.tower.smartline.common.Constants;
import com.tower.smartline.factory.data.repository.base.BaseDbRepository;
import com.tower.smartline.factory.data.repository.base.IMessageSource;
import com.tower.smartline.factory.model.db.MessageEntity;
import com.tower.smartline.factory.model.db.MessageEntity_Table;

/**
 * MessageRepository
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/26 17:46
 */
public class UserMessageRepository extends BaseDbRepository<MessageEntity>
        implements IMessageSource {
    // 聊天对象Id
    private String mChatUserId;

    /**
     * 构造方法
     *
     * @param chatUserId 聊天对象Id
     */
    public UserMessageRepository(String chatUserId) {
        super();
        this.mChatUserId = chatUserId;
    }

    @Override
    protected void loadData() {
        SQLite.select()
                .from(MessageEntity.class)
                .where(OperatorGroup.clause()
                        .and(MessageEntity_Table.sender_id.eq(mChatUserId)) // 发送者Id与聊天对象Id一致
                        .and(MessageEntity_Table.group_id.isNull())) // 接收群为空
                .or(MessageEntity_Table.receiver_id.eq(mChatUserId)) // 接收者Id与聊天对象Id一致
                .orderBy(MessageEntity_Table.createAt, false) // 按创建时间排序
                .limit(Constants.MAX_RESULTS_EACH_PAGE) // 上限30
                .async() // 异步执行
                .queryListResultCallback(this) // 设置回调
                .execute();
    }

    @Override
    protected boolean isRequired(MessageEntity data) {
        if (TextUtils.isEmpty(mChatUserId) || data == null || data.getSender() == null) {
            return false;
        }

        // 接收群为空，接收者不为空
        // 发送者或接受者Id与聊天对象Id一致
        return data.getGroup() == null && data.getReceiver() != null
                && (mChatUserId.equals(data.getSender().getId()) || mChatUserId.equals(data.getReceiver().getId()));
    }
}
