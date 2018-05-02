package com.thoughtworks.chengdu.gb.moments.model;

import com.thoughtworks.chengdu.gb.moments.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GB on 2018/04/30.
 */

public class TweetsModel extends BaseModel implements Serializable{
    private List<Tweet> tweets;

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }
}
