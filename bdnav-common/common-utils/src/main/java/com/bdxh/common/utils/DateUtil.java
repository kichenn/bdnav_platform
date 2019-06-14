package com.bdxh.common.utils;


import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 时间操作帮助类
 * @Author: Kang
 * @Date: 2019/3/12 17:35
 */
public class DateUtil {

    public static String DATE_FORMAT_SHORT = "yyyyMMddHHmmss";

    /**
     * 将日期格式化为字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取当前时间的字符串
     *
     * @return
     */
    public static String now1() {
        return format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取当前时间的字符串
     *
     * @return
     */
    public static String now2() {
        return format(new Date(), "yyyy-MM-dd");
    }

    public static Date now() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 将字符串转为日期
     *
     * @param date
     * @param format
     * @return
     */
    public static Date format(String date, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取两个字符串日期相隔天数
     *
     * @param date1 yyyy-MMd-dd
     * @param date2 yyyy-MMd-dd
     * @return
     */
    public static int getDaySpace(String date1, String date2) {

        try {
            LocalDate start = LocalDate.parse(date1);
            LocalDate end = LocalDate.parse(date2);
            return end.compareTo(start);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取两个字符串日期相隔时间(格式如:N小时N分)
     *
     * @param date1
     * @param date2
     * @return
     */
    public static String getTimeSpace(String date1, String date2, String format) {

        try {
            DateTimeFormatter strToDateFormatter = DateTimeFormatter.ofPattern(format);
            TemporalAccessor temporalAccessor1 = strToDateFormatter.parse(date1);
            TemporalAccessor temporalAccessor2 = strToDateFormatter.parse(date2);
            Duration duration = Duration.between(LocalDateTime.from(temporalAccessor1),
                    LocalDateTime.from(temporalAccessor2));
            StringBuilder formatTime = new StringBuilder();
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            if (hours != 0) {
                formatTime.append(hours).append("小时");
            }
            if (minutes != 0) {
                formatTime.append(minutes).append("分钟");
            }
            return formatTime.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 两个时长之和
     *
     * @param time1 格式N小时N分
     * @param time2 格式N小时N分
     * @return 返回格式N小时N分
     */
    public static String getTwoTimeSum(String time1, String time2) {

        try {
            int hour1 = getHour(time1);
            int minute1 = getMinute(time1);

            int hour2 = getHour(time2);
            int minute2 = getMinute(time2);

            long hours = hour1 + hour2 + (minute1 + minute2) / 60;
            long minutes = (minute1 + minute2) % 60;

            StringBuilder formatTime = new StringBuilder();
            if (hours != 0) {
                formatTime.append(hours).append("小时");
            }
            if (minutes != 0) {
                formatTime.append(minutes).append("分钟");
            }
            return formatTime.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private static int getHour(String time) {
        int hour = 0;
        if (time.indexOf("小时") != -1) {
            hour = Integer.parseInt(time.split("小时")[0]);
        }
        return hour;
    }

    private static int getMinute(String time) {
        int minute = 0;
        if (time.indexOf("小时") != -1) {
            if (time.indexOf("分") == -1) {
                return minute;
            }
            time = time.split("小时")[1];
        }
        minute = Integer.parseInt(time.split("分")[0]);

        return minute;
    }

    /**
     * 返回
     *
     * @param time 格式hh:mm
     * @return 返回格式 N小时N分
     */
    public static String formatTime(String time, String format) {

        try {
            if (StringUtils.isBlank(format)) {
                format = "HH:mm";
            }
            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(format));
            long hours = localTime.getHour();
            long minutes = localTime.getMinute();
            StringBuilder formatTime = new StringBuilder();
            if (hours != 0) {
                formatTime.append(hours).append("小时");
            }
            if (minutes != 0) {
                formatTime.append(minutes).append("分钟");
            }
            return formatTime.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取UTC时间
     *
     * @return
     */
    public static Date getUTCTime() {
        // 1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        // 2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        return cal.getTime();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getNowTime() {
        return new Date();
    }

    public static Date getNextMinuteTime(Date date, int minute) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);

        return cal.getTime();
    }

    /**
     * 两个时间差(秒)
     *
     * @param before
     * @param after
     * @return
     */
    public static long getSecondsOfTwoDate(Date before, Date after) {

        long time = after.getTime() - before.getTime();

        return time / 1000;
    }

    /**
     * @param dateStr
     * @param fromFormat
     * @param toFormat
     * @return
     */
    public static String formatToOther(String dateStr, String fromFormat, String toFormat) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(fromFormat);
            Date date = dateFormat.parse(dateStr);
            SimpleDateFormat format = new SimpleDateFormat(toFormat);
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateStr;
    }

    /**
     * 是否为周末
     *
     * @param date
     * @return
     */
    public static boolean isWeekend(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int week = cal.get(Calendar.DAY_OF_WEEK);
        if (week == Calendar.SATURDAY || week == Calendar.SUNDAY) {
            return true;
        }

        return false;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) // 同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
                {
                    timeDistance += 366;
                } else // 不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else // 不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }


    public static String addDay(String day, int days) {
        if (StringUtils.isNotEmpty(day)) {
            Date date = format(day, "yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, days);// 今天+1天
            return format(c.getTime(), "yyyy-MM-dd");
        }
        return "";
    }

    public static String addDay1(String day, int days) {
        if (StringUtils.isNotEmpty(day)) {
            Date date = format(day, "yyyy/MM/dd");
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, days);// 今天+1天
            return format(c.getTime(), "yyyy/MM/dd");
        }
        return "";
    }


}