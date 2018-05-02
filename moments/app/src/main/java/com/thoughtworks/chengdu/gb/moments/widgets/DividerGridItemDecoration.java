package com.thoughtworks.chengdu.gb.moments.widgets;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by GB on 2017/11/2.
 */

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration
{

    private int space=20;
    public OutRectType outRectType=OutRectType.OutRectTypeLR;

    public DividerGridItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        switch (outRectType){
            case OutRectTypeL:
                outRect.left = space;
                break;
            case OutRectTypeR:
                outRect.right = space;
                break;
            case OutRectTypeLR:
                outRect.left = space;
                outRect.right = space;
                break;
            case OutRectTypeB:
                outRect.bottom = space;
                break;
            case OutRectTypeT:
                outRect.top=space;
                break;
            case OutRectTypeTB:
                outRect.top=space;
                outRect.bottom=space;
                break;
            default:
                outRect.left = space;
                outRect.right = space;
                break;
        }
    }

    public static enum OutRectType{
        OutRectTypeL,
        OutRectTypeR,
        OutRectTypeLR,
        OutRectTypeTB,
        OutRectTypeT,
        OutRectTypeB,
    }
}