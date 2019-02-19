package com.wd.tech.https;

import com.wd.tech.bean.Result;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * date:2019/2/18 20:28
 * author:赵明珠(啊哈)
 * function:
 */
public interface IResult {

    @GET("information/v1/bannerShow")
    Observable<Result<List<Cerdshi>>> ceshi();
}
