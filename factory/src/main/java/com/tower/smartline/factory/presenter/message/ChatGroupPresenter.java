package com.tower.smartline.factory.presenter.message;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.data.repository.GroupMessageRepository;
import com.tower.smartline.factory.data.repository.UserMessageRepository;
import com.tower.smartline.factory.data.repository.base.IMessageSource;
import com.tower.smartline.factory.model.api.message.MessageCreateModel;
import com.tower.smartline.factory.model.db.GroupEntity;
import com.tower.smartline.factory.model.db.UserEntity;

/**
 * ChatGroupPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/26 23:54
 */
public class ChatGroupPresenter extends ChatPresenter<GroupEntity> {
    /**
     * 构造方法
     *
     * @param view       Presenter需要绑定的View层实例
     * @param receiverId 接受者Id
     */
    public ChatGroupPresenter(@NonNull IChatContract.View<GroupEntity> view, String receiverId) {
        super(view, new GroupMessageRepository(receiverId), receiverId, MessageCreateModel.RECEIVER_TYPE_GROUP);
    }

    @Override
    public void initReceiverData() {
        super.start();

        // TODO 群组模块
    }
}
