package com.thoughtworks.chengdu.gb.moments.interfaces;

import android.view.View;


public interface OnItemViewClickListener<T> {
    void onItemChildClickListener(int position, int type, T t, View view);
}
