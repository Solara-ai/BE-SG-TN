package org.se06203.besgtn.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // common error
    SUCCESS("00"),
    FAILED("01", HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * Server side error, prefix with "S"
     */
    // authen error - prefix "A"
    ACCESS_DENIED("SA01", HttpStatus.FORBIDDEN),
    OTP_INVALID("SA02", HttpStatus.BAD_REQUEST),
    OTP_EXPIRED("SA03", HttpStatus.UNAUTHORIZED),
    INVALID_GOOGLE_TOKEN("SA04", HttpStatus.UNAUTHORIZED),
    INVALID_GOOGLE_IDENTITY("SA05", HttpStatus.INTERNAL_SERVER_ERROR),
    APPLE_KEY_ID_NOT_FOUND("SA06", HttpStatus.BAD_REQUEST),
    APPLE_KEY_VERIFICATION_FAILED("SA07", HttpStatus.UNAUTHORIZED),
    APPLE_TOKEN_EXPIRED("SA08", HttpStatus.UNAUTHORIZED),
    APPLE_TOKEN_PARSE_ERROR("SA09", HttpStatus.INTERNAL_SERVER_ERROR),
    LOCKED_ACCOUNT("SA10", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("SA11", HttpStatus.UNAUTHORIZED),

    // business error - prefix "B"
    NOT_FOUND("SB01", HttpStatus.NOT_FOUND),
    ALREADY_EXIST("SB02", HttpStatus.CONFLICT),
    INVALID("SB03", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_SUPPORTED("SB04", HttpStatus.BAD_REQUEST),

    // Validation - prefix "V"
    INVALID_INPUT("SV01", HttpStatus.BAD_REQUEST),
    MISSING_PARAM("SV02", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER("SV03", HttpStatus.BAD_REQUEST),

    // Follow BA confluence documents: https://confluence.savvycom.vn/display/FCMDV/1.+Business+rule
    // Common
    MISSING_PARAMETERS("MSG01", HttpStatus.BAD_REQUEST),

    // by API error:
    // authen:
    WRONG_PW_ATTEMPT_1("MSG1201", HttpStatus.CONFLICT),
    WRONG_PW_ATTEMPT_2("MSG1202", HttpStatus.CONFLICT),
    WRONG_PW_ATTEMPT_3("MSG1203", HttpStatus.CONFLICT),
    WRONG_PW_ATTEMPT_4("MSG1204", HttpStatus.CONFLICT),
    WRONG_PW_ATTEMPT_5("MSG1205", HttpStatus.CONFLICT),
    INACTIVE_USER("MSG1206", HttpStatus.BAD_REQUEST),

    // signup:

    OTP_CODE_INVALID_OR_EXPIRED("MSG1101", HttpStatus.BAD_REQUEST),
    PASSCODE_NOT_MATCH("MSG1102", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED("MSG1103", HttpStatus.CONFLICT),
    INVALID_EMAIL("MSG1104", HttpStatus.BAD_REQUEST),
    REQUIRED_FIELD_NOT_NULL("MSG1105", HttpStatus.BAD_REQUEST),
    PHONE_EXISTED("MSG1106", HttpStatus.CONFLICT),

    // reset password:
    RESET_PASSWORD_OTP_CODE("MSG1301", HttpStatus.BAD_REQUEST),

    // appointment
    MAX_BOOKING_APPOINTMENT("MSG9999", HttpStatus.BAD_REQUEST),
    MAX_PEOPLE_IN_SLOT("MSG9998", HttpStatus.BAD_REQUEST),
    APPOINTMENT_EXISTED("MSG9997", HttpStatus.BAD_REQUEST),
    WORK_HOURS_NOT_FOUND("MSG9996", HttpStatus.BAD_REQUEST),
    DAY_OFF("MSG8999", HttpStatus.BAD_REQUEST),


    WALK_IN_USER_TIME_SLOT_NOT_AVAILABLE("MSG8000", HttpStatus.BAD_REQUEST),

    //voucher
    NOT_ENOUGH_POINT("MSG9995", HttpStatus.BAD_REQUEST),
    VOUCHER_REDEEMED("MSG9994", HttpStatus.BAD_REQUEST),
    VOUCHER_EXPIRED("MSG9993", HttpStatus.BAD_REQUEST),
    VOUCHER_OUT_OF_STOCK("MSG9992", HttpStatus.BAD_REQUEST),
    VOUCHER_USED("MSG9991", HttpStatus.BAD_REQUEST),
    NOT_REDEEM_VOUCHER("MSG9990", HttpStatus.BAD_REQUEST),
    SUBSCRIPTION_VOUCHER_CANT_BE_REDEEMED("MSG8998", HttpStatus.BAD_REQUEST),
    VOUCHER_REDEEMED_BY_ANOTHER("MSG8997", HttpStatus.BAD_REQUEST),
    FREE_VOUCHER_CANT_BE_REDEEMED("MSG8888", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_VALUE("MSG8868", HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_BELONG_TO_CLINIC("MSG8668", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER_MINVALUE("MSG8669", HttpStatus.BAD_REQUEST),
    ERR_VOUCHER_NOT_YET_ACTIVE("MSG8667", HttpStatus.BAD_REQUEST),

    //Subscription
    PACKAGE_HAS_BEEN_SUBSCRIBED("MSG8996", HttpStatus.BAD_REQUEST),

    // refresh token
    INVALID_REFRESH_TOKEN("MSG1401", HttpStatus.BAD_REQUEST),

    // announcement
    CANT_UPDATE_ANNOUNCEMENT("MSG1501", HttpStatus.BAD_REQUEST),

//    Records have been exceeded when export
    WARNING_LIMIT_RECORD("MSG1601", HttpStatus.BAD_REQUEST),
    //Invalid bill
    INVALID_BILL("MSG1701", HttpStatus.BAD_REQUEST),

    //clinic error
    APPOINTMENT_NOT_BELONG_TO_CLINIC("MSG3700", HttpStatus.BAD_REQUEST);


    public static final String SUCCESS_MESSAGE = "Success";
    public static final String FAILURE_MESSAGE = "Failed";

    ErrorCode(String code) {
        this.code = code;
        this.status = HttpStatus.OK;
    }

    ErrorCode(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    private final String code;
    private final HttpStatus status;
}
