package com.tower.smartline.factory.model.response.base;

import androidx.annotation.NonNull;

import java.util.Date;

/**
 * 响应Model
 *
 * @param <T> 网络请求返回Result的类型
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 6:42
 */
public class ResponseModel<T> {
    // 响应错误码
    private int code;

    // 响应说明
    private String msg;

    // 携带的数据
    private T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
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
                // ", result=" + result +
                ", time=" + time +
                '}';
    }
}
