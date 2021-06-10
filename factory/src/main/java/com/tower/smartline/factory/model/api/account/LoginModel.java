package com.tower.smartline.factory.model.api.account;

/**
 * LoginModel
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 7:18
 */
public class LoginModel {
    // 手机号
    private String phone;

    // 密码
    private String password;

    // PushId
    private String pushId;

    public LoginModel(String phone, String password) {
        this(phone, password, null);
    }

    public LoginModel(String phone, String password, String pushId) {
        this.phone = phone;
        this.password = password;
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

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
