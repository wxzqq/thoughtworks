package com.thoughtworks.chengdu.gb.moments.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.awen.photo.photopick.controller.PhotoPagerConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.thoughtworks.chengdu.gb.moments.R;
import com.thoughtworks.chengdu.gb.moments.interfaces.OnItemClickListener;
import com.thoughtworks.chengdu.gb.moments.model.ActionItem;
import com.thoughtworks.chengdu.gb.moments.model.Comment;
import com.thoughtworks.chengdu.gb.moments.model.ImageModel;
import com.thoughtworks.chengdu.gb.moments.model.Sender;
import com.thoughtworks.chengdu.gb.moments.model.Tweet;
import com.thoughtworks.chengdu.gb.moments.util.DensityUtil;
import com.thoughtworks.chengdu.gb.moments.util.HelpUtils;
import com.thoughtworks.chengdu.gb.moments.widgets.ActionPopup;
import com.thoughtworks.chengdu.gb.moments.widgets.CollapseTextView;
import com.thoughtworks.chengdu.gb.moments.widgets.DividerGridItemDecoration;
import com.thoughtworks.chengdu.gb.moments.widgets.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

public class MomentsRecyclerAdapter extends BaseRecyclerAdapter<Tweet> {

    private Context mContext;

    private ActionPopup actionPopup;
    public MomentsRecyclerAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onBindChildHolder(BaseHolder holder, int childPosition) {
        final Tweet tweet = mData.get(childPosition);
        MomentsViewHolder viewHolder = (MomentsViewHolder) holder;
        Sender sender = tweet.getSender();
        //头像
        if (sender != null && HelpUtils.checkNull(sender.getAvatar())) {
            //Glide.with(mContext).load(sender.getAvatar()).placeholder(R.mipmap.default_hd_avatar).centerCrop().transform(new GlideRoundTransform(mContext)).into(viewHolder.sender_avater);
            RequestOptions options = new RequestOptions();
            options.placeholder(R.color.color_69).transform(new GlideRoundTransform(mContext)).diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(sender.getAvatar()).apply(options).into(viewHolder.sender_avater);

        } else {
            RequestOptions options = new RequestOptions();
            options.placeholder(R.color.color_69).transform(new GlideRoundTransform(mContext)).diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(R.mipmap.default_hd_avatar).apply(options).into(viewHolder.sender_avater);
            //Glide.with(mContext).load(R.mipmap.default_hd_avatar).centerCrop().transform(new GlideRoundTransform(mContext)).into(viewHolder.sender_avater);
        }
        //昵称
        if (sender != null && HelpUtils.checkNull(sender.getNick())) {
            viewHolder.sender_name.setText(sender.getNick());
        }
        //内容
        if (HelpUtils.checkNull(tweet.getContent())) {
            viewHolder.content.setText(tweet.getContent());
        }
        //设置日期
        viewHolder.comment_date.setText(HelpUtils.getTimeRange(HelpUtils.getSystemDate()));
        //设置图片
        List<ImageModel> tweetsImgs = tweet.getImages();
        if (tweetsImgs != null && tweetsImgs.size() > 0) {
            final List<String> imgDataList = new ArrayList<>();
            for (ImageModel imageModel : tweetsImgs) {
                if (HelpUtils.checkNull(imageModel.getUrl())) {
                    imgDataList.add(imageModel.getUrl());
                }
            }

            if (imgDataList != null & imgDataList.size() > 0) {
                MomentsImgsAdapter imgsAdapter = new MomentsImgsAdapter(mContext);
                viewHolder.imgs_recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                DividerGridItemDecoration decoration = new DividerGridItemDecoration(10);
                decoration.outRectType = DividerGridItemDecoration.OutRectType.OutRectTypeLR;
                viewHolder.imgs_recyclerView.addItemDecoration(decoration);
                viewHolder.imgs_recyclerView.setAdapter(imgsAdapter);
                imgsAdapter.addData(imgDataList);
                imgsAdapter.setListener(new OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int groupPosition, int childPosition, Object o, View view) {
                        showBigPics(childPosition,imgDataList);
                    }
                });
            }

        }
        //设置评论
        List<Comment> tweetsComments = tweet.getComments();
        if (tweetsComments != null && tweetsComments.size() > 0) {
            CommentAdapter commentAdapter = new CommentAdapter(mContext);
            viewHolder.comment_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            viewHolder.comment_recyclerView.setAdapter(commentAdapter);
            commentAdapter.addData(tweetsComments);
        }

        //
        viewHolder.user_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionPopup actionPopup = new ActionPopup(mContext, DensityUtil.dip2px(mContext, 165), DensityUtil.dip2px(
                        mContext, 40));
                actionPopup
                        .addAction(new ActionItem(mContext, "点赞", R.mipmap.circle_praise));
                actionPopup.addAction(new ActionItem(mContext, "评论", R.mipmap.circle_comment));
                actionPopup.setAnimationStyle(R.style.cricleBottomAnimation);
                actionPopup.show(view);
                actionPopup.setItemOnClickListener(onclick);
            }
        });

    }

    @Override
    protected BaseHolder onCreateHolder(ViewGroup viewGroup) {
        View view = getLayoutInflater().inflate(R.layout.item_moments, viewGroup, false);
        MomentsViewHolder holder = new MomentsViewHolder(view);
        return holder;
    }

    public static class MomentsViewHolder extends BaseHolder {
        @BindView(R.id.sender_avater)
        ImageView sender_avater;
        @BindView(R.id.sender_name)
        TextView sender_name;
        @BindView(R.id.content)
        CollapseTextView content;
        @BindView(R.id.imgs_recyclerView)
        RecyclerView imgs_recyclerView;
        @BindView(R.id.comment_date)
        TextView comment_date;
        @BindView(R.id.user_action)
        ImageView user_action;
        @BindView(R.id.comment_recyclerView)
        RecyclerView comment_recyclerView;

        public MomentsViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void showBigPics(int position,List<String> list) {
        new PhotoPagerConfig.Builder((Activity) mContext)
                .setBigImageUrls((ArrayList<String>) list)//大图片url,可以是sd卡res，asset，网络图片.
                //.setSmallImageUrls((ArrayList<String>) list)//小图图片的url,用于大图展示前展示的
                //.addSingleBigImageUrl(picUrl)//一张一张大图add进ArrayList
                //.addSingleSmallImageUrl(String smallImageUrl)//一张一张小图add进ArrayList
                .setSavaImage(false)//开启保存图片，默认false
                .setPosition(position)//默认展示第2张图片
                //.setSaveImageLocalPath("Android/SD/xxx/xxx")//这里是你想保存大图片到手机的地址,可在手机图库看到，不传会有默认地址
                //.setBundle(bundle)//传递自己的数据，如果数据中包含java bean，必须实现Parcelable接口
                .setOpenDownAnimate(true)//是否开启下滑关闭activity，默认开启。类似微信的图片浏览，可下滑关闭一样
                .build();
    }

    ActionPopup.OnItemOnClickListener onclick = new ActionPopup.OnItemOnClickListener() {
        @Override
        public void onItemClick(ActionItem item, int position) {

        }
    };
}
