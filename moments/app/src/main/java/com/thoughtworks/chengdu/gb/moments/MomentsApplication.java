package com.thoughtworks.chengdu.gb.moments;

import android.app.Application;
import android.content.Context;

import com.awen.photo.FrescoImageLoader;
import com.thoughtworks.chengdu.gb.moments.model.UserInfoModel;
import com.thoughtworks.chengdu.gb.moments.network.RequestInterceptor;
import com.thoughtworks.chengdu.gb.moments.network.ResponseInterceptor;
import com.thoughtworks.chengdu.gb.moments.util.CacheManager;
import com.thoughtworks.chengdu.swipe.CHSwipeBackHelper;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by GB on 2018/04/30.
 */

public class MomentsApplication extends Application {
    private static Context context;
    public static MomentsApplication instance = null;
    //设置用户信息
    public UserInfoModel.UserInfo userInfo;

    public static MomentsApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        instance = this;
        /**
         * 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        CHSwipeBackHelper.init(this, null);
        //初始化缓存数据
        CacheManager.init(context);
        //注册拦截器
        XApi.registerProvider(new NetProvider() {
            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[]{new RequestInterceptor(),new ResponseInterceptor()};
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {
            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        });

        //初始化
        FrescoImageLoader.init(this, android.R.color.holo_blue_light);
    }

    public static Context getContext() {
        return context;
    }
}
