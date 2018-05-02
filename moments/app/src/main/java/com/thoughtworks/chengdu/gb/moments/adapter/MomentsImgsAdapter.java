package com.thoughtworks.chengdu.gb.moments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.thoughtworks.chengdu.gb.moments.R;
import com.thoughtworks.chengdu.gb.moments.interfaces.OnItemClickListener;
import com.thoughtworks.chengdu.gb.moments.util.DensityUtil;
import com.thoughtworks.chengdu.gb.moments.util.HelpUtils;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by GB on 2018/05/01.
 */

public class MomentsImgsAdapter extends SimpleRecAdapter<String, MomentsImgsAdapter.ViewHolder> {
    public static final int TAG_VIEW = 0;
    private Context mContext;
    private OnItemClickListener mListener;

    public MomentsImgsAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_moments_img;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String iconUrl = data.get(position);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.comment_img.getLayoutParams();
        //获取当前控件的布局对象
        params.height = (int) ((width - DensityUtil.dip2px(mContext, 80)) / 3.0);
        params.width = params.height;
        holder.comment_img.setLayoutParams(params);
        if (HelpUtils.checkNull(iconUrl)) {
            RequestOptions options = new RequestOptions();
            options.placeholder(R.mipmap.load_img_default).diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(iconUrl).apply(options).into(holder.comment_img);
            //Glide.with(context).load(iconUrl).placeholder(R.mipmap.load_img_default).into(holder.comment_img);
        } else {
            Glide.with(context).load(R.mipmap.load_img_default).into(holder.comment_img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClickListener(0, position, iconUrl, view);
                }

            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.comment_img)
        ImageView comment_img;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    //设置监听事件
    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
