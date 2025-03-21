package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Document(collection = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("schedules")
public class Schedules {
    @Id
    private String id;
    private String userId;
    private String name;
    private String description;

    @Field(targetType = FieldType.STRING)
    private String startTime; // Lưu HH:mm:ss dưới dạng String

    @Field(targetType = FieldType.STRING)
    private String endTime; // Lưu HH:mm:ss dưới dạng String

    @Field(targetType = FieldType.STRING)
    private String date; // Lưu yyyy-MM-dd dưới dạng String

    @Enumerated(EnumType.STRING)
    @Field(targetType = FieldType.STRING)
    private Constants.RepeatType repeat;

    @Field(targetType = FieldType.STRING)
    private String repeatEndDate;
    private boolean remindMe;
    private String categoryId;
    private String parentScheduleId;
    private List<ChildSchedule> childSchedules;
}