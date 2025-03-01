package org.se06203.sgtmbackend.config;



import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class ConvertToDate {
    public static Month convertInstantToDateMonth(Instant date) {
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));

        return zonedDateTime.getMonth();
    }

    public static Instant lastDayOfMonth() {
        Instant instant = Instant.now();
        Instant vietnamTime = instant.plus(7, ChronoUnit.HOURS);
        ZonedDateTime zonedDateTime = vietnamTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));

        int lastDay = zonedDateTime.toLocalDate().lengthOfMonth();
        return zonedDateTime.withDayOfMonth(lastDay).toInstant();
    }

    public static Instant firstDayOfMonth(){
        Instant instant = Instant.now();
        Instant vietnamTime = instant.plus(7, ChronoUnit.HOURS);
        ZonedDateTime zonedDateTime = vietnamTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));

        return zonedDateTime.withDayOfMonth(1).toInstant();
    }
}
