package com.moshhsa.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private DateUtil() {

    }

    public static Date now() {
        return new Date();
    }

    public static Date hoursFromNow(int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now());
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static long diffInSeconds(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / 1000;
    }
}
