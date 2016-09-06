package com.befiring.easytime.network;

import com.befiring.easytime.bean.JokeResponse.JokeResponse;
import com.befiring.easytime.bean.PictureResponse.Image;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/8/31.
 */
public interface ApiService {
    @GET("search")
    Observable<List<Image>> searchPicture(@Query("q") String query);
    @GET("content/list.from")
    Observable<JokeResponse> getJokeData(@Query("key") String key, @Query("sort") String sort, @Query("page") int page, @Query("pagesize") int pageSize, @Query("time") String time);
}
