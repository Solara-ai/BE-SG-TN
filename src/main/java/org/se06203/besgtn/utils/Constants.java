package org.se06203.besgtn.utils;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Constants {
    public enum AuthorityEnum {
        USER,
        ADMIN
    }

    public enum RepeatType {
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY,
        NONE
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
}
