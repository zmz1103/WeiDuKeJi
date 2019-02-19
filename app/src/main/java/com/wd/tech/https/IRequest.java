package com.wd.tech.https;

import com.wd.tech.bean.CommunitylistData;
import com.wd.tech.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * date:2019/2/18 20:28
 * author:赵明珠(啊哈)
 * function:
 */
public interface IRequest {
    @GET("community/v1/findCommunityList")
    Observable<Result<List<CommunitylistData>>> getCommunitylist(@Header("userId") int userId,
                                                                 @Header("sessionId") String sessionId,
                                                                 @Query("page")int page,
                                                                 @Query("count")int count);

}
