package com.didi.game.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {
    public static LocalDateTime now() {
        ZoneId bjZone = ZoneId.of("GMT+08:00");
        return LocalDateTime.now(bjZone);
    }

    /**
     * 把字符串日期及时间转成日期时间格式
     *
     * @param date 以年-月-日的格式
     * @return
     * @throws Exception
     */
    public static LocalDateTime getFormatString(String date, int hour, int minute) throws Exception {
        String[] arr = date.split("-");
        if (arr.length != 3) {
            throw new Exception("date格式错误");
        }
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);
        return LocalDateTime.of(year, month, day, hour, minute);
    }

    /**
     * 比较一个日期值是否是在两个日期值之间,包括相等
     */
    public static boolean betweenTwoDate(LocalDateTime min, LocalDateTime max, LocalDateTime compareDate) {
        if (compareDate == null) {
            return false;
        }
        if (min == null && max == null) {
            return false;
        }
        if (min == null && max != null) {
            return max.isAfter(compareDate);
        }
        if (min != null && max == null) {
            return min.isBefore(compareDate);
        }
        return (min.isBefore(compareDate) && max.isAfter(compareDate))
                || min.equals(compareDate) || max.equals(compareDate);
    }
}
