package com.xuexiang.googlecomponentsdemo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xuexiang
 * @date 2018/3/13 下午5:26
 */
public final class DateUtils {

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final ThreadLocal<DateFormat> yyyyMMdd = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将时间字符串转为 Date 类型
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @return Date 类型
     */
    public static Date string2Date(final String time) {
        return string2Date(time, yyyyMMdd.get());
    }

    /**
     * 将时间字符串转为 Date 类型
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date 类型
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            if (format != null) {
                return format.parse(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 Date 类型转为时间字符串
     * <p>格式为 format</p>
     *
     * @param date   Date 类型时间
     * @return 时间字符串
     */
    public static String date2String(final Date date) {
        return date2String(date, yyyyMMdd.get());
    }

    /**
     * 将 Date 类型转为时间字符串
     * <p>格式为 format</p>
     *
     * @param date   Date 类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        if (format != null) {
            return format.format(date);
        } else {
            return "";
        }
    }

}
