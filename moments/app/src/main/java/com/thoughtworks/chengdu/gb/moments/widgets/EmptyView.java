package com.thoughtworks.chengdu.gb.moments.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtworks.chengdu.gb.moments.R;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.kit.KnifeKit;


public class EmptyView extends LinearLayout {

    @BindView(R.id.text_tip)
    TextView text_tip;
    @BindView(R.id.text_explain)
    TextView text_explain;

    public EmptyView(Context context) {
        super(context);
        setupView(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView(context);
    }

    private void setupView(Context context) {
        inflate(context, R.layout.weight_empty_view, this);
        KnifeKit.bind(this);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            text_tip.setText(msg);
        }
    }
    public void setExplain(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            text_explain.setText(msg);
        }
    }
}
