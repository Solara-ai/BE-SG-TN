package org.se06203.besgtn.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor

public enum NotificationEnum {
    SCHD_CREATE_0001("SCHD_CREATE_0001", "New Schedule Created", "USER", "SYSTEM", "SCHEDULE_CREATED"),
    SCHD_UPDATE_0002("SCHD_UPDATE_0002", "Schedule Updated", "USER", "SYSTEM", "SCHEDULE_UPDATED"),
    SCHD_CANCEL_0003("SCHD_CANCEL_0003", "Schedule Cancelled", "USER", "SYSTEM", "SCHEDULE_CANCELLED"),
    SCHD_REMINDER_0004("SCHD_REMINDER_0004", "Upcoming Schedule Reminder", "USER", "SYSTEM", "SCHEDULE_REMINDER"),
    SCHD_OVERDUE_0005("SCHD_OVERDUE_0005", "Missed Schedule Notification", "USER", "SYSTEM", "SCHEDULE_OVERDUE");


    private final String code;
    private final String title;
    private final String receiver;
    private final String sender;
    private final String actionType;

    // Static maps for quick lookup
    public static final Map<String, String> ACTION_TYPE_BY_CODE = new HashMap<>();
    public static final Map<String, String> ACTION_MESS_BY_CODE = new HashMap<>();

    // Initialize static maps
    static {
        for (NotificationEnum notification : NotificationEnum.values()) {
            ACTION_TYPE_BY_CODE.put(notification.code, notification.actionType);
            ACTION_MESS_BY_CODE.put(notification.code, notification.title);
        }
    }
}
