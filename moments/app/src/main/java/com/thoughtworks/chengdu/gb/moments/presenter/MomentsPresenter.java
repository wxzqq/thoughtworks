package com.thoughtworks.chengdu.gb.moments.presenter;

import com.thoughtworks.chengdu.gb.moments.activity.MomentsActivity;
import com.thoughtworks.chengdu.gb.moments.model.Paginator;
import com.thoughtworks.chengdu.gb.moments.model.Tweet;
import com.thoughtworks.chengdu.gb.moments.model.TweetsModel;
import com.thoughtworks.chengdu.gb.moments.model.UserInfo;
import com.thoughtworks.chengdu.gb.moments.network.CustomApiSubscriber;
import com.thoughtworks.chengdu.gb.moments.network.RestApi;
import com.thoughtworks.chengdu.gb.moments.util.CacheManager;
import com.thoughtworks.chengdu.gb.moments.util.Constants;
import com.thoughtworks.chengdu.gb.moments.util.HelpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by GB on 2018/04/30.
 */
public class MomentsPresenter extends XPresent<MomentsActivity> {

    /**
     * 获取登录用户的信息
     *
     * @param name
     */
    public void getUserInfo(String name) {
        RestApi.getMomentsService().getUserInfo(name)
                .compose(XApi.<UserInfo>getApiTransformer())
                .compose(XApi.<UserInfo>getScheduler())
                .compose(getV().<UserInfo>bindToLifecycle())
                .subscribe(new CustomApiSubscriber<UserInfo>() {
                    @Override
                    protected void onFail(NetError error, String message) {
                        getV().loadUserInfoError(message);
                    }

                    @Override
                    public void onFinish(UserInfo userInfo) {
                        getV().loadUserInfoSuccess(userInfo);
                    }
                });
    }

    /**
     * 获取所有推特信息
     *
     * @param name
     */
    public void getTweets(String name, int currentPage) {
        List<Tweet> tweets = CacheManager.get("tweets");
        if (tweets != null && tweets.size() > 0) {
            Map<String, Object> returnMap = getPageTweets(tweets, currentPage);
            getV().getTweetsSuccess(returnMap);
        } else {
            RestApi.getMomentsService().getTweets(name)
                    .compose(XApi.<TweetsModel>getApiTransformer())
                    .compose(XApi.<TweetsModel>getScheduler())
                    .compose(getV().<TweetsModel>bindToLifecycle())
                    .subscribe(new CustomApiSubscriber<TweetsModel>() {
                        @Override
                        protected void onFail(NetError error, String message) {
                            getV().getTweetsError(message);
                        }

                        @Override
                        public void onFinish(TweetsModel tweetsModel) {
                            if (tweetsModel != null && tweetsModel.getTweets() != null && tweetsModel.getTweets().size() > 0) {
                                List<Tweet> tweets = tweetsModel.getTweets();
                                List<Tweet> returnTweets = new ArrayList<>();
                                for (Tweet tweet : tweets) {
                                    if (tweet != null && (HelpUtils.checkNull(tweet.getContent()) || (tweet.getImages() != null && tweet.getImages().size() > 0))) {
                                        returnTweets.add(tweet);
                                    }
                                }
                                if (returnTweets != null && returnTweets.size() > 0) {
                                    CacheManager.put("tweets", returnTweets);
                                    //返回第一页数据
                                    Map<String, Object> returnMap = getPageTweets(returnTweets, 0);
                                    getV().getTweetsSuccess(returnMap);
                                } else {
                                    getV().getTweetsError("暂无数据");
                                }

                            } else {
                                getV().getTweetsError("暂无数据");
                            }
                        }
                    });
        }
    }

    /**
     * 分页获取数据
     *
     * @param tweets：数据
     * @param currentPage:当前页数
     * @return
     */
    private Map<String, Object> getPageTweets(List<Tweet> tweets, int currentPage) {
        List<Tweet> returnList = new ArrayList<>();
        Paginator paginator = new Paginator();
        paginator.setTotleNum(tweets.size());
        int totalPage = (tweets.size() % Constants.PAGE_SIZE > 0) ? 1 + tweets.size() / Constants.PAGE_SIZE : tweets.size() / Constants.PAGE_SIZE;
        paginator.setCurrentPage(currentPage);
        paginator.setTotlePages(totalPage);
        if (currentPage > totalPage) {
            paginator.setLength(0);
        } else {
            if (currentPage == totalPage) {
                paginator.setLength(tweets.size() - Constants.PAGE_SIZE * (currentPage - 1));
                for (int i = Constants.PAGE_SIZE * (currentPage - 1); i < tweets.size(); i++) {
                    returnList.add(tweets.get(i));
                }
            } else {
                paginator.setLength(Constants.PAGE_SIZE);
                for (int i = Constants.PAGE_SIZE * (currentPage - 1); i < Constants.PAGE_SIZE * currentPage; i++) {
                    returnList.add(tweets.get(i));
                }
            }
        }
        //设置返回值
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("dataList", returnList);
        returnMap.put("paginator", paginator);
        return returnMap;
    }


}
