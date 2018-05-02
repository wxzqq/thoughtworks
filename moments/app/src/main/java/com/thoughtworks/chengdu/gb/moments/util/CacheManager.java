package com.thoughtworks.chengdu.gb.moments.util;

import android.content.Context;

import com.thoughtworks.chengdu.gb.moments.model.Tweet;

import java.util.List;

public class CacheManager {
    /**
     * 只使用硬盘缓存(DiskLruCache)
     */
    public static final int ONLY_DISKLRU = 2;

    /**
     * 设置类型为硬盘缓存——用于取硬盘缓存大小
     */
    public static final int DISKSIZE = 0;

    //设置硬盘缓存的最大值，单位为M
    private static int maxSizeForDiskLruCache = 0;
    //设置自定义的硬盘缓存文件夹名称
    private static String dirNameForDiskLruCache = "";
    //硬盘缓存管理类
    private static DiskLruCacheManager diskLruCacheManager;

    private static Context ct;

    /**
     * 初始化缓存管理
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        ct = context;
        init_();
    }

    //根据传入的标志，初始化内存缓存以及硬盘缓存，默认开启是同时使用
    private static void init_() {
        initDiskLruCacheManager();
    }

    //初始化硬盘缓存管理
    private static void initDiskLruCacheManager() {
        if (maxSizeForDiskLruCache > 0 && dirNameForDiskLruCache != null && !"".equals(dirNameForDiskLruCache)) {
            diskLruCacheManager = new DiskLruCacheManager(ct, dirNameForDiskLruCache, maxSizeForDiskLruCache * 1024 * 1024);
        } else if (maxSizeForDiskLruCache > 0) {
            diskLruCacheManager = new DiskLruCacheManager(ct, maxSizeForDiskLruCache * 1024 * 1024);
        } else if (dirNameForDiskLruCache != null && !"".equals(dirNameForDiskLruCache)) {
            diskLruCacheManager = new DiskLruCacheManager(ct, dirNameForDiskLruCache);
        } else {
            diskLruCacheManager = new DiskLruCacheManager(ct);
        }
    }

    /**
     * 设置硬盘缓存的最大值，单位为兆（M）.
     *
     * @param maxSizeForDisk 硬盘缓存最大值，单位为兆（M）
     */
    public static void setMaxSize(int maxSizeForDisk) {
        maxSizeForDiskLruCache = maxSizeForDisk;
    }

    /**
     * 设置硬盘缓存自定义的文件名
     *
     * @param dirName 自定义文件名
     */
    public static void setDirName(String dirName) {
        dirNameForDiskLruCache = dirName;
    }

    /**
     * 索引key对应的tweetList写入缓存
     *
     * @param key       缓存索引
     * @param tweetList
     */
    public static void put(String key, List<Tweet> tweetList) {
        if (diskLruCacheManager != null) {
            diskLruCacheManager.putDiskCache(key, tweetList);
        }
    }

    /**
     * 获取索引key对应的缓存内容
     *
     * @param key 缓存索引key
     * @return key索引对应的Bitmap数据
     */
    public static List<Tweet> get(String key) {
        List<Tweet> tweetList = null;
        if (diskLruCacheManager != null) {
            tweetList = diskLruCacheManager.getDiskCache(key);
        }
        return tweetList;
    }

    /**
     * 删除所有缓存
     */
    public static void delete() {
        if (diskLruCacheManager != null) {
            diskLruCacheManager.deleteDiskCache();
        }
    }

    /**
     * 移除一条索引key对应的缓存
     *
     * @param key 索引
     */
    public static void remove(String key) {
        if (diskLruCacheManager != null) {
            diskLruCacheManager.removeDiskCache(key);
        }
    }

    /**
     * 缓存数据同步
     */
    public static void flush() {
        if (diskLruCacheManager != null) {
            diskLruCacheManager.fluchCache();
        }
    }

    /**
     * 删除特定文件名的缓存文件
     *
     * @param dirName 文件名
     */
    public static void deleteFile(String dirName) {
        if (diskLruCacheManager != null) {
            diskLruCacheManager.deleteFile(ct, dirName);
        }
    }

    /**
     * 获取缓存大小——硬盘缓存
     *
     * @return
     */
    public static int size() {
        int size = 0;
        if (diskLruCacheManager != null) {
            size += diskLruCacheManager.size();
        }
        return size;
    }

    /**
     * 获取缓存大小
     *
     * @param type 硬盘缓存类型：DISKSIZE、内存缓存类型：MEMORYSIZE
     * @return 对应类型的缓存大小
     */
    public static int size(int type) {
        int size = 0;
        if (diskLruCacheManager != null) {
            size += diskLruCacheManager.size();
        }
        return size;
    }

    /**
     * 关闭缓存
     */
    public static void close() {
        if (diskLruCacheManager != null) {
            diskLruCacheManager.close();
        }
    }
}
