package cn.liangjieheng.util;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocalDateTimeUtil {

    private static final String DATE_STR = "yyyy-MM-dd";

    private static final String DATE_TIME_STR = "yyyy-MM-dd HH:mm:ss";

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static Date toDate(final LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    private static Date toDate(final LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    private static LocalDateTime toLocalDateTime(final Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(final Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 日加减
     *
     * @param date
     * @param days
     * @return
     */
    public static Date plusDays(final Date date, final int days) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plusDays(days));
    }

    /**
     * 日加减
     *
     * @param date
     * @param days
     * @return
     */
    public static LocalDate plusDays(final LocalDate date, final int days) {
        return date.plusDays(days);
    }

    /**
     * 年加减
     *
     * @param localDate
     * @param years
     * @return
     */
    public static LocalDate plusYears(final LocalDate localDate, final int years) {
        return localDate.plusYears(years);
    }

    /**
     * 格式化日期 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(final Date date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_STR);
        return dateTimeFormatter.format(toLocalDateTime(date));
    }

    /**
     * 格式化日期 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDateTime(final Date date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_STR);
        return dateTimeFormatter.format(toLocalDateTime(date));
    }

    /**
     * 自定义格式化日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateByPattern(final Date date, final String pattern) {
        if (pattern == null || "".equals(pattern)) {
            return "";
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTimeFormatter.format(toLocalDateTime(date));
    }

    /**
     * 字符串转为date
     *
     * @param date yyyy-MM-dd 格式字符串
     * @return
     */
    public static Date toDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_STR);
        return toDate(LocalDate.parse(date, dateTimeFormatter));
    }

    /**
     * 计算两天之间的天数
     *
     * @param startDay
     * @param endDate
     * @return
     */
    public static long betweenDays(Date startDay, Date endDate) {
        LocalDateTime localDateTimeStart = toLocalDateTime(startDay);
        LocalDateTime localDateTimeEnd = toLocalDateTime(endDate);
        return localDateTimeStart.until(localDateTimeEnd, ChronoUnit.DAYS);
    }

    /**
     * 获取两天之间的日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> getDateListBetweenDays(LocalDate startDate, LocalDate endDate) {
        List<Date> dates = new ArrayList<>();
        Date start = toDate(startDate);
        Date end = toDate(endDate);
        long betweenDays = betweenDays(start, end);
        dates.add(start);
        for (int i = 1; i <= betweenDays; i++) {
            dates.add(plusDays(start, i));
        }
        return dates;
    }

    /**
     * 获取两天之间的日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> getDateListBetweenDays(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        long betweenDays = betweenDays(startDate, endDate);
        dates.add(startDate);
        for (int i = 1; i <= betweenDays; i++) {
            dates.add(plusDays(startDate, i));
        }
        return dates;
    }

    public static void main(String[] args) {
//        System.out.println(getDateListBetweenDays(LocalDate.now(), LocalDate.now().plusDays(2)));
    }
}
