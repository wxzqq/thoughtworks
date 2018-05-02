package com.thoughtworks.chengdu.gb.moments.model;

import android.support.annotation.DrawableRes;

/**
 * Created by Administrator on 2017/11/28.
 */

public class MainItem {
    @DrawableRes
    private int srcId;
    private String title;
    private Class<?> jumpClass;

    public int getSrcId() {
        return srcId;
    }

    public void setSrcId(int srcId) {
        this.srcId = srcId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class<?> getJumpClass() {
        return jumpClass;
    }

    public void setJumpClass(Class<?> jumpClass) {
        this.jumpClass = jumpClass;
    }
}
