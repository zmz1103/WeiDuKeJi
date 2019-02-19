package com.wd.tech.https;

import com.wd.tech.bean.BannnerBean;
import com.wd.tech.bean.InformationListBean;
import com.wd.tech.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * date:2019/2/18 20:28
 * author:赵明珠(啊哈)
 * function:
 */
public interface IRequest {

    /**
     * Banner轮播图
     * @return
     */
    @GET("information/v1/bannerShow")
    Observable<Result<List<BannnerBean>>> ShowBannner();

    @POST("information/v1/infoRecommendList")
    @FormUrlEncoded
    Observable<Result<List<InformationListBean>>> showinformationList(@Header("userId") int userId,
                                                                      @Header("sessionId")String sessionId,
                                                                      @Field("plateId")int plateId,
                                                                      @Field("page")int page,
                                                                      @Field("count")int count);

}
