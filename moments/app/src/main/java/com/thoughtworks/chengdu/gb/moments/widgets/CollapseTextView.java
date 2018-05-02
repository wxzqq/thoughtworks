package com.thoughtworks.chengdu.gb.moments.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thoughtworks.chengdu.gb.moments.R;

public class CollapseTextView extends LinearLayout {
    CollapseTextViewHolder mTextViewHolder;
    View myView;

    /**
     * 真实行数
     */
    int realLineCounts = 0;

    /**
     * 默认行数
     */
    int defaultLineCounts = 5;

    /**
     * 真实高度
     */
    int realHeight = 0;

    /**
     * 折叠后的高度
     */
    int foldHeight = 0;

    boolean isFirstLoad = true;

    /**
     * 当前是否展开
     */
    boolean isExp = false;

    /**
     * 展开TextView
     */
    final int TEXT_OPEN = 1;

    /**
     * 关闭TextView
     */
    final int TEXT_CLOSE = 2;

    Handler mHander = new Handler() {
        LayoutParams layoutParma;

        public void handleMessage(android.os.Message msg) {
            int lines = (Integer) msg.obj;
            switch (msg.what) {
                case TEXT_OPEN: //打开，增加高度
                    mTextViewHolder.collapse_content.setMaxLines(lines);
                    break;
                case TEXT_CLOSE://关闭，减少高度
                    mTextViewHolder.collapse_content.setMaxLines(lines);
                    break;
            }
        }

        ;
    };

    public CollapseTextView(Context context) {
        super(context);
        init();
    }

    public CollapseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CollapseTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        myView = inflater.inflate(R.layout.collapse_textview, null);
        this.addView(myView, layoutParams);
        mTextViewHolder = new CollapseTextViewHolder(this);
    }

    public void setText(String text) {
        mTextViewHolder.collapse_content.setText(text);
        ViewTreeObserver vto = mTextViewHolder.collapse_content.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isFirstLoad) {
                    return;
                }

                //获取真实行数
                realLineCounts = mTextViewHolder.collapse_content.getLineCount();
                realHeight = mTextViewHolder.collapse_content.getMeasuredHeight();

                //如果真实行数大于默认的显示行数，则默认将其折叠起来  isExp为false
                if (realLineCounts > defaultLineCounts) {
                    mTextViewHolder.collapse_content.setMaxLines(defaultLineCounts);
                    mTextViewHolder.collapse_content.measure(0, 0);
                    foldHeight = mTextViewHolder.collapse_content.getMeasuredHeight();
                    mTextViewHolder.collapse_deal.setVisibility(View.VISIBLE);

                    //mTextViewHolder.collapse_content.setOnClickListener(collapseOnclick);
                    mTextViewHolder.collapse_deal.setOnClickListener(collapseOnclick);
                    isExp = false;
                } else {
                    //如果真实行数小于默认行数，则直接展示出来。isExp为true;
                    mTextViewHolder.collapse_deal.setVisibility(View.GONE);
                    isExp = true;
                }

                isFirstLoad = false;
            }
        });
    }

    View.OnClickListener collapseOnclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isExp) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int endcount = realLineCounts;
                        while (endcount-- > defaultLineCounts) {
                            Message msg = Message.obtain();
                            msg.what = TEXT_CLOSE;
                            msg.obj = endcount;
                            mHander.sendMessage(msg);
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }).start();
                mTextViewHolder.collapse_deal.setText(getResources().getString(R.string.collapse_full_title));
                isExp = false;
            } else {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        int startcount = defaultLineCounts;
                        while (startcount++ < realLineCounts) {
                            Message msg = Message.obtain();
                            msg.what = TEXT_OPEN;
                            msg.obj = startcount;
                            mHander.sendMessage(msg);
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                mTextViewHolder.collapse_deal.setText(getResources().getString(R.string.collapse_title));
                isExp = true;
            }
        }
    };

    static class CollapseTextViewHolder {
        TextView collapse_content;
        TextView collapse_deal;

        public CollapseTextViewHolder(View v) {
            collapse_content = (TextView) v.findViewById(R.id.collapse_content);
            collapse_deal = (TextView) v.findViewById(R.id.collapse_deal);
        }
    }
}
