package com.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by guo on 17-12-12.
 */

public class TimeUtil {

    public static final long day0_0_1 = 1262275201000L;//2010-01-1 0:0:1.000
    public static final long day23_59_59 = 1262361599000L;//2010-01-1 23:59:59.000

    /**
     * 秒 转 毫秒
     */
    public static final int S_TO_MS = 1000;
    /**
     * 分 转 毫秒
     */
    public static final int M_TO_MS = 60 * 1000;

    /**
     * 时 转 毫秒
     */
    public static final int H_TO_MS = 60 * 60 * 1000;

    /**
     * 天 转 毫秒
     */
    public static final int D_TO_MS = 24 * 60 * 60 * 1000;

    public static final String FORMAT_AM_PM_TIME = "";
    public static final String FORMAT_YY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_STRING_YY_MM_D = "%tY/%<tm/%<te";
    public static final String FORMAT_STRING_HH_mm = "%tR";
    public static final String FORMAT_YYnianMMyueDDri = "yyyy年MM月dd日";
    public static final String FORMAT_YY_MM_DD_H_M_S = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YY_MM_DD_H_M = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YYnianMMyueDDriH_M = "yyyy年MM月dd日 HH:mm";
    public static final String FORMAT_YYnianMMyueDDriW = "%tY年%<tm月%<td\t%<tA";
    public static final String FORMAT_YYnianMMyueDDriAP_HH_mm = "%tY年%<tm月%<td日    %<tp%<tI:%<tM";
    public static final String FORMAT_H_M = "HH:mm";
    public static String FORMAT_YY_MM = "yyyy-MM";
    public static String FORMAT_MM_DD = "MM月dd日";

