package com.thoughtworks.chengdu.gb.moments.interfaces;

import android.view.View;


public interface OnItemClickListener<T> {
    void onItemClickListener(int groupPosition, int childPosition, T t, View view);
}
