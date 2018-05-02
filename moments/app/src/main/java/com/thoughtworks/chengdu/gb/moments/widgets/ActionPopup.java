package com.thoughtworks.chengdu.gb.moments.widgets;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.thoughtworks.chengdu.gb.moments.R;
import com.thoughtworks.chengdu.gb.moments.model.ActionItem;

import java.util.ArrayList;

public class ActionPopup extends PopupWindow {

    private TextView priase;
    private TextView comment;

    private Context mContext;

    protected final int LIST_PADDING = 10;

    private Rect mRect = new Rect();

    private final int[] mLocation = new int[2];

    private int mScreenWidth, mScreenHeight;

    private boolean mIsDirty;

    private int popupGravity = Gravity.NO_GRAVITY;

    private OnItemOnClickListener mItemOnClickListener;

    private ArrayList<ActionItem> mActionItems = new ArrayList<ActionItem>();

    public ActionPopup(Context context) {
        this(context, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    public ActionPopup(Context context, int width, int height) {
        this.mContext = context;
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);

        setWidth(width);
        setHeight(height);

        setBackgroundDrawable(new BitmapDrawable());

        View view = LayoutInflater.from(mContext).inflate(R.layout.action_popu, null);
        setContentView(view);
        priase = (TextView) view.findViewById(R.id.popu_praise);
        comment = (TextView) view.findViewById(R.id.popu_comment);
        priase.setOnClickListener(onclick);
        comment.setOnClickListener(onclick);
    }

    /**
     * 显示
     */
    public void show(final View c) {
        c.getLocationOnScreen(mLocation);
        mRect.set(mLocation[0], mLocation[1], mLocation[0] + c.getWidth(),
                mLocation[1] + c.getHeight());
        priase.setText(mActionItems.get(0).mTitle);
        if (mIsDirty) {
            // populateActions();
        }
        // showAtLocation(view, popupGravity, mScreenWidth - LIST_PADDING
        // - (getWidth() / 2), mRect.bottom);
        showAtLocation(c, Gravity.NO_GRAVITY, mLocation[0] - this.getWidth()
                - 10, mLocation[1] - ((this.getHeight() - c.getHeight()) / 2));
    }

    OnClickListener onclick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            switch (v.getId()) {
                case R.id.popu_comment:
                    mItemOnClickListener.onItemClick(mActionItems.get(1), 1);
                    break;
                case R.id.popu_praise:
                    mItemOnClickListener.onItemClick(mActionItems.get(0), 0);
                    break;
            }
        }

    };

    /**
     * 添加操作
     */
    public void addAction(ActionItem action) {
        if (action != null) {
            mActionItems.add(action);
            mIsDirty = true;
        }
    }

    public void cleanAction() {
        if (mActionItems.isEmpty()) {
            mActionItems.clear();
            mIsDirty = true;
        }
    }

    /**
     * 添加item
     */
    public ActionItem getAction(int position) {
        if (position < 0 || position > mActionItems.size())
            return null;
        return mActionItems.get(position);
    }

    /**
     * 设置点击事件
     */
    public void setItemOnClickListener(
            OnItemOnClickListener onItemOnClickListener) {
        this.mItemOnClickListener = onItemOnClickListener;
    }

    public static interface OnItemOnClickListener {
        public void onItemClick(ActionItem item, int position);
    }
}
