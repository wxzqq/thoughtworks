package com.thoughtworks.chengdu.gb.moments.network;

import com.thoughtworks.chengdu.gb.moments.base.BaseModel;

import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.IModel;
import cn.droidlover.xdroidmvp.net.NetError;

/**
 * Created by GB on 2017/11/28.
 */
public abstract class CustomApiSubscriber<T extends IModel> extends ApiSubscriber<T> {

    @Override
    protected void onFail(NetError error) {
        onFail(error, NetWorkErrorHelper.getErrorMessage(error));
    }

    @Override
    public void onNext(T t) {
        if(t instanceof BaseModel){
            BaseModel model=(BaseModel)t;
            if (model != null) {
                onFinish((T) model);
            }else{
                onFail(new NetError("数据类型转换错误",NetError.BusinessError));
            }
        }
    }

    protected abstract void onFinish(T e);

    protected abstract void onFail(NetError e, String message);
}
