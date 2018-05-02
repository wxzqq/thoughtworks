package com.thoughtworks.chengdu.gb.moments.network;

import android.os.Build;

import com.thoughtworks.chengdu.gb.moments.MomentsApplication;

import java.io.IOException;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by GB on 2018/04/30.
 */

public class RequestInterceptor implements Interceptor {

    /**
     * OkHttp请求头通过拦截器添加Header，两种方式的不同
     * .header(key, val):如果key相同，最后一个val会将前面的val值覆盖
     * .addHeader(key, val):如果key相同，最后一个val不会将前面的val值覆盖，而是新添加一个Header
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        NetStateProvider mNetStateProvider = new NetStateProvider(MomentsApplication.getContext());
        String currentNetworkType = "";
        String provider = "";
        if (mNetStateProvider.isNetworkAvailable()) {
            currentNetworkType = mNetStateProvider.getCurrentNetworkType();
            provider = mNetStateProvider.getProvider();
        }
        Request request = chain.request().newBuilder()
                .header("currentNetworkType", currentNetworkType)
                .header("provider", provider)
                .header("model", Build.MODEL)
                .header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_1_2 like Mac OS X) AppleWebKit/604.3.5 (KHTML, like Gecko) Mobile/15B202 Html5Plus/1.0 (Immersed/20)")
                .build();

        return chain.proceed(request);
    }
}
