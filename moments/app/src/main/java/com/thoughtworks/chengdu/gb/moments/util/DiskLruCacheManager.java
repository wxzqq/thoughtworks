package com.thoughtworks.chengdu.gb.moments.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.thoughtworks.chengdu.gb.moments.model.Tweet;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import cn.droidlover.xdroidmvp.cache.DiskLruCache;
import cn.droidlover.xdroidmvp.kit.Codec;

public class DiskLruCacheManager {
    private DiskLruCache diskLruCache;
    private static int maxSize = 30 * 1024 * 1024;
    private static final String defaultDirName = "thoughtworks";

    public DiskLruCacheManager(Context context) {
        this(context, defaultDirName, maxSize);
    }

    public DiskLruCacheManager(Context context, int maxDiskLruCacheSize) {
        this(context, defaultDirName, maxDiskLruCacheSize);
    }

    public DiskLruCacheManager(Context context, String dirName) {
        this(context, dirName, maxSize);
    }

    public DiskLruCacheManager(Context context, String dirName, int maxDiskLruCacheSize) {
        try {
            diskLruCache = DiskLruCache.open(getDiskCacheFile(context, dirName), getAppVersion(context), 1, maxDiskLruCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取文件目录（不存在则创建）
     *
     * @param context
     * @param dirName
     * @return
     */
    private File getDiskCacheFile(Context context, String dirName) {
        File cacheDir = packDiskCacheFile(context, dirName);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }

    /**
     * 获取缓存文件夹目录
     *
     * @param context
     * @param dirName
     * @return
     */
    private File packDiskCacheFile(Context context, String dirName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + dirName);
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 对可以进行MD5加密
     *
     * @param key
     * @return
     */
    private String getMD5Key(String key) {
        return Codec.MD5.getMessageDigest(key.getBytes());
    }

    /**
     * 将缓存记录同步到journal文件中
     */
    public void fluchCache() {
        if (diskLruCache != null) {
            try {
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据key获取disk中数据
     *
     * @param key
     * @return
     */
    public List<Tweet> getDiskCache(String key) {
        String md5Key = getMD5Key(key);
        List<Tweet> tweets = null;
        try {
            if (diskLruCache != null) {
                DiskLruCache.Snapshot snapshot = diskLruCache.get(md5Key);
                if (snapshot != null) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(snapshot.getInputStream(0));
                    tweets = (List<Tweet>) objectInputStream.readObject();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tweets;
    }

    /**
     * 将数据存放在disk上
     *
     * @param key
     * @param tweets
     * @return
     */
    public boolean putDiskCache(String key, List<Tweet> tweets) {
        String md5Key = getMD5Key(key);
        try {
            if (diskLruCache != null) {
                if (diskLruCache.get(md5Key) != null) {
                    return true;
                }
                DiskLruCache.Editor editor = diskLruCache.edit(md5Key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(tweets);
                    if (outputStream != null) {
                        editor.commit();
                        return true;
                    } else {
                        editor.abort();
                        return false;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除diskcache
     */
    public void deleteDiskCache() {
        try {
            if (diskLruCache != null) {
                diskLruCache.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除对应的缓存
     *
     * @param key
     */
    public void removeDiskCache(String key) {
        if (diskLruCache != null) {
            try {
                diskLruCache.remove(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除缓存文件
     *
     * @param context
     * @param dirName
     */
    public void deleteFile(Context context, String dirName) {
        try {
            DiskLruCache.deleteContents(packDiskCacheFile(context, dirName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存大小
     *
     * @return
     */
    public int size() {
        int size = 0;
        if (diskLruCache != null) {
            size = (int) diskLruCache.size();
        }
        return size;
    }

    /**
     * 关闭缓存
     */
    public void close() {
        if (diskLruCache != null) {
            try {
                diskLruCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