    /**
     * 返回当前时间
     *
     * @return
     */
    public static long getNowTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 返回当前时间
     *
     * @return
     */
    public static Calendar getInstanceCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(getNowTimeMillis());
        return calendar;
    }


    /**
     * Date转时间字符串
     *
     * @param data       Date类型的时间
     * @param formatType 格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * 时间戳转字符串
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType  要转换的string类型的时间格式
     * @return
     * @throws ParseException
     */
    public static String longToString(long currentTime, SimpleDateFormat formatType) {
        Date date = longToDate(currentTime); // long类型转成Date类型
        //        String strTime = dateToString(date, formatType); // date类型转成String
        return formatType.format(date);
    }

    /**
     * 时间戳转字符串
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType  要转换的string类型的时间格式
     * @return
     * @throws ParseException
     */
    public static String longToString(long currentTime, String formatType) {
        Date date = longToDate(currentTime); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }


    /**
     * 时间戳转字符串
     *
     * @param currentLongTime 要转换的long类型的时间 String
     * @param formatType      要转换的string类型的时间格式
     * @return
     * @throws ParseException
     */
    public static String longToString(String currentLongTime, String formatType) {
        return longToString(Long.valueOf(currentLongTime), formatType);
    }

    /**
     * 字符串转Date
     *
     * @param strTime    要转换的string类型的时间
     * @param formatType 要转换的格式yyyy-MM-dd HH:mm:ss yyyy年MM月dd日 HH时mm分ss秒，
     * @return
     * @throws ParseException strTime的时间格式必须要与formatType的时间格式相同
     */
    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 时间戳转Date
     *
     * @param currentTime 要转换的long类型的时间
     * @return
     * @throws ParseException
     */
    public static Date longToDate(long currentTime) {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        //        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        //        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return dateOld;
    }

    /**
     * 时间戳转Date
     *
     * @param timeString 要转换的long类型的时间
     * @return
     */
    public static Calendar longToCalendar(String timeString, String formatType) {
        Calendar dateOld = Calendar.getInstance();
        try {
            dateOld.setTimeInMillis(stringToLong(timeString, formatType));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return dateOld;
        }

    }


    /**
     * 字符串转时间戳
     *
     * @param strTime    要转换的String类型的时间
     * @param formatType 时间格式
     * @return
     * @throws ParseException strTime的时间格式和formatType的时间格式必须相同
     */
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }


    /**
     * Date转时间戳
     *
     * @param date 要转换的date类型的时间
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 当天时间
     *
     * @param time yyyy-MM-dd
     * @return
     */
    public static long getDayTimes(String time) {
        long times = 0L;
        try {
            times = stringToLong(time, "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return times;
        }
    }

    /**
     * 现在 月一日时间
     *
     * @return
     */
    public static long getCurrMonthTimes() {
        long times = getNowTimeMillis();
        try {
            String time = longToString(times, "yyyy-MM");
            times = stringToLong(time + "-01", "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return times;

    }

    /**
     * 返回系统当前时间 Calendar
     *
     * @return
     */
    public static Calendar getToDay() {
        Calendar today = Calendar.getInstance();
        today.setTimeInMillis(getNowTimeMillis());
        return today;
    }

    /**
     * 现在 日时间
     *
     * @return
     */
    public static long getCurrDayTimes() {
        long times = getNowTimeMillis();
        String time = longToString(times, "yyyy-MM-dd");
        times = getDayTimes(time);

        return times;

    }

    /**
     * 现在 日时间
     *
     * @return
     */
    public static long getCurrDayTimes(int year, int month, int day) {
        long times = 0;
        try {
            times = stringToLong(year + "-" + month + "-" + day, "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }


    /**
     * 返回 下个月的时间差
     *
     * @param nowTimeMillis
     * @return
     */
    public static long getNextMonthDifferenceTime(long nowTimeMillis) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTimeInMillis(nowTimeMillis);
        newCalendar.add(Calendar.MONTH, 1);

        long nextCalMillis = newCalendar.getTimeInMillis();
        return nextCalMillis - nowTimeMillis;


    }

    /**
     * 返回 下一年的时间差
     *
     * @param nowTimeMillis
     * @return
     */
    public static long getNextYearDifferenceTime(long nowTimeMillis) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTimeInMillis(nowTimeMillis);
        newCalendar.add(Calendar.YEAR, 1);
        long nextCalMillis = newCalendar.getTimeInMillis();
        return nextCalMillis - nowTimeMillis;


    }

    /**
     * 返回时间格式
     * <p>
     * a 星期几  %ta  a/A 小写简称大写全称
     * <p>
     * b 月份  %tb  b/B 小写简称大写全称
     * <p>
     * C 年份年前两位  y 年份后两位 Y年份全称
     * <p>
     * j 一年的天数，一年中第几天
     * <p>
     * m 两位数子的月份，不足两位补0
     * <p>
     * d 两位数子的日，不足两位补0
     * <p>
     * e 日，不补0
     * <p>
     * c:包括全部日期和时间信息:星期六 十月 27 14:21:20 CST 2007
     * <p>
     * F:"年-月-日"格式:2007-10-27
     * <p>
     * D:"月/日/年"格式:10/27/07
     * <p>
     * r:"HH:MM:SS PM"格式（12时制）:02:25:51 下午
     * <p>
     * T:"HH:MM:SS"格式（24时制）:14:28:16
     * <p>
     * R:"HH:MM"格式（24时制）:14:28
     * <p>
     * %t之后用H表示输出时间的时（24进制），%t之后用I表示输出时间的时（12进制），%t之后用M表示输出时间的分，%t之后用S表示输出时间的秒
     * <p>
     * %t之后用L表示输出时间的秒中的毫秒
     * <p>
     * %t之后p表示输出时间的上午或下午信息
     *
     * @param format
     * @param calendar
     * @return
     */
    public static String getFormatStringCalendar(String format, Calendar calendar) {
        Date date = calendar.getTime();
        return getFormatStringDate(format, date);

    }

    public static String getFormatStringDate(String format, Date date) {
        return String.format(Locale.CHINESE, format, date);
    }

    public static String getFormatStringDateMillis(String format, long millis) {
        Date date = new Date(millis);
        return getFormatStringDate(format, date);
    }

    /**
     * 两个时间作比较->交换
     *
     * @param minCalendar 结果小
     * @param maxCalendar 结果大
     */
    public static void compareReplacementCalendar(Calendar minCalendar, Calendar maxCalendar) {
        if (minCalendar.getTimeInMillis() > maxCalendar.getTimeInMillis()) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(minCalendar.getTimeInMillis());
            minCalendar.setTimeInMillis(maxCalendar.getTimeInMillis());
            maxCalendar.setTimeInMillis(c.getTimeInMillis());
        }

    }

    /**
     * 两个时间作比较->交换
     *
     * @param minCalendar
     * @param maxCalendar
     */
    public static long compareCalendar(Calendar minCalendar, Calendar maxCalendar) {
        return minCalendar.getTimeInMillis() - maxCalendar.getTimeInMillis();
    }


    /**
     * 判断是否是今天 返回0
     * <p>未来 : > 0
     * <p>过去 : < 0
     *
     * @param calendar 当前时间
     * @return
     */
    public static long isToday(Calendar calendar) {
        Calendar calendarToday = getToDay();
        if (calendar.get(Calendar.YEAR) == calendarToday.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.MONTH) == calendarToday.get(Calendar.MONTH)) {
                if (calendar.get(Calendar.DAY_OF_MONTH) == calendarToday.get(Calendar.DAY_OF_MONTH)) {
                    return 0;
                }
            }
        }


        return calendar.getTimeInMillis() - calendarToday.getTimeInMillis();
    }

    /**
     * * 判断是否是今天 返回0
     * <p>未来 : > 0
     * <p>过去 : < 0
     *
     * @param calendarMillis 当前时间戳
     * @return
     */
    public static long isToday(long calendarMillis) {
        Calendar calendarToday = getToDay();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendarMillis);
        if (calendar.get(Calendar.YEAR) == calendarToday.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.MONTH) == calendarToday.get(Calendar.MONTH)) {
                if (calendar.get(Calendar.DAY_OF_MONTH) == calendarToday.get(Calendar.DAY_OF_MONTH)) {
                    return 0;
                }
            }
        }


        return calendar.getTimeInMillis() - calendarToday.getTimeInMillis();
    }


    /**
     * 判断是否是据今天多少天
     *
     * @param millis 传入的日期
     * @param day    多少
     * @return
     */
    public static boolean isNowDay(long millis, int day) {
        Calendar calendarToday = getToDay();
        calendarToday.add(Calendar.DAY_OF_MONTH, day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        if (calendar.get(Calendar.YEAR) == calendarToday.get(Calendar.YEAR)) {
            if (calendar.get(Calendar.MONTH) == calendarToday.get(Calendar.MONTH)) {
                return calendar.get(Calendar.DAY_OF_MONTH) == calendarToday.get(Calendar.DAY_OF_MONTH);
            }
        }
        return false;

    }


    /**
     * 判断是否是当月
     *
     * @param calendar 当前时间
     * @return
     */
    public static int isCurrMonth(Calendar calendar) {
        Calendar calendarToday = getToDay();
        return (calendar.get(Calendar.YEAR) - calendarToday.get(Calendar.YEAR)) * 12 + calendar.get(Calendar.MONTH) - calendarToday.get(Calendar.MONTH);

    }


    /**
     * 时间转换
     *
     * @param date
     * @return
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    /**
     * 返回 月初月末
     *
     * @param year
     * @param month
     * @return
     */
    public static long[] getMonthTimeS_E(int year, int month) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(year, month - 1, 1, 0, 0, 0);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(year, month, 1, 0, 0, 0);
        return new long[]{startCalendar.getTimeInMillis() / S_TO_MS, endCalendar.getTimeInMillis() / S_TO_MS};


    }

    /**
     * 返回 月初月末
     *
     * @param calendar
     * @return 单位 秒
     */
    public static long[] getMonthTimeS_E(Calendar calendar) {

        return getMonthTimeS_E(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);


    }

    /**
     * 返回 月初月末
     *
     * @param year
     * @return
     */
    public static long[] getYearTimeS_E(int year) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(year, 0, 1, 0, 0, 0);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(year + 1, 0, 1, 0, 0, 0);
        return new long[]{startCalendar.getTimeInMillis() / S_TO_MS, endCalendar.getTimeInMillis() / S_TO_MS};
    }

    /**
     * 返回 月初月末
     *
     * @param calendar
     * @return 单位 秒
     */
    public static long[] getYearTimeS_E(Calendar calendar) {
        return getYearTimeS_E(calendar.get(Calendar.YEAR));


    }

    /**
     * 返回 日初日末
     *
     * @param year
     * @param month
     * @return 单位 秒
     */
    public static long[] getDayTimeS_E(int year, int month, int day) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(year, month - 1, day, 0, 0, 0);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(year, month - 1, day + 1, 0, 0, 0);
        return new long[]{startCalendar.getTimeInMillis() / S_TO_MS, endCalendar.getTimeInMillis() / S_TO_MS};


    }

    /**
     * 返回 月初月末
     *
     * @param calendar
     * @return
     */
    public static long[] getDayTimeS_E(Calendar calendar) {
        return getDayTimeS_E(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));


    }


    /**
     * 是否是过去
     *
     * @param mCalendar
     * @return
     */
    public static boolean getTimePast(Calendar mCalendar) {
        return isToday(mCalendar) < 0;
    }

    /**
     * 时间段 凌晨,早上,上午,中午,下午,傍晚,晚上,深夜
     *
     * @return
     */
    public static String getTimeSegment(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        // TODO: 18-5-7 待定 
        return "";


    }

    /**
     * 返回 毫秒数
     *
     * @param time
     * @return
     */
    public static long getTimeMillis(long time) {
        String timeStr = String.valueOf(time);
        if (timeStr.length() <= 10) {
            return time * S_TO_MS;
        } else {
            return time;
        }


    }

    /**
     * 返回 秒数
     *
     * @param time
     * @return
     */
    public static long getTimeSeconds(long time) {
        String timeStr = String.valueOf(time);
        if (timeStr.length() <= 10) {
            return time;
        } else {
            return time / S_TO_MS;
        }

    }


    /**
     * 判断是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(long date1, long date2) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTimeInMillis(date1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTimeInMillis(date2);

        return isSameDay(calDateA, calDateB);
    }

    private static boolean isSameDay(Calendar calDateA, Calendar calDateB) {
        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR) && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH) && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * date2比date1多的天数  <p>date2>date1;<p/>
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(long date1, long date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)    //不同年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {  //同一年
            return day2 - day1;
        }
    }

    /**
     * 时间天数差
     * <p>date2 > date1正数<p>date2 < date1负数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int diffDay(long date1, long date2) {
        if (date1 > date2) {
            return -differentDays(date2, date1);
        } else {
            return differentDays(date1, date2);
        }

    }


    /**
     * date2比date1多的月数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentMonths(long date1, long date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(date2);
        int month1 = cal1.get(Calendar.MONTH);
        int month2 = cal2.get(Calendar.MONTH);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);

        return (year2 - year1) * 12 + month2 - month1;
    }

    /**
     * 当天任意时间转当天0点时间
     *
     * @param time 毫秒
     * @return 毫秒
     */
    public static long getDay0ClockTimeMillis(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();


    }

    public static boolean isNextDay(long today, long nextDay) {

        return getDay0ClockTimeMillis(nextDay) > getDay0ClockTimeMillis(today);
    }

    /**
     * 前小 后大
     *
     * @param timeMillis_1
     * @param timeMillis_2
     * @param isUp         是否向上取整
     * @return
     */
    public static int differentSe(long timeMillis_1, long timeMillis_2, boolean isUp) {
        if (isUp) {
            return (int) Math.ceil((timeMillis_2 - timeMillis_1) / (1.0 * M_TO_MS));
        } else {
            return (int) Math.floor((timeMillis_2 - timeMillis_1) / (1.0 * M_TO_MS));
        }


    }


    /**
     * 获取当前时间点的 下一个整点时间
     *
     * @return
     */
    public static Calendar getNowTimeNext0M(long time) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.add(Calendar.HOUR, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;


    }
}
