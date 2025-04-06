package org.se06203.besgtn.utils;

import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Constants {
    public enum AuthorityEnum {
        USER, ADMIN, BOT
    }

    public enum RepeatType {
        DAILY, WEEKLY, MONTHLY, YEARLY, NONE
    }

    public enum Gender {
        MALE, FEMALE,
    }

    public enum RepeatUpdateType {
        ALL, THIS, CONTINUE
    }

    public enum TypeEpic {
        WORK, STUDY
    }

    public enum TypeTask {
        TODO, IN_PROGRESS, DONE
    }

    public enum Status {
        COMPLETE, INCOMPLETE, ERROR
    }

    public enum StatusNotification {
        PENDING,SENT,FAILED
    }
    @Getter
    public enum InternalHttpHeader {
        CORRELATION_ID("x-correlation-id"),
        USER_ID("x-user-id"),
        AUTHORIZATION("Authorization"),
        LANGUAGE("Accept-Language"),
        START_TIME("x-start-time"),
        API_KEY("api-key");

        private final String value;

        InternalHttpHeader(String value) {
            this.value = value;
        }

        public static Set<String> getInternalHeaders() {
            return EnumSet.allOf(InternalHttpHeader.class).stream()
                    .map(InternalHttpHeader::getValue)
                    .collect(Collectors.toSet());
        }
    }

    public static final String PHONE_REGEX = "^[0-9]\\d{9,14}$";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final int DURATION_PER_SLOT = 30;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    public static final int LIMIT_RECORDS = 10000;

    public static final String K_OTP_TRANS_RESULT = "otp-trans-result:%s";
    public static final String K_OTP_TRANS = "otp-trans:%s";
    public static final int OTP_LENGTH = 6;
    public static final int EXPIRY_DURATION_MINUTES = 5;
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    public static final Pattern DATE_PATTERN_VN = Pattern.compile("📅 Lịch trình ngày (\\d{2}/\\d{2}/\\d{4})");
    public static final Pattern DATE_PATTERN_EN = Pattern.compile("Schedule for (\\d{2}/\\d{2}/\\d{4})");
    public static final Pattern SCHEDULE_PATTERN = Pattern.compile("(\\d{2}:\\d{2}) - (\\d{2}:\\d{2}) \\| (.*?) \\| (.*)");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
}
