package com.thoughtworks.chengdu.gb.moments.api;

import com.thoughtworks.chengdu.gb.moments.model.TweetsModel;
import com.thoughtworks.chengdu.gb.moments.model.UserInfo;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by GB on 2017/11/27.
 */

public interface MomentsService {

    /**
     * 获取登录用户信息
     *
     * @param name
     * @return
     */
    @GET("{name}")
    Flowable<UserInfo> getUserInfo(@Path("name") String name);


    @GET("{name}/tweets")
    Flowable<TweetsModel> getTweets(@Path("name") String name);


}
