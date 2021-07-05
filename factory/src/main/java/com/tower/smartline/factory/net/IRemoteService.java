package com.tower.smartline.factory.net;

import com.tower.smartline.factory.model.api.account.LoginModel;
import com.tower.smartline.factory.model.api.account.RegisterModel;
import com.tower.smartline.factory.model.api.user.UpdateInfoModel;
import com.tower.smartline.factory.model.response.AccountRspModel;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.model.response.base.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * 所有的网络请求接口
 *
 * @author zpsong-tower <pingzisong2012@gmail.com>
 * @since 2021/6/9 6:33
 */
public interface IRemoteService {
    /**
     * 登录接口
     *
     * @param model LoginModel
     * @return ResponseModel<AccountRspModel>
     */
    @POST("account/login")
    Call<ResponseModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    /**
     * 注册接口
     *
     * @param model RegisterModel
     * @return ResponseModel<AccountRspModel>
     */
    @POST("account/register")
    Call<ResponseModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    /**
     * 绑定设备Id
     *
     * @param pushId 设备Id
     * @return ResponseModel<AccountRspModel>
     */
    @POST("account/bind/{pushId}")
    Call<ResponseModel<AccountRspModel>> accountBind(@Path(encoded = true, value = "pushId") String pushId);

    /**
     * 更新用户个人信息
     *
     * @param model UpdateInfoModel
     * @return ResponseModel<UserCard>
     */
    @PUT("user")
    Call<ResponseModel<UserCard>> userUpdate(@Body UpdateInfoModel model);
}
