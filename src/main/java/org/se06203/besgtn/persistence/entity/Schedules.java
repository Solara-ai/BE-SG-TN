package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "schedules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("schedules")
public class Schedules {

    @MongoId(FieldType.STRING)
    @Builder.Default
    private String id = new ObjectId().toString();
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
    private List<ChildSchedule> childSchedules;
    private List<ScheduleException> exceptions;
}