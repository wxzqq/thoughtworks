package com.thoughtworks.chengdu.gb.moments.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.thoughtworks.chengdu.gb.moments.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.INPUT_METHOD_SERVICE;
/**
 * Created by GB on 2017/7/19.
 */

public class HelpUtils {
    /**
     * 设置每个阶段时间
     */
    private static final int seconds_of_1minute = 60;
    private static final int seconds_of_1hour = 60 * 60;
    private static final int seconds_of_1day = 24 * 60 * 60;
    private static final int seconds_of_7days = seconds_of_1day * 8;

    /**
     * 处理字符串
     *
     * @param obj
     * @return
     */
    public static String getStr(Object obj) {
        String str = "";
        if (obj != null && !"".equals(obj)) {
            str = obj.toString().trim();
        }
        return str;
    }

    /**
     * 字符串非空判断
     *
     * @param str
     * @return boolean true:不为空; false：空
     */
    public static boolean checkNull(Object str) {
        boolean falg = false;
        if (null != str && !"".equals(str.toString().trim())) {
            falg = true;
        }
        return falg;
    }

    /**
     * 时间格式转换
     *
     * @param data
     * @return
     */
    public static String formateData(int data) {
        if (data > 9) {
            return data + "";
        } else {
            return "0" + data;
        }
    }


    /*
     * 获取系统年月日
     * */
    public static String getSystemDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(new Date());
        return dateStr;
    }

    public static int computeInitialSampleSize(BitmapFactory.Options options,
                                               int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 没有数据的提示
     *
     * @param emptyView
     */
    public static void setNoDataTips(View emptyView) {
        TextView tipView = (TextView) emptyView.findViewById(R.id.text_tip);
        tipView.setText("还没有相应的数据");
        TextView explainView = (TextView) emptyView.findViewById(R.id.text_explain);
        explainView.setText("请下拉刷新重新加载");
    }

    /**
     * 数据加载中的提示
     *
     * @param emptyView
     */
    public static void setDataLoadingTips(View emptyView) {
        TextView tipView = (TextView) emptyView.findViewById(R.id.text_tip);
        tipView.setText("信息加载中...");
        TextView explainView = (TextView) emptyView.findViewById(R.id.text_explain);
        explainView.setText("正在加载信息，请等待...");
    }

    /**
     * 格式化时间显示为分钟前，小时前，
     *
     * @param mTime
     * @return
     */
    public static String getTimeRange(String mTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String dataStrNew = sdf.format(curDate);
        Date startTime = null;
        try {
            curDate = sdf.parse(dataStrNew);
            startTime = sdf.parse(mTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long between = (curDate.getTime() - startTime.getTime()) / 1000;
        int elapsedTime = (int) (between);
        if (elapsedTime < seconds_of_1minute) {
            return "刚刚";
        }
        if (elapsedTime < seconds_of_1hour) {
            return elapsedTime / seconds_of_1minute + "分钟前";
        }
        if (elapsedTime < seconds_of_1day) {
            return elapsedTime / seconds_of_1hour + "小时前";
        }
        if (elapsedTime < seconds_of_7days) {

            return elapsedTime / seconds_of_1day + "天前";
        }
        return sdf.format(startTime).replace("T", " ");

    }

}
