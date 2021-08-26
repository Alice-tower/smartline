package com.tower.smartline.factory.data.helper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tower.smartline.factory.data.dispatcher.DataCenter;
import com.tower.smartline.factory.data.helper.base.MyCallback;
import com.tower.smartline.factory.model.api.message.MessageCreateModel;
import com.tower.smartline.factory.model.db.MessageEntity;
import com.tower.smartline.factory.model.db.MessageEntity_Table;
import com.tower.smartline.factory.model.response.MessageCard;
import com.tower.smartline.factory.model.response.base.ResponseModel;
import com.tower.smartline.factory.net.Network;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import retrofit2.Call;
import retrofit2.Response;

/**
 * MessageHelper
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/27 11:21
 */
public class MessageHelper {
    private static final String TAG = MessageHelper.class.getName();

    // 发送是异步进行的
    public static void send(MessageCreateModel model) {
        Log.i(TAG, "send: start");
        if (model == null) {
            Log.w(TAG, "send: model == null");
            return;
        }
        // TODO 考虑做消息发送失败，用户可一键重新发送的逻辑
        // TODO 如果文件类型为图片语音等，需要先上传后才发送
        MessageCard card = model.toMessageCard();
        DataCenter.dispatchMessage(card);
        Network.remote()
                .messageSend(model)
                .enqueue(new MyCallback<MessageCard>(null) {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel<MessageCard>> call, @NonNull Response<ResponseModel<MessageCard>> response) {
                        super.onResponse(call, response);
                        MessageCard result = getResultWithoutCallback();
                        if (result == null) {
                            sendFailure();
                        } else {
                            DataCenter.dispatchMessage(result);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseModel<MessageCard>> call, @NonNull Throwable t) {
                        super.onFailure(call, t);
                        sendFailure();
                    }

                    /**
                     * 发送失败，通知界面刷新消息状态
                     */
                    private void sendFailure() {
                        card.setState(MessageEntity.STATE_FAILED);
                        DataCenter.dispatchMessage(card);
                    }
                });
    }

    /**
     * 查询指定Id的消息详情 (本地)
     *
     * @param id 消息Id
     */
    @Nullable
    public static MessageEntity findFromLocal(String id) {
        return SQLite.select()
                .from(MessageEntity.class)
                .where(MessageEntity_Table.id.eq(id))
                .querySingle();
    }
}
