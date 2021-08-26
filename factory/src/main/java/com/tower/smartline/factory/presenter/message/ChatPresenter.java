package com.tower.smartline.factory.presenter.message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.tower.smartline.factory.data.helper.MessageHelper;
import com.tower.smartline.factory.data.repository.base.IMessageSource;
import com.tower.smartline.factory.model.api.message.MessageCreateModel;
import com.tower.smartline.factory.model.db.MessageEntity;
import com.tower.smartline.factory.presenter.BaseDbDataSourcePresenter;
import com.tower.smartline.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * ChatPresenter
 *
 * @param <ReceiverEntity> 接受者类型 (UserEntity / GroupEntity)
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/26 17:43
 */
public abstract class ChatPresenter<ReceiverEntity>
        extends BaseDbDataSourcePresenter<MessageEntity, IChatContract.View<ReceiverEntity>, IMessageSource>
        implements IChatContract.Presenter {
    // 接受者Id
    protected String mReceiverId;

    // 接收者类型
    protected int mReceiverType;

    /**
     * 构造方法
     *
     * @param view           Presenter需要绑定的View层实例
     * @param iMessageSource 数据仓库
     * @param receiverId     接受者Id
     * @param receiverType   接收者类型
     */
    public ChatPresenter(@NonNull IChatContract.View<ReceiverEntity> view, IMessageSource iMessageSource,
                         String receiverId, int receiverType) {
        super(view, iMessageSource);
        this.mReceiverId = receiverId;
        this.mReceiverType = receiverType;
    }

    @Override
    public void sendText(String content) {
        MessageCreateModel model = new MessageCreateModel.Builder()
                .receiver(mReceiverId, mReceiverType)
                .content(content, MessageEntity.TYPE_TEXT)
                .build();
        MessageHelper.send(model);
    }

    @Override
    public void sendImages(String[] paths) {
        // TODO
    }

    @Override
    public void onSuccess(List<MessageEntity> messageEntities) {
        if (getView() == null || getView().getRecyclerAdapter() == null) {
            return;
        }
        List<MessageEntity> old = getView().getRecyclerAdapter().getItems();

        // 与当前显示的数据进行比较刷新
        DiffUiDataCallback<MessageEntity> callback = new DiffUiDataCallback<>(old, messageEntities);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        refreshData(result, messageEntities);
    }
}
