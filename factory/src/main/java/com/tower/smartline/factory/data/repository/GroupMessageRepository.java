package com.tower.smartline.factory.data.repository;

import com.tower.smartline.factory.data.repository.base.BaseDbRepository;
import com.tower.smartline.factory.data.repository.base.IMessageSource;
import com.tower.smartline.factory.model.db.MessageEntity;

/**
 * GroupMessageRepository
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/26 21:58
 */
public class GroupMessageRepository extends BaseDbRepository<MessageEntity>
        implements IMessageSource {
    // 聊天群组Id
    private String mChatGroupId;

    /**
     * 构造方法
     *
     * @param chatGroupId 聊天群组Id
     */
    public GroupMessageRepository(String chatGroupId) {
        super();
        this.mChatGroupId = chatGroupId;
    }

    @Override
    protected void loadData() {
        // TODO 群组模块
    }

    @Override
    protected boolean isRequired(MessageEntity data) {
        // TODO 群组模块
        return false;
    }
}
