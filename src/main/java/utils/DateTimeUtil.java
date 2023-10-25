package utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Timezone is set to UTC by default in all methods.
 */
@UtilityClass
@Slf4j
public class DateTimeUtil {
    public static final String DATE_PATTERN_1 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String DATE_PATTERN_2 = "yyyy-MM-dd'T'HH:mm:ss.SS'Z'";
    public static final String DATE_PATTERN_3 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_PATTERN_4 = "yyyy-MM-dd'T'00:00:00.000'Z'";
    public static final String DATE_PATTERN_5 = "dd/MM/yy";
    public static final String DATE_PATTERN_8 = "MM/dd/yyyy";
    public static final String DATE_PATTERN_6 = "dd/MM/yyyy HH:mm";
    public static final String DATE_PATTERN_7 = "dd/MM/yy HH:mm";
    public static final String DATE_PATTERN_9 = "HH:mm a";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_1, Locale.ENGLISH);
    private final SimpleDateFormat simpleDateFormat_2 = new SimpleDateFormat(DATE_PATTERN_4, Locale.ENGLISH);
    private final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.ENGLISH);

    static {
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static LocalDateTime parseDate(@NonNull String date, @NonNull String format) {
        var dateTimeFormatter = ofPattern(format);
        return LocalDateTime.parse(date, dateTimeFormatter);
    }

    public static @NonNull String todayDateInFormat(@NonNull String format) {
        var dateTimeFormatter = ofPattern(format);
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    public static @NonNull String todayTimeInFormat(@NonNull String format) {
        var dateTimeFormatter = ofPattern(format);
        return LocalTime.now().format(dateTimeFormatter);
    }

    public static @NonNull String tomorrowInFormat(@NonNull String format) {
        var dateTimeFormatter = ofPattern(format);
        return LocalDate.now().plusDays(1).format(dateTimeFormatter);
    }

    public static @NonNull String currentDatePlusOneDay() {
        return simpleDateFormat_2.format(new DateTime().plusDays(1).toDate());
    }

    public static @NonNull String currentDatePlusDays(int days) {
        return LocalDate.now().plusDays(days).format(ofPattern("dd/MM/yyyy"));
    }

    public static @NonNull DateTime currentDateTime() {
        return new DateTime(DateTimeZone.UTC);
    }

    public static @NonNull LocalDateTime currentLocalDateTime() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }

    public static boolean dateIsBefore(@NonNull DateTime date1, @NonNull DateTime date2) {
        return date1.isBefore(date2.toInstant().getMillis());
    }

    public static boolean dateIsAfter(@NonNull DateTime date1, @NonNull DateTime date2) {
        return date1.isAfter(date2.toInstant().getMillis());
    }

    public static @NonNull String fromFormatToFormat(@NonNull String date, @NonNull String from, @NonNull String to) {
        var formatterFrom = ofPattern(from);
        var formatterTo = ofPattern(to);
        return LocalDateTime.parse(date.trim(), formatterFrom).format(formatterTo);
    }

}
