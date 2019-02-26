package com.wd.tech.https;

import com.wd.tech.bean.AllInfoCommentListBean;
import com.wd.tech.bean.AttentionListData;
import com.wd.tech.bean.BannnerBean;
import com.wd.tech.bean.CollectDataList;
import com.wd.tech.bean.GetUserBean;
import com.wd.tech.bean.Group;
import com.wd.tech.bean.CommunitylistData;
import com.wd.tech.bean.FriendsPostData;
import com.wd.tech.bean.InformationDetailsBean;
import com.wd.tech.bean.InformationListBean;
import com.wd.tech.bean.InformationSearchByTitleBean;
import com.wd.tech.bean.InterestListBean;
import com.wd.tech.bean.QueryFriendList;
import com.wd.tech.bean.NoticeListDAta;
import com.wd.tech.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Observable<Result<List<InformationListBean>>> showinformationList(@Header("userId") long userId,
                                                                      @Header("sessionId")String sessionId,
                                                                      @Query("plateId") String plateId,
                                                                      @Query("page")int page,
                                                                      @Query("count")int count);


    // 微信登录
    @POST("user/v1/weChatLogin")
    @FormUrlEncoded
    Observable<Result<User>> getWxlogin(@Header("ak") String ak,@Field("code") String code);


    //社区点赞
    @POST("community/verify/v1/addCommunityGreat")
    @FormUrlEncoded
    Observable<Result> getLike(@Header("userId") int userId,
                               @Header("sessionId") String sessionId,
                               @Field("communityId") int communityId);

    //社区取消点赞
    @DELETE("community/verify/v1/cancelCommunityGreat")
    Observable<Result> getcancelLike(@Header("userId") int userId,
                                     @Header("sessionId") String sessionId,
                                     @Query("communityId") int communityId);


    /**
     * 板块兴趣(lk)
     */
    @GET("information/v1/findAllInfoPlate")
    Observable<Result<List<InterestListBean>>> showinterestlist();
    /*Observable<Result<List<InformationListBean>>> showinformationList(@Header("userId") int userId,
                                                                      @Header("sessionId")String sessionId,
                                                                      @Field("plateId")int plateId,
                                                                      @Field("page")int page,
                                                                      @Field("count")int count);*/
    /**
     * @作者 啊哈
     * @date 2019/2/20
     * @method：分组
     */
    @GET("chat/verify/v1/initFriendList")
    Observable<Result<List<Group>>> group(@Header("userId") int userId,
                                          @Header("sessionId")String sessionId);

    //关注用户
    @POST("user/verify/v1/addFollow")
    @FormUrlEncoded
    Observable<Result> getAddFollow(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Field("focusId") int focusId);

    //取消关注用户
    @DELETE("user/verify/v1/cancelFollow")
    Observable<Result> getCancelFollow(@Header("userId") int userId,
                                       @Header("sessionId") String sessionId,
                                       @Query("focusId") int focusId);

    //添加好友
    @POST("chat/verify/v1/addFriend")
    @FormUrlEncoded
    Observable<Result> getAddFriend(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Field("friendUid") int friendUid,
                                    @Field("remark") String remark);

    //查询好友信息
    @GET("user/verify/v1/queryFriendInformation")
    Observable<Result<QueryFriendList>> getQueryFriend(@Header("userId") int userId,
                                                       @Header("sessionId")String sessionId,
                                                       @Query("friend")int friend);

    //社区评论
    @POST("community/verify/v1/addCommunityComment")
    @FormUrlEncoded
    Observable<Result> getAddCommunity(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Field("communityId") int communityId,
                                    @Field("content") String content);



    // 查询用户信息 user/verify/v1/getUserInfoByUserId
    @GET("user/verify/v1/getUserInfoByUserId")
    Observable<Result<GetUserBean>> getUserInfoByUserId(@Header("userId") long userId,
                                                        @Header("sessionId") String sessionId);

    // 完善用户信息 user/verify/v1/perfectUserInfo
    @POST("user/verify/v1/perfectUserInfo")
    @FormUrlEncoded
    Observable<Result> perfectUserInfo(@Header("userId") long userId,
                                       @Header("sessionId") String sessionId,
                                       @Field("nickName") String nickName,
                                       @Field("sex") int sex,
                                       @Field("signature") String signature,
                                       @Field("birthday") String birthday,
                                       @Field("email") String email);

    // 查询收藏列表user/verify/v1/findAllInfoCollection
    @GET("user/verify/v1/findAllInfoCollection")
    Observable<Result<List<CollectDataList>>> findAllInfoCollection(@Header("userId") long userId,
                                                                    @Header("sessionId") String sessionId,
                                                                    @Query("page") int page,
                                                                    @Query("count") int count);

    // 用户关注列表  user/verify/v1/findFollowUserList
    @GET("user/verify/v1/findFollowUserList")
    Observable<Result<List<AttentionListData>>> findFollowUserList(@Header("userId") long userId,
                                                                   @Header("sessionId") String sessionId,
                                                                   @Query("page") int page,
                                                                   @Query("count") int count);
    // 用户通知  tool/verify/v1/findSysNoticeList
    @GET("tool/verify/v1/findSysNoticeList")
    Observable<Result<List<NoticeListDAta>>> findSysNoticeList(@Header("userId") long userId,
                                                               @Header("sessionId") String sessionId,
                                                               @Query("page") int page,
                                                               @Query("count") int count);

    // 我的帖子community/verify/v1/findMyPostById
    @GET("community/verify/v1/findMyPostById")
    Observable<Result<List<NoticeListDAta>>> findMyPostById(@Header("userId") long userId,
                                                            @Header("sessionId") String sessionId,
                                                            @Query("page") int page,
                                                            @Query("count") int count);


    /**
     * 按标题搜索(lk)
     */
    @GET("information/v1/findInformationByTitle")
    Observable<Result<List<InformationSearchByTitleBean>>> SearchByTitle(@Query("title") String title,
                                                                         @Query("page") int page,
                                                                         @Query("count") int count);

    /**
     * 资讯点赞（lk）
     */
    @POST("information/verify/v1/addGreatRecord")
    @FormUrlEncoded
    Observable<Result> addgreat(@Header("userId") long userId,
                                @Header("sessionId")String sessionId,
                                @Field("infoId")int infoId);

    /**
     * 资讯取消点赞（lk）
     */
    @DELETE("information/verify/v1/cancelGreat")
    Observable<Result> cancelGreat(@Header("userId") long userId,
                                @Header("sessionId")String sessionId,
                                @Query("infoId") String infoId);

    /**
     * 资讯收藏（lk)
     */
    @POST("user/verify/v1/addCollection")
    @FormUrlEncoded
    Observable<Result> addcollection(@Header("userId") long userId,
                                @Header("sessionId")String sessionId,
                                @Field("infoId")int infoId);
    /**
     * 资讯取消收藏（lk)
     */
    @DELETE("user/verify/v1/cancelCollection")
    Observable<Result> cancelcollection(@Header("userId") long userId,
                                        @Header("sessionId")String sessionId,
                                        @Query("infoId") String infoId);


    /**
     * 资讯详情页（lk）
     */
    @GET("information/v1/findInformationDetails")
    Observable<Result<InformationDetailsBean>> infordetails(@Header("userId") long userId,
                                                            @Header("sessionId")String sessionId,
                                                            @Query("id") String id);


    /**
     * 资讯详情页评论查询（lk）
     */
    @GET("information/v1/findAllInfoCommentList")
    Observable<Result<List<AllInfoCommentListBean>>> infoCommentList(@Header("userId") long userId,
                                                                     @Header("sessionId")String sessionId,
                                                                     @Query("infoId") String infoId,
                                                                     @Query("page") int page,
                                                                     @Query("count") int count);



}
