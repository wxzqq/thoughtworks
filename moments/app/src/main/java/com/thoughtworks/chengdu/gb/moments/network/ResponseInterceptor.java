package com.thoughtworks.chengdu.gb.moments.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by GB on 2018/04/30.
 */

public class ResponseInterceptor implements Interceptor {

    /**
     * 拦截response对profile-image的处理
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String bodyStr = response.body().string();
        if (bodyStr.contains("profile-image")) {
            String resultStr = bodyStr.replace("profile-image", "profileImage");
            ResponseBody responseBody = ResponseBody.create(null, resultStr);
            return response.newBuilder()
                    .body(responseBody)
                    .build();
        }else if(bodyStr.contains("content")){
            String resultStr = "{\"tweets\":"+bodyStr+"}";
            ResponseBody responseBody = ResponseBody.create(null, resultStr);
            return response.newBuilder()
                    .body(responseBody)
                    .build();
        }
        return response;
    }

}
