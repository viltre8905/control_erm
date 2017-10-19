package com.verynet.gcint.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by day on 14/04/2016.
 */
public class TimestampUtil {
    public static String getISO8601Date(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public static String getISO8601Date() {
        return getISO8601Date(new Date());
    }

    public static long hoursDifferences(String stringDate1, String stringDate2) throws ParseException {
        Date date1 = getDateFromISO8601Date(stringDate1);
        Date date2 = getDateFromISO8601Date(stringDate2);
        long diff = date2.getTime() - date1.getTime();
        long diffHours = diff / (60 * 60 * 1000) % 24;
        return diffHours;
    }

    public static Date getDateFromISO8601Date(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(date);
    }

    public static Date getDateFromTimeStamp(String time) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.parse(time);
    }

    public static Date getDayFromSimpleText(String time) {
        String dateArray[] = time.split("/");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[1]) - 1, Integer.parseInt(dateArray[0]));
        return calendar.getTime();

    }

    public static boolean areEquals(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH) + 1;
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH) + 1;
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        return year1 == year2 && month1 == month2 && day1 == day2;
    }

    public static String toShortDate(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return String.format("%s/%s/%s", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
        }
        return null;
    }
}
