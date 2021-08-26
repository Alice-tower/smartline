package com.tower.smartline.factory.presenter.message;

import androidx.annotation.NonNull;

import com.tower.smartline.factory.data.helper.UserHelper;
import com.tower.smartline.factory.data.repository.UserMessageRepository;
import com.tower.smartline.factory.model.api.message.MessageCreateModel;
import com.tower.smartline.factory.model.db.UserEntity;

/**
 * ChatUserPresenter
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/26 21:27
 */
public class ChatUserPresenter extends ChatPresenter<UserEntity> {
    /**
     * 构造方法
     *
     * @param view       Presenter需要绑定的View层实例
     * @param receiverId 接受者Id
     */
    public ChatUserPresenter(@NonNull IChatContract.View<UserEntity> view, String receiverId) {
        super(view, new UserMessageRepository(receiverId), receiverId, MessageCreateModel.RECEIVER_TYPE_USER);
    }

    @Override
    public void initReceiverData() {
        super.start();
        UserEntity receiver = UserHelper.infoFirstOfLocal(mReceiverId);
        if (getView() != null) {
            getView().initReceiverSuccess(receiver);
        }
    }
}
