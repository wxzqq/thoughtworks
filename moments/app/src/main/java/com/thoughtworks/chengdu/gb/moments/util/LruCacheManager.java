package com.thoughtworks.chengdu.gb.moments.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.thoughtworks.chengdu.gb.moments.model.Tweet;

import java.util.List;

public class LruCacheManager {
    private LruCache<String,List<Tweet>> lruCache;
    public LruCacheManager(){

    }

//    public LruCacheManager(int maxSize){
//        lruCache=new LruCache<String,List>(maxSize*1024){
//
//            @Override
//            protected int sizeOf(String key, List<Tweet> value) {
//                for (Tweet tweet: value) {
//                    tweet.
//                }
//                return value.getByteCount()/1024;
//            }
//
//        };
//    }


}
