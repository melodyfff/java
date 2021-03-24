package com.xinchen.java.util.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * 日期处理工具类
 *
 * @author xinchen
 * @version 1.0
 * @date 10 /12/2020 14:31
 */
public final class DateMan {
    private static final ZoneId zone = ZoneId.systemDefault();


    /**
     * Time str 2 date date.
     *
     * @param timeStr the time str
     * @return the date
     */
    static Date timeStr2Date(String timeStr){
        DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(timeStr, timeDtf);
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }


    /**
     * Days format between list.
     *
     * 返回一段时间内所经历的每一天日期
     *
     * @param start the start
     * @param end   the end
     * @return the list
     */
    List<String> daysFormatBetween(long start,long end){
        List<String> list = new ArrayList<>();
        final LocalDateTime localDateTimeStart = LocalDateTime.ofInstant(new Date(start).toInstant(), zone);
        final LocalDateTime localDateTimeEnd = LocalDateTime.ofInstant(new Date(end).toInstant(), zone);

        // 判断相差多少天
        final long distance = ChronoUnit.DAYS.between(localDateTimeStart, localDateTimeEnd);

        if (distance <1){
            return list;
        }

        // 从开始时间累加一天，返回指定格式的日期字符串
        // 这里默认返回的是 yyyyMMdd
        Stream.iterate(localDateTimeStart, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> list.add(f.format(DateTimeFormatter.BASIC_ISO_DATE)));

        return list;
    }
}
