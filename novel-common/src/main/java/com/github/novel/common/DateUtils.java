package com.github.novel.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

    private DateUtils() {
    }

    public static final String YMD_FORMAT = "yyyy-MM-dd";
    public static final String YMD_HMS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static final int SECONDS_IN_HOUR = 60 * 60;
    /**
     * 一天的秒数
     */
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    /**
     * 一天的毫秒数
     */
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    public static String getSimpleformat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(date);
        return format;
    }

    /**
     * 当前季度的开始时间
     *
     * @return
     */
    public static Date getCurrentQuarterStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        if (currentMonth >= 1 && currentMonth <= 3) {
            calendar.set(Calendar.MONTH, 0);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            calendar.set(Calendar.MONTH, 3);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            calendar.set(Calendar.MONTH, 4);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            calendar.set(Calendar.MONTH, 9);
        }

        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 当前季度的结束时间
     *
     * @return
     */
    public static Date getCurrentQuarterEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        Date now = null;
        if (currentMonth >= 1 && currentMonth <= 3) {
            calendar.set(Calendar.MONTH, 2);
            calendar.set(Calendar.DATE, 31);
        } else if (currentMonth >= 4 && currentMonth <= 6) {
            calendar.set(Calendar.MONTH, 5);
            calendar.set(Calendar.DATE, 30);
        } else if (currentMonth >= 7 && currentMonth <= 9) {
            calendar.set(Calendar.MONTH, 8);
            calendar.set(Calendar.DATE, 30);
        } else if (currentMonth >= 10 && currentMonth <= 12) {
            calendar.set(Calendar.MONTH, 11);
            calendar.set(Calendar.DATE, 31);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 将Date转为指定格式的String
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateFormatString(Date date, String pattern) {
        try {
            if (StringUtils.isBlank(pattern)) {
                pattern = YMD_HMS_FORMAT;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
            return sdf.format(date);
        } catch (Exception e) {
            LOGGER.error("getDateFormatString error", e);
        }
        return null;
    }

    public static Date getDate(String date, String pattern) {
        try {
            if (StringUtils.isBlank(pattern)) {
                pattern = YMD_HMS_FORMAT;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
            return sdf.parse(date);
        } catch (Exception e) {
            LOGGER.error("getDateFormatString error", e);
        }
        return null;
    }

    /**
     * @param daysBefore
     * @return
     */
    public static Date getDateBefore(Date date, int daysBefore) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.add(Calendar.DATE, -daysBefore);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar1.getTime();

    }

    /**
     * @param monthBefore
     * @return
     */
    public static Date getMonthBefore(Date date, int monthBefore) {
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -monthBefore);

        return calendar.getTime();
    }

    /**
     * @param monthBefore
     * @return
     */
    public static Date getMonthBefore(Integer month, int monthBefore) {
        Date dateByMonth = getDateByMonth(month);
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(dateByMonth);
        calendar.add(Calendar.MONTH, -monthBefore);

        return calendar.getTime();
    }

    /**
     * @param daysAfter
     * @return
     */
    public static Date getDateAfter(int daysAfter) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, daysAfter);
        return cal.getTime();
    }

    /**
     * @param date
     * @param daysAfter
     * @return
     */
    public static Date getDateAfter(Date date, int daysAfter) {
        long dateMillSeconds = date.getTime() + 3600L * 1000 * 24 * daysAfter;
        return new Date(dateMillSeconds);
    }

    public static Date today() {
        return toDay(new Date());
    }

    public static Date toDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 指定日期当天的最后一秒
     *
     * @param date
     * @return
     * @author lichengwu
     * @created Sep 13, 2011
     */
    public static Date toNight(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * @param month 201701
     * @return
     */
    public static Integer getFirstDayOfMonth(Integer month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date dateByMonth = getDateByMonth(month);
        return getFirstDayOfMonth(dateByMonth);

    }

    /**
     * @param month 201701
     * @return
     */
    public static Integer getLastDayOfMonth(Integer month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date dateByMonth = getDateByMonth(month);
        return getLastDayOfMonth(dateByMonth);

    }

    public static Integer getActualMaximumOfDay(Integer month) {
        Date dateByMonth = getDateByMonth(month);
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(dateByMonth);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * @return 20160101
     */
    public static Integer getLastDayOfMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //获取前月的最后一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        // calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String time = format.format(calendar.getTime());
        return Integer.valueOf(time);

    }

    public static Integer getDayOfMonth(Date date, Integer dayOfMonth) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date day = getDateOfMonth(date, dayOfMonth);
        String time = format.format(day);
        return Integer.valueOf(time);

    }

    public static Date getDateOfMonth(Date date, Integer dayOfMonth) {
        //获取前月的最后一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        // calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar.getTime();

    }

    /**
     * @return date
     */
    public static Date getFirstDateOfMonth(Date date) {
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return calendar.getTime();

    }

    /**
     * @return 20160101
     */
    public static Integer getFirstDayOfMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date firstDateOfMonth = getFirstDateOfMonth(date);
        String time = format.format(firstDateOfMonth);
        return Integer.valueOf(time);

    }

    /**
     * @return 20160101
     */
    public static Integer getDayOfMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        String time = format.format(calendar.getTime());
        return Integer.valueOf(time);

    }

    public static Date getDateByDatekey(Integer datekey) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = format.parse(String.valueOf(datekey));
            return date;
        } catch (Exception e) {
            LOGGER.warn("getDateByMonth error" + datekey, e);
            return null;
        }
    }

    /**
     * @return 20160101
     */
    public static Integer getYesterDayOfMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        String time = format.format(calendar.getTime());
        return Integer.valueOf(time);

    }

    /**
     * @return 20160101
     */
    public static Integer getYesterDayOfMonth(Integer hourAgo) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.add(Calendar.DATE, -1);
        calendar.add(Calendar.HOUR_OF_DAY, -hourAgo);// before  hour
        Date date = calendar.getTime();
        String time = format.format(date);
        return Integer.valueOf(time);

    }

    /**
     * @return 20160101
     */
    public static Integer getYesterDayOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        //获取前月的第一天
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.add(Calendar.DATE, -1);
        String time = format.format(calendar.getTime());
        return Integer.valueOf(time);

    }

    public static String getYMDDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(YMD_FORMAT);
        return format.format(date);

    }

    public static Date getDateByMonth(Integer month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        try {
            Date date = format.parse(String.valueOf(month));
            return date;
        } catch (Exception e) {
            LOGGER.warn("getDateByMonth error" + month, e);
            return null;
        }
    }

    /**
     * @return 201601
     */
    public static Integer getMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String time = format.format(date);
        return Integer.valueOf(time);

    }

    /**
     * @return 201601
     */
    public static Integer getLastMonth(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.add(Calendar.MONTH, -1);
        String time = format.format(calendar.getTime());
        return Integer.valueOf(time);

    }

    /**
     * @return 201601
     */
    public static Integer getMonthOfLastYear() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();//获取当前日期
        calendar.add(Calendar.YEAR, -1);
        calendar.add(Calendar.MONTH, -1);
        String time = format.format(calendar.getTime());
        return Integer.valueOf(time);

    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1 date1
     * @param date2 date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
        return isSameDate;
    }

    /**
     * 获取当年的第一天
     *
     * @return
     */
    public static Date getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static Date getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }

    public static Integer daysOfTwoDay(Date fDate, Date oDate) {

        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return Math.abs(day2 - day1);
    }

    /**
     * @param month1
     * @param month2
     * @return int
     */
    public static Integer daysOfTwoMonth(Integer month1, Integer month2) {

        Date startmonth = getDateByDatekey(getFirstDayOfMonth(month1));
        Date endmonth = getDateByDatekey(getLastDayOfMonth(month2));
        return daysOfTwoDay(startmonth, endmonth) + 1;

    }

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int amount = (calendar.getFirstDayOfWeek() + 1) - dayWeek;
        calendar.add(Calendar.DATE, amount);
        System.out.println(calendar.getTime());
    }

}
