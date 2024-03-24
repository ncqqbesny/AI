package com.hdpt.device.utils;

import com.hdpt.device.type.NumberConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class DateUtils {
    public static final String ORISDF = "yyyyMMdd";
    public static final String DATE_YEARMM = "yyyyMM";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String ORI_DATE_F = "yyyy-MM-dd";
    public static final String ORI_DATE_Y = "yyyy";
    private static Map<String, DateFormat> formatters = new HashMap<String, DateFormat>();

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss sss";

    public static final String SECOND_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DAY_PATTERN = "yyyy-MM-dd";

    public static final long DAY_SECONDS = (NumberConst.TWENTY_FOUR.intValue() * NumberConst.SIXTY.intValue() * NumberConst.SIXTY.intValue());

    public static final long DAY_MILLI_SECONDS = DAY_SECONDS * NumberConst.THOUSAND.intValue();
    private final static Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 判断两个时间相关多少天，多少时小时，多少分钟，多少秒。
     * @param str1
     * @param str2
     * @param dataFormat
     * @return
     * @throws java.text.ParseException
     */
    public static long[] getDistanceTimes(String str1, String str2, String dataFormat)
            throws java.text.ParseException {
        DateFormat df = new SimpleDateFormat(dataFormat);
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }

    /**
     * 时间截转为字符串
     * @param timestamp
     * @return
     */
    public static Date convertToDateTime(String timestamp) {
        // 创建一个Date对象，将时间戳作为参数传递给构造函数
        Long dateLong=ConvertUtils.toLong(timestamp);
        Date date = new Date(dateLong);
        return date;
    }

    /**
     * 时间截转为字符串
     * @param timestamp
     * @param dateFormate
     * @return
     */
    public static String convertToDateTimeString(long timestamp,final String dateFormate) {
        // 创建一个Date对象，将时间戳作为参数传递给构造函数
        Date date = new Date(timestamp);
        // 创建SimpleDateFormat对象，定义日期时间的格式
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);

        // 使用SimpleDateFormat对象的format()方法将Date对象格式化为字符串
        String dateTimeString = sdf.format(date);
        return dateTimeString;
    }
    public static String getYYYYMMDDHHMMSSDate(final Date currentDate) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(currentDate);
    }

    public static String getYYYYMMDDDate(final Date currentDate) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(currentDate);
    }

    public static String getYYYYDate(final Date currentDate) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(currentDate);
    }

    public static String getWeekByDate(final Date date) {
        String yearWeek = "";
        final String year = getYYYYDate(date);
        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        yearWeek = year + calendar.get(3);
        return yearWeek;
    }

    public static String getFormateDate(final Date currentDate, final String dateFormate) {
        final SimpleDateFormat newSdf = new SimpleDateFormat(dateFormate);
        return newSdf.format(currentDate);
    }

    public static String getYYYYMMDate(final Date currentDate) {
        final SimpleDateFormat newSdf = new SimpleDateFormat("yyyyMM");
        return newSdf.format(currentDate);
    }

    public static String getDayFormatDate(final Date currentDate) {
        final SimpleDateFormat newSdf = new SimpleDateFormat("yyyyMMdd");
        return newSdf.format(currentDate);
    }

    public static Date getDateByString(final String dateStr, final String dateFormate) {
        final SimpleDateFormat newSdf = new SimpleDateFormat(dateFormate);
        try {
            return newSdf.parse(dateStr);
        } catch (Exception e) {
            DateUtils.LOG.error(e.getMessage());
            return null;
        }
    }

    public static Date getNowDate() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date currentTime = getNowDateTime();
        final String dateString = sdf.format(currentTime);
        final ParsePosition pos = new ParsePosition(0);
        final Date currentTime_2 = sdf.parse(dateString, pos);
        return currentTime_2;
    }

    public static Date getNowDateTime() {
        final Calendar c = Calendar.getInstance();
        final Date currentTime = c.getTime();
        return currentTime;
    }

    public static String getNowDate(final String dateFormate) {
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
        return sdf.format(getNowDate());
    }
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟"+sec+"秒";
    }
    public static String getNowTime(final boolean showTime) {
        String formatStr = "yyyy-MM-dd";
        if (showTime) {
            formatStr = "yyyy-MM-dd HH:mm:ss";
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(getNowDateTime());
    }

    public static String getDateStr(final Date date, final String dateFormate) {
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
        return sdf.format(date);
    }

    public static Date getYestoday() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar c = Calendar.getInstance();
        c.add(5, -1);
        final Date currentTime = c.getTime();
        final String dateString = sdf.format(currentTime);
        final ParsePosition pos = new ParsePosition(0);
        final Date currentTime_2 = sdf.parse(dateString, pos);
        return currentTime_2;
    }

    public static String getYestoday(final String dateFormate) {
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
        return sdf.format(getYestoday());
    }

    public static Date getTomorrow() {
        return addDay(1);
    }

    public static Date addDay(final int day) {
        final Calendar c = Calendar.getInstance();
        c.add(5, day);
        final Date currentTime = c.getTime();
        return currentTime;
    }

    public static Date getAddDay(final Date date, final int add) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(5, add);
        final Date currentTime = c.getTime();
        return currentTime;
    }

    public static Date getFirstDayOfWeek(final int weekIndex) {
        final Calendar c = Calendar.getInstance();
        c.add(4, weekIndex);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        c.set(7, 1);
        return c.getTime();
    }

    public static String getFirstDayOfWeek(final int weekIndex, final String dateFormate) {
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
        return sdf.format(getFirstDayOfWeek(weekIndex));
    }

    public static Date getLastDayOfWeek(final int weekIndex) {
        final Calendar c = Calendar.getInstance();
        c.add(4, weekIndex);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        c.set(7, 7);
        return c.getTime();
    }

    public static String getLastDayOfWeek(final int weekIndex, final String dateFormate) {
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
        return sdf.format(getLastDayOfWeek(weekIndex));
    }

    public static Date getFirstDayOfMonth(final int monthIndex) {
        final Calendar c = Calendar.getInstance();
        c.add(2, monthIndex);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        c.set(5, 1);
        return c.getTime();
    }

    public static String getFirstDayOfMonth(final int weekIndex, final String dateFormate) {
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
        return sdf.format(getFirstDayOfMonth(weekIndex));
    }

    public static Date getLastDayOfMonth(final int monthIndex) {
        final Calendar c = Calendar.getInstance();
        c.add(2, monthIndex + 1);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        c.set(5, 0);
        return c.getTime();
    }

    public static Date getLastDayOfMonth(final int year, final int monthIndex) {
        final Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, monthIndex - 1);
        final int lastDay = cal.getActualMaximum(5);
        cal.set(5, lastDay);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getLastDayOfMonth(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(5, cal.getActualMaximum(5));
        cal.set(11, cal.getActualMaximum(11));
        cal.set(12, cal.getActualMaximum(12));
        cal.set(13, cal.getActualMaximum(13));
        cal.set(14, cal.getActualMaximum(14));
        return cal.getTime();
    }

    public static Date getCurrYearLast() {
        final Calendar currCal = Calendar.getInstance();
        final int currentYear = currCal.get(1);
        return getYearLast(currentYear);
    }

    public static Date getYearLast(final int year) {
        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.roll(6, -1);
        final Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    public static Date getYearFirst(final int year) {
        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.roll(6, 0);
        final Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    public static String getLastDayOfMonth(final int weekIndex, final String dateFormate) {
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
        return sdf.format(getLastDayOfMonth(weekIndex));
    }

    public static boolean isSameDay(final Date day1, final Date day2) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String ds1 = sdf.format(day1);
        final String ds2 = sdf.format(day2);
        return ds1.equals(ds2);
    }

    public static Date getDayOfNYear(final int yearadd) {
        final Calendar c = Calendar.getInstance();
        c.add(1, yearadd);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return c.getTime();
    }

    public static int getDayOfNYear(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(1);
    }

    public static int getMonthOfYear(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(2) + 1;
    }

    public static Date getNowStartDate() {
        final Calendar c = Calendar.getInstance();
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return c.getTime();
    }

    public static Date getNowEndDate() {
        final Calendar c = Calendar.getInstance();
        c.set(11, 23);
        c.set(12, 59);
        c.set(13, 59);
        c.set(14, 999);
        return c.getTime();
    }

    public static Date getStartDate(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        return c.getTime();
    }

    public static Date getEndDate(final Date date) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(11, 23);
        c.set(12, 59);
        c.set(13, 59);
        c.set(14, 999);
        return c.getTime();
    }

    public static Date getFirstDayOfMonth(final Date date, final int monthIndex) {
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, monthIndex);
        c.set(11, 0);
        c.set(12, 0);
        c.set(13, 0);
        c.set(14, 0);
        c.set(5, 1);
        return c.getTime();
    }

    public static String getFisrtDayOfMonth(final int year, final int month) {
        final Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        final int firstDay = cal.getActualMinimum(5);
        cal.set(5, firstDay);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    public static String getDateFormat(final String str) {
        boolean year = false;
        final Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (pattern.matcher(str.substring(0, 4)).matches()) {
            year = true;
        }
        final StringBuilder sb = new StringBuilder();
        int index = 0;
        if (!year) {
            if (str.contains("\u6708") || str.contains("-") || str.contains("/")) {
                if (Character.isDigit(str.charAt(0))) {
                    index = 1;
                }
            } else {
                index = 3;
            }
        }
        for (int i = 0; i < str.length(); ++i) {
            final char chr = str.charAt(i);
            if (Character.isDigit(chr)) {
                if (index == 0) {
                    sb.append("y");
                }
                if (index == 1) {
                    sb.append("M");
                }
                if (index == 2) {
                    sb.append("d");
                }
                if (index == 3) {
                    sb.append("H");
                }
                if (index == 4) {
                    sb.append("m");
                }
                if (index == 5) {
                    sb.append("s");
                }
                if (index == 6) {
                    sb.append("S");
                }
            } else {
                if (i > 0) {
                    final char lastChar = str.charAt(i - 1);
                    if (Character.isDigit(lastChar)) {
                        ++index;
                    }
                }
                sb.append(chr);
            }
        }
        return sb.toString();
    }

    public static int[] years(final String startTime, final String endTime) {
        final String startStr = startTime.substring(0, 4);
        final String endStr = endTime.substring(0, 4);
        final int startYear = Integer.valueOf(startStr);
        final int endYear = Integer.valueOf(endStr);
        if (endYear > startYear) {
            final int xc = endYear - startYear;
            final int[] years = new int[xc + 1];
            for (int i = 0; i <= xc; ++i) {
                years[i] = startYear + i;
            }
            return years;
        }
        if (endYear == startYear && endYear - startYear == 0) {
            final int[] years = {startYear};
            return years;
        }
        return null;
    }

    public static String[] startAndEndTime(final String year) {
        final String startTime = year + "-01-01 00:00:00";
        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, Integer.valueOf(year));
        calendar.roll(6, -1);
        final Date time = calendar.getTime();
        final String endTime = getYYYYMMDDDate(time);
        final String[] sAe = {startTime, endTime + " 23:59:59"};
        return sAe;
    }

    public static int getDays(final Date endTime, final Date startTime) {
        final long day = (endTime.getTime() - startTime.getTime()) / 86400000L;
        return ConvertUtils.toInt((Object) day);
    }

    public static long getLongDays(final Date endTime, final Date startTime) {
        final long day = endTime.getTime() - startTime.getTime();
        return day;
    }

    public static List<String> redisKey(final String compCode, final List<String> typeCodes, final boolean onlyYear, final String startTime, final String endTime, final String timeField) {
        final List<String> keys = new ArrayList<String>();
        final List<String> yearAndMonths = yearAndMonths(onlyYear, startTime, endTime);
        final int size = yearAndMonths.size();
        final int typeCodesSize = typeCodes.size();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < typeCodesSize; ++j) {
                final String key = "archives_classification_count_" + compCode + "_" + yearAndMonths.get(i) + "_" + typeCodes.get(j) + "_" + timeField;
                keys.add(key);
            }
        }
        return keys;
    }

    public static List<String> yearAndMonths(final boolean onlyYear, final String startTime, final String endTime) {
        if (!onlyYear) {
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = new SimpleDateFormat("yyyy-MM").parse(startTime);
                d2 = new SimpleDateFormat("yyyy-MM").parse(endTime);
            } catch (ParseException e) {
                if (DateUtils.LOG.isErrorEnabled()) {
                    DateUtils.LOG.error(("\u6839\u636e\u8d77\u59cb\u65f6\u95f4\uff0c\u751f\u6210\u4e2d\u95f4\u6240\u6709\u7684\u5e74\u6708\u65b9\u6cd5\u8f6c\u6362\u5f02\u5e38\uff1a" + e.getMessage()));
                }
            }
            final List<String> keys = new ArrayList<String>();
            final Calendar dd = Calendar.getInstance();
            dd.setTime(d1);
            while (dd.getTime().before(d2)) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                final String key;
                final String str = key = sdf.format(dd.getTime());
                keys.add(key);
                dd.add(2, 1);
            }
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String key;
            final String str = key = sdf.format(dd.getTime());
            keys.add(key);
            return keys;
        }
        if (onlyYear) {
            final List<String> keys2 = new ArrayList<String>();
            final int[] year = years(startTime, endTime);
            final int length = year.length;
            final List<String[]> all = new ArrayList<String[]>();
            for (final int y : year) {
                final String[] strings = startAndEndTime(String.valueOf(y));
                all.add(strings);
            }
            for (int size = all.size(), j = 0; j < size; ++j) {
                final String[] strings = all.get(j);
                final String start = strings[0];
                final String end = strings[1];
                Date d3 = null;
                Date d4 = null;
                try {
                    d3 = new SimpleDateFormat("yyyy-MM").parse(start);
                    d4 = new SimpleDateFormat("yyyy-MM").parse(end);
                } catch (ParseException e2) {
                    if (DateUtils.LOG.isErrorEnabled()) {
                        DateUtils.LOG.error((String) ("\u751f\u6210\u4e2d\u95f4\u6240\u6709\u7684\u5e74\u6708\u65f6\u95f4:\u65f6\u95f4\u8f6c\u6362\u5931\u8d25:" + e2.getMessage()));
                    }
                }
                final Calendar dd2 = Calendar.getInstance();
                dd2.setTime(d3);
                while (dd2.getTime().before(d4)) {
                    final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
                    final String key2;
                    final String str2 = key2 = sdf2.format(dd2.getTime());
                    keys2.add(key2);
                    dd2.add(2, 1);
                }
                final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
                String key2;
                final String str2 = key2 = sdf2.format(dd2.getTime());
                keys2.add(key2);
            }
            return keys2;
        }
        return null;
    }

    public static String formatDuring(final long mss) {
        final long days = mss / 86400000L;
        final long hours = mss % 86400000L / 3600000L;
        final long minutes = mss % 3600000L / 60000L;
        return days + "\u5929 " + hours + "\u5c0f\u65f6 " + minutes + "\u5206\u949f ";
    }

    public static String getPeriodByDate(final Date date) {
        String period = "";
        String quarter = " ";
        final String year = getFormateDate(date, "yyyy");
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int m = cal.get(2) + 1;
        if (m >= 1 && m <= 3) {
            quarter = "1";
        }
        if (m >= 4 && m <= 6) {
            quarter = "2";
        }
        if (m >= 7 && m <= 9) {
            quarter = "3";
        }
        if (m >= 10 && m <= 12) {
            quarter = "4";
        }
        period = year + quarter;
        return period;
    }


    public static Date str2ShortDate(String date) {
        return str2Date("yyyy-MM-dd", date);
    }

    public static Date str2LongDate(String date) {
        return str2Date("yyyy-MM-dd HH:mm:ss", date);
    }

    public static String shortDate2Str(Date date) {
        return date2Str("yyyy-MM-dd", date);
    }

    public static String longDate2Str(Date date) {
        return date2Str("yyyy-MM-dd HH:mm:ss", date);
    }

    public static Date str2Date(String pattern, String date) {
        if (!StringUtil.isEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                return sdf.parse(date);
            } catch (ParseException e) {
                if (LOG.isErrorEnabled())
                    LOG.error(e.getMessage(), e);
                return null;
            }
        }
        return null;
    }

    public static Date str2Date(String dateStr) {
        Date date = str2Date("yyyy-MM-dd HH:mm:ss", dateStr);
        if (date == null)
            date = str2Date("yyyy-MM-dd", dateStr);
        if (date == null)
            date = str2Date("yyyy-MM-dd HH:mm:ss sss", dateStr);
        return date;
    }
    public static String date2Str(String pattern, Date date) {
        if (!StringUtil.isEmpty(pattern) && date != null)
            try {
                return (new SimpleDateFormat(pattern)).format(date);
            } catch (Exception e) {
                if (LOG.isErrorEnabled())
                    LOG.error(e.getMessage(), e);
                return null;
            }
        return null;
    }

    public static String date2Str(Date date) {
        if (date != null)
            return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
        return null;
    }

    public static TimeZone getSysTimeZone() {
        return TimeZone.getDefault();
    }




    private static Date obj2Date(Object srcDate) {
        if (srcDate == null)
            return null;
        if (Date.class.isAssignableFrom(Date.class))
            return (Date) srcDate;
        if (srcDate instanceof String)
            return str2Date("yyyy-MM-dd HH:mm:ss sss", (String) srcDate);
        return null;
    }

    public static String formate(String pattern, Long millseconds) {
        if (millseconds == null)
            return null;
        return getDateFormatter(pattern).format(new Date(millseconds.longValue()));
    }

    public static DateFormat getDateFormatter(String pattern) {
        if (StringUtil.isEmpty(pattern))
            return formatters.get("yyyy-MM-dd HH:mm:ss sss");
        DateFormat df = formatters.get(pattern);
        if (null != df)
            return df;
        synchronized (formatters) {
            df = formatters.get(pattern);
            if (null == df) {
                df = new SimpleDateFormat(pattern);
                formatters.put(pattern, df);
            }
        }
        return df;
    }

    public static long getWorkingMills(Date endDateTime, Date startDateTime) {
        Date startDate = startDateTime;
        Date endDate = endDateTime;
        if (startDateTime.getTime() > endDateTime.getTime()) {
            startDate = endDateTime;
            endDate = startDateTime;
        }
        long result = 0L;
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        boolean startTimeIsWorkDay = (startCal.get(7) == 7 || startCal.get(7) == 1);
        boolean endTimeIsWorkDay = (endCal.get(7) == 7 || endCal.get(7) == 1);
        int workDay = getWorkingDaysBetweenTwoDates(endDate, startDate);
        if (startTimeIsWorkDay && endTimeIsWorkDay)
            return (workDay * NumberConst.TWENTY_FOUR.intValue() * NumberConst.SIXTY.intValue() * NumberConst.SIXTY.intValue());
        if (startTimeIsWorkDay && !endTimeIsWorkDay) {
            result = workDay * DAY_SECONDS;
            String endDateString = formate("yyyy-MM-dd", Long.valueOf(endDate.getTime()));
            Date temp = str2Date("yyyy-MM-dd", endDateString);
            result += (endDate.getTime() - temp.getTime()) / NumberConst.THOUSAND.intValue();
        } else if (!startTimeIsWorkDay && endTimeIsWorkDay) {
            result = workDay * DAY_SECONDS;
            String startDateString = formate("yyyy-MM-dd", Long.valueOf(startDate.getTime() + DAY_MILLI_SECONDS));
            Date temp = str2Date("yyyy-MM-dd", startDateString);
            result += (temp.getTime() - startDate.getTime()) / NumberConst.THOUSAND.intValue();
        } else if (workDay > 0) {
            result = (workDay * NumberConst.TWENTY_FOUR.intValue() * NumberConst.SIXTY.intValue() * NumberConst.SIXTY.intValue());
            String startDateString = formate("yyyy-MM-dd", Long.valueOf(startDate.getTime() + DAY_MILLI_SECONDS));
            Date temp = str2Date("yyyy-MM-dd", startDateString);
            result += (temp.getTime() - startDate.getTime()) / NumberConst.THOUSAND.intValue();
            String endDateString = formate("yyyy-MM-dd", Long.valueOf(endDate.getTime()));
            temp = str2Date("yyyy-MM-dd", endDateString);
            result += (endDate.getTime() - temp.getTime()) / NumberConst.THOUSAND.intValue();
        } else {
            result = (endDate.getTime() - startDate.getTime()) / NumberConst.THOUSAND.intValue();
        }
        return result;
    }

    public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int workDays = 0;
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis())
            return 0;
        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }
        while (true) {
            startCal.add(5, 1);
            if (startCal.get(7) != 7 && startCal
                    .get(7) != 1)
                workDays++;
            if (startCal.getTimeInMillis() >= endCal.getTimeInMillis())
                return workDays;
        }
    }
}

