package com.thoughtworks.chengdu.gb.moments.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.thoughtworks.chengdu.gb.moments.R;
import com.thoughtworks.chengdu.gb.moments.adapter.MomentsRecyclerAdapter;
import com.thoughtworks.chengdu.gb.moments.base.BaseActivity;
import com.thoughtworks.chengdu.gb.moments.model.Paginator;
import com.thoughtworks.chengdu.gb.moments.model.Tweet;
import com.thoughtworks.chengdu.gb.moments.model.UserInfo;
import com.thoughtworks.chengdu.gb.moments.presenter.MomentsPresenter;
import com.thoughtworks.chengdu.gb.moments.util.HelpUtils;
import com.thoughtworks.chengdu.gb.moments.widgets.EmptyRecyclerView;
import com.thoughtworks.chengdu.gb.moments.widgets.RecycleViewDivider;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by GB on 2018/04/30.
 * moments
 */
public class MomentsActivity extends BaseActivity<MomentsPresenter> {

    @BindView(R.id.profile_img)
    ImageView profile_img;
    @BindView(R.id.avatar_img)
    ImageView avatar_img;
    @BindView(R.id.user_nick)
    TextView user_nick;
    @BindView(R.id.id_empty_view)
    View mEmptyView;
    @BindView(R.id.recyclerview)
    EmptyRecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private int mCurrentPage = 1;
    private boolean isLoadingMore = false;
    private MomentsRecyclerAdapter momentsAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_moments;
    }

    @Override
    public MomentsPresenter newP() {
        return new MomentsPresenter();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initDatas();
        initViews();
    }

    private void initDatas() {
        mCurrentPage = 1;
        getP().getUserInfo("jsmith");
    }

    private void initViews() {
        momentsAdapter = new MomentsRecyclerAdapter(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(momentsAdapter);
        mRecyclerView.setEmptyView(mEmptyView);

        mRecyclerView.addItemDecoration(new RecycleViewDivider(
                context, LinearLayoutManager.HORIZONTAL, 1, getResources().getColor(R.color.line_color)));
        refreshLayout.autoRefresh();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                momentsAdapter.clear();
                isLoadingMore = false;
                mCurrentPage = 1;
                HelpUtils.setDataLoadingTips(mEmptyView);
                getP().getUserInfo("jsmith");
                getP().getTweets("jsmith",mCurrentPage);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                HelpUtils.setDataLoadingTips(mEmptyView);
                isLoadingMore = true;
                getP().getTweets("jsmith",mCurrentPage);
            }
        });

        //设置默认
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float width = dm.widthPixels;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) profile_img.getLayoutParams();
        //获取当前控件的布局对象
        params.height = (int)(width*0.75);
        params.width = (int) width;
        profile_img.setLayoutParams(params);
        Glide.with(context).load(R.drawable.bg_gray).into(profile_img);
    }

    /**
     * 加载用户信息失败
     * @param msg
     */
    public void loadUserInfoError(String msg){
        //暂无操作
    }

    /**
     * 加载用户信息成功
     * @param userInfo
     */
    public void loadUserInfoSuccess(UserInfo userInfo){

        //设置profile
        if(HelpUtils.checkNull(userInfo.getProfileImage())) {
            //Glide.with(context).load(userInfo.getProfileImage()).placeholder(R.drawable.bg_gray).into(profile_img);
            RequestOptions options = new RequestOptions();
            options.placeholder(R.drawable.bg_gray).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(userInfo.getProfileImage()).apply(options).into(profile_img);
        } else {
            Glide.with(context).load(R.drawable.bg_gray).into(profile_img);
        }
        //设置头像
        if(HelpUtils.checkNull(userInfo.getAvatar())) {
            //Glide.with(context).load(userInfo.getAvatar()).placeholder(R.mipmap.default_nor_avatar).into(avatar_img);
            RequestOptions options = new RequestOptions();
            options.placeholder(R.mipmap.default_nor_avatar).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(userInfo.getProfileImage()).apply(options).into(avatar_img);
        } else {
            Glide.with(context).load(R.mipmap.default_nor_avatar).into(avatar_img);
        }
        if(HelpUtils.checkNull(userInfo.getNick())){
            user_nick.setText(userInfo.getNick());
        }

    }


    /**
     * 获取推特失败
     * @param msg
     */
    public void getTweetsError(String msg){
        getvDelegate().toastShort(msg);
        if (isLoadingMore) {
            refreshLayout.finishLoadmore();
        } else {
            refreshLayout.finishRefresh();
        }
        HelpUtils.setNoDataTips(mEmptyView);
    }

    /**
     * 获取推特成功
     * @param returnMap
     */
    public void getTweetsSuccess(Map<String, Object> returnMap){
        List<Tweet> pagelist = (List<Tweet>) returnMap.get("dataList");
        Paginator paginator = (Paginator) returnMap.get("paginator");
        if (isLoadingMore) {
            refreshLayout.finishLoadmore();
            if (paginator.getLength() == 0 || pagelist == null || pagelist.size() == 0) {
                if (mCurrentPage > 1) {
                    getvDelegate().toastShort("没有更多数据");
                }
            } else {
                mCurrentPage++;
                momentsAdapter.loadMore(pagelist);
            }
            momentsAdapter.loadMore(pagelist);
        } else {
            mCurrentPage = 2;
            refreshLayout.finishRefresh();
            momentsAdapter.refresh(pagelist);
        }
        HelpUtils.setNoDataTips(mEmptyView);
    }


}
