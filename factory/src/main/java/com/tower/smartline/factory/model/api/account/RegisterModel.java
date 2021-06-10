package com.tower.smartline.factory.model.api.account;

/**
 * RegisterModel
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 7:17
 */
public class RegisterModel {
    // 手机号
    private String phone;

    // 密码
    private String password;

    // 用户名
    private String name;

    // PushId
    private String pushId;

    public RegisterModel(String phone, String password, String name) {
        this(phone, password, name, null);
    }

    public RegisterModel(String phone, String password, String name, String pushId) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.pushId = pushId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
