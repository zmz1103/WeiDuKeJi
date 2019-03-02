package com.wd.tech.util;

import java.util.Date;

public class TimeUtil {
        private final static long MINUTE = 60 * 1000;// 1分钟
        private final static long HOUR = 60 * MINUTE;// 1小时
        private final static long DAY = 24 * HOUR;// 1天
        private final static long MONTH = 31 * DAY;// 月
        private final static long YEAR = 12 * MONTH;// 年

        /**
         * 返回文字描述的日期
         *
         * @param date
         * @return
         */
        public static String getTimeFormatText(Date date) {
            if (date == null) {
                return null;
            }
            long diff = new Date().getTime() - date.getTime();
            long r = 0;
            if (diff > YEAR) {
                r = (diff / YEAR);
                return r + "年前";
            }
            if (diff > MONTH) {
                r = (diff / MONTH);
                return r + "个月前";
            }
            if (diff > DAY) {
                r = (diff / DAY);
                return r + "天前";
            }
            if (diff > HOUR) {
                r = (diff / HOUR);
                return r + "小时前";
            }
            if (diff > MINUTE) {
                r = (diff / MINUTE);
                return r + "分钟前";
            }
            return "刚刚";
        }
    }
