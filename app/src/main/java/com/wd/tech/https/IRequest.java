package com.wd.tech.https;

import com.wd.tech.bean.Result;
import com.wd.tech.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * date:2019/2/18 20:28
 * author:赵明珠(啊哈)
 * function:
 */
public interface IRequest {

    // 注册
    @POST("user/v1/register")
    @FormUrlEncoded
    Observable<Result> register(
            @Field("phone") String phone,
            @Field("nickName") String nickName,
            @Field("pwd") String pwd);

    // 登录
    @POST("user/v1/login")
    @FormUrlEncoded
    Observable<Result<User>> login(
            @Field("phone") String phone,
            @Field("pwd") String pwd);
}
