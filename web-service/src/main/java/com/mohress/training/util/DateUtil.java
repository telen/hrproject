package com.mohress.training.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by youtao.wan on 2017/8/10.
 */
@Slf4j
public class DateUtil {

    public static Date addYear(Date date, int years){
        return addTime(date, Calendar.YEAR, years);
    }

    public static Date addMonth(Date date, int months) {
        return addTime(date, Calendar.MONTH, months);
    }

    public static Date addDay(Date date, int days){
        return addTime(date, Calendar.DAY_OF_YEAR, days);
    }

    public static Date addHour(Date date, int hours){
        return addTime(date, Calendar.HOUR, hours);
    }

    public static Date addMinute(Date date, int minutes){
        return addTime(date, Calendar.MINUTE, minutes);
    }

    public static Date addSecond(Date date, int seconds){
        return addTime(date, Calendar.SECOND, seconds);
    }

    private static Date addTime(Date date, int timeClass, int i){
        if (i == 0){
            return date;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(timeClass, i);
        return c.getTime();
    }

    public static boolean isBeforeNow(Date date){
        return isBeforeDate(date, new Date());
    }

    public static boolean isAfterNow(Date date){
        return !isBeforeNow(date);
    }

    public static boolean isBeforeDate(Date d1, Date d2){
        return d1.compareTo(d2) == -1;
    }

    public static boolean isAfterDate(Date d1, Date d2){
        return !isBeforeDate(d1, d2);
    }

    public static Date parse(String source) {
        String pattern = "";
        switch(source.length()) {
            case 4:
                pattern = "yyyy";
            case 5:
            case 9:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
            default:
                break;
            case 6:
                pattern = "yyyyMM";
                break;
            case 7:
                pattern = "yyyy-MM";
                break;
            case 8:
                pattern = "yyyyMMdd";
                break;
            case 10:
                pattern = "yyyy-MM-dd";
                break;
            case 14:
                pattern = "yyyyMMddHHmmss";
                break;
            case 19:
                pattern = "yyyy-MM-dd HH:mm:ss";
                break;
            case 23:
                pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        }

        return parse(source, pattern);
    }

    public static Date parse(String source, String pattern){
        if (StringUtils.isEmpty(source) || StringUtils.isEmpty(pattern)){
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = formatter.parse(source);
        }catch (ParseException e){
            log.error("", e);
        }
        return date;
    }

    public static String format(Date date, String pattern){
        if (date == null || StringUtils.isEmpty(pattern)){
            return "";
        }

        return format(date, new SimpleDateFormat(pattern));
    }

    private static String format(Date date, SimpleDateFormat formatter){
        String s = "";

        try {
            s = formatter.format(date);
        }catch (Exception e){
            log.error("", e);
        }
        return s;
    }
}
