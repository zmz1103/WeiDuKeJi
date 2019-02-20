package com.wd.tech.https;

import com.wd.tech.bean.BannnerBean;
import com.wd.tech.bean.CommunitylistData;
import com.wd.tech.bean.FriendsPostData;
import com.wd.tech.bean.InformationListBean;
import com.wd.tech.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

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
    //社区列表展示
    @GET("community/v1/findCommunityList")
    Observable<Result<List<CommunitylistData>>> getCommunitylist(@Header("userId") int userId,
                                                                 @Header("sessionId") String sessionId,
                                                                 @Query("page")int page,
                                                                 @Query("count")int count);


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

    //发布帖子
    @POST("community/verify/v1/releasePost")
    @FormUrlEncoded
    Observable<Result> getPublish(@Header("userId") int userId,
                                  @Header("sessionId") String sessionId,
                                  @Body MultipartBody multipartBody);

    //用户发布的贴子
    @GET("community/verify/v1/findUserPostById")
    Observable<Result<List<FriendsPostData>>> getFriendsPost(@Header("userId") int userId,
                                                             @Header("sessionId") String sessionId,
                                                             @Query("fromUid")int fromUid,
                                                             @Query("page")int page,
                                                             @Query("count")int count);

    /**
     * Banner轮播图
     * @return
     */
    @GET("information/v1/bannerShow")
    Observable<Result<List<BannnerBean>>> ShowBannner();


    /**
     * 资讯页面展示
     * @return
     */
    @GET("information/v1/infoRecommendList")
    Observable<Result<List<InformationListBean>>> showinformationList(@Header("userId") int userId,
                                                                      @Header("sessionId")String sessionId,
                                                                      @Query("plateId") int plateId,
                                                                      @Query("page")int page,
                                                                      @Query("count")int count);


    // 微信登录
    @POST("user/v1/weChatLogin")
    @FormUrlEncoded
    Observable<Result<User>> getWxlogin(@Header("ak") String ak,@Field("code") String code);
}
