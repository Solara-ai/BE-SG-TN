package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "recipient")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@TypeAlias("recipient")
public class NotificationRecipient {
    @MongoId(FieldType.STRING)
    private String id = new ObjectId().toString();
    private String notificationId; // Tham chiếu đến Notification
    private String userId; // User nhận thông báo

}
