package org.se06203.besgtn.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
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
}
