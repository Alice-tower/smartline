package com.tower.smartline.factory.model.response.base;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * 推送Model
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/8/15 16:02
 */
public class PushModel {
    // 推送类型
    private int type;

    // 推送内容
    private String content;

    // 创建时间
    private Date createAt;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @NonNull
    @Override
    public String toString() {
        return "PushModel{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
