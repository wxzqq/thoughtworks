package com.thoughtworks.chengdu.gb.moments.base;

import com.thoughtworks.chengdu.gb.moments.model.ResponseState;

import cn.droidlover.xdroidmvp.net.IModel;

/**
 * Created by GB on 2017/11/27.
 */

public class BaseModel implements IModel {

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
