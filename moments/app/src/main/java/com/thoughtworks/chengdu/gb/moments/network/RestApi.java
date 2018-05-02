package com.thoughtworks.chengdu.gb.moments.network;

import com.thoughtworks.chengdu.gb.moments.api.MomentsService;

import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by GB on 2018/04/30.
 */
public class RestApi {
    public static final String API_URL = "http://thoughtworks-ios.herokuapp.com/";
    private final static String USER = API_URL + "user/";
    private static MomentsService momentsService;

    public static MomentsService getMomentsService() {
        if (momentsService == null) {
            synchronized (RestApi.class) {
                if (momentsService == null) {
                    momentsService = XApi.getInstance().getRetrofit(USER, true).create(MomentsService.class);
                }
            }
        }
        return momentsService;
    }

}
