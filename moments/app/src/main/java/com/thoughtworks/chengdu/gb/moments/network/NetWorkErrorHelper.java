package com.thoughtworks.chengdu.gb.moments.network;

import com.thoughtworks.chengdu.gb.moments.util.HelpUtils;

import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by GB on 2017/10/18.
 */

public class NetWorkErrorHelper {

    public static String getErrorMessage(NetError error) {
        error.printStackTrace();
        String returnMsg = "";
        if (error.getType() == NetError.AuthError) {
            returnMsg = "网络授权失败";
        } else if (error.getType() == NetError.NoConnectError) {
            returnMsg = "未连接网络";
        } else if (error.getType() == NetError.OtherError) {
            if (HelpUtils.checkNull(error.getMessage())) {
                returnMsg = error.getMessage();
            } else {
                returnMsg = "网络异常";
            }
        } else if (error.getType() == NetError.BusinessError) {
            if (HelpUtils.checkNull(error.getMessage())) {
                returnMsg = error.getMessage();
            } else {
                returnMsg = "服务端异常";
            }
        } else if (error.getType() == NetError.ParseError) {
            returnMsg = "数据格式转换异常";
        } else {
            if (HelpUtils.checkNull(error.getMessage())) {
                returnMsg = error.getMessage();
            } else {
                returnMsg = "服务端连接失败";
            }
        }
        return returnMsg;
    }
}
