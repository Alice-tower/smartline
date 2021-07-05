package com.tower.smartline.factory.model.api.user;

/**
 * UpdateInfoModel
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/7/5 2:49
 */
public class UpdateInfoModel {
    // 用户名
    private String name;

    // 头像
    private String portrait;

    // 个性签名
    private String description;

    // 性别
    private int sex;

    public UpdateInfoModel(String name, String portrait, String description, int sex) {
        this.name = name;
        this.portrait = portrait;
        this.description = description;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
