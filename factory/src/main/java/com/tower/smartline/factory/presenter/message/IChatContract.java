package com.tower.smartline.factory.presenter.message;

import com.tower.smartline.factory.model.db.MessageEntity;
import com.tower.smartline.factory.presenter.IBaseContract;

/**
 * IChatContract
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/26 16:53
 */
public interface IChatContract {
    /**
     * 聊天界面
     *
     * @param <ReceiverEntity> 接受者类型 (UserEntity / GroupEntity)
     */
    interface View<ReceiverEntity> extends IBaseContract.RecyclableView<Presenter, MessageEntity> {
        /**
         * Receiver数据初始化后界面的变化
         */
        void initReceiverSuccess(ReceiverEntity data);
    }

    interface Presenter extends IBaseContract.Presenter {
        /**
         * 初始化Receiver数据
         */
        void initReceiverData();

        /**
         * 发送文本类型消息
         *
         * @param content 文本内容
         */
        void sendText(String content);

        /**
         * 发送图片类型消息
         *
         * @param paths 图片路径数组
         */
        void sendImages(String[] paths);

        // TODO 发送语音
        // TODO 发送文件
    }
}
