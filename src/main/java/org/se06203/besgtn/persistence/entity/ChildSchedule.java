package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildSchedule {

    @MongoId(FieldType.STRING)
    @Builder.Default
    private String id = new ObjectId().toString();

    private String title;

    private String weekday;

    @Field(targetType = FieldType.STRING)
    private String date; // yyyy-MM-dd

    @Field(targetType = FieldType.STRING)
    private String startTime; // HH:mm:ss

    @Field(targetType = FieldType.STRING)
    private String endTime; // HH:mm:ss
}
