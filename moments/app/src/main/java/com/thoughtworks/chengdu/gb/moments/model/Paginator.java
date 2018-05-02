package com.thoughtworks.chengdu.gb.moments.model;

import java.io.Serializable;

/**
 * 用于项目中分页
 * Created by GB on 2018/04/30.
 */

public class Paginator implements Serializable {
    private int length;//每一页的长度
    private int totleNum;//总的条数
    private int currentPage;//当前页数
    private int totlePages;//总页数

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTotleNum() {
        return totleNum;
    }

    public void setTotleNum(int totleNum) {
        this.totleNum = totleNum;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotlePages() {
        return totlePages;
    }

    public void setTotlePages(int totlePages) {
        this.totlePages = totlePages;
    }
}
