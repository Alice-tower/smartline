package com.tower.smartline.factory.net;

import com.tower.smartline.factory.model.api.account.LoginModel;
import com.tower.smartline.factory.model.api.account.RegisterModel;
import com.tower.smartline.factory.model.response.AccountRspModel;
import com.tower.smartline.factory.model.response.base.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
}
