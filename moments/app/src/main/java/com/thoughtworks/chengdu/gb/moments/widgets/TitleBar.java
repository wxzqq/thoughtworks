package com.thoughtworks.chengdu.gb.moments.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thoughtworks.chengdu.gb.moments.R;
import com.thoughtworks.chengdu.gb.moments.util.DensityUtil;


public class TitleBar extends LinearLayout {

    private Context mContext;
    private ImageView ivBack;
    private TextView textRight;
    private TextView tvTitle;
    private RelativeLayout tbWrap;
    private int rightTag;//用于设置右边按钮的tag

    public TitleBar(Context context) {
        this(context, null);
        this.mContext = context;
    }

    public TitleBar(final Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.titlebar);

        Boolean helpVisible = a.getBoolean(R.styleable.titlebar_rightbtnvisibility, false);
        Boolean backVisible = a.getBoolean(R.styleable.titlebar_backvisibility, false);
//        Drawable serviceImage = a.getDrawable(R.styleable.titlebar_serviceimage);
        Drawable drawableleft = a.getDrawable(R.styleable.titlebar_drawableleft);
        String titleText = a.getString(R.styleable.titlebar_titletext);
        String rightBtnText = a.getString(R.styleable.titlebar_rightbtntext);
        a.recycle();

        //在构造函数中将Xml中定义的布局解析出来。
        View view = LayoutInflater.from(context).inflate(R.layout.weight_titlebar, this, true);
        tbWrap = (RelativeLayout) view.findViewById(R.id.tbWrap);
        ivBack = (ImageView) view.findViewById(R.id.imageBack);

        textRight = (TextView) view.findViewById(R.id.textRight);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        textRight.setVisibility(helpVisible ? VISIBLE : GONE);
        if (TextUtils.isEmpty(rightBtnText) && drawableleft != null) {
            // 这一步必须要做,否则不会显示.
            drawableleft.setBounds(0, 0, drawableleft.getMinimumWidth(), drawableleft.getMinimumHeight());
            textRight.setCompoundDrawables(drawableleft, null, null, null);
            textRight.setText("");
        } else {
            textRight.setText(rightBtnText);
        }

        int statusHeight = DensityUtil.getStatusBarHeight(context);
        tbWrap.setPadding(0, statusHeight, 0, 0);
        tvTitle.setPadding(0, statusHeight, 0, 0);
        textRight.setPadding(0, statusHeight, 0, 0);

        ivBack.setVisibility(backVisible ? VISIBLE : GONE);
        tvTitle.setText(titleText);
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        });
    }

    public void setTitleBarText(String title) {
        tvTitle.setText(title);
    }

    public void setBackOnClickListener(OnClickListener l) {
        ivBack.setOnClickListener(l);
    }

    public void setRightBtnOnClickListener(OnClickListener l) {
        textRight.setOnClickListener(l);
    }

    public void setRightBtnText(String text) {
        textRight.setText(text);
    }

    public void setRightBtnEnable(boolean enable) {
        textRight.setEnabled(enable);
    }

    public void setTitleBarBG(int bg) {
        tbWrap.setBackgroundResource(bg);
    }

    public void setRigthBtnVisibility(boolean visibility) {
        textRight.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public int getRightTag() {
        return rightTag;
    }

    public TextView getTextRight() {
        return textRight;
    }

    public void setRightTag(int rightTag) {
        this.rightTag = rightTag;
    }


}