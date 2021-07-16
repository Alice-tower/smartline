package com.tower.smartline.factory.net;

import com.tower.smartline.factory.model.api.account.LoginModel;
import com.tower.smartline.factory.model.api.account.RegisterModel;
import com.tower.smartline.factory.model.api.user.UpdateInfoModel;
import com.tower.smartline.factory.model.response.AccountRspModel;
import com.tower.smartline.factory.model.response.UserCard;
import com.tower.smartline.factory.model.response.base.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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

    /**
     * 查询指定Id的用户信息
     *
     * @param userId 查询的用户Id
     * @return ResponseModel<UserCard>
     */
    @GET("user/{userId}")
    Call<ResponseModel<UserCard>> userInfo(@Path("userId") String userId);

    /**
     * 查询指定用户名的用户信息 (模糊查询 单页最多返回20个)
     *
     * @param name 用户名
     * @return ResponseModel<List < UserCard>>
     */
    @GET("user/search/{name}")
    Call<ResponseModel<List<UserCard>>> userSearch(@Path("name") String name);

    /**
     * 关注人(目前设计为自动互关)
     *
     * @param userId 被关注人的Id
     * @return ResponseModel<UserCard>
     */
    @PUT("user/follow/{userId}")
    Call<ResponseModel<UserCard>> userFollow(@Path("userId") String userId);

    /**
     * 拉取联系人列表(关注的人列表)
     *
     * @return ResponseModel<List < UserCard>>
     */
    @GET("user/contact")
    Call<ResponseModel<List<UserCard>>> userContacts();
}
