package com.tower.smartline.factory.model.response.base;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * 响应Model
 *
 * @param <R> 响应携带的数据的模型
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 6:42
 */
public class ResponseModel<R> {
    // 响应错误码
    private int code;

    // 响应说明
    private String msg;

    // 携带的数据
    private R result;

    // 服务侧响应时间
    private Date time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResponseModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", time=" + time +
                '}';
    }
}
