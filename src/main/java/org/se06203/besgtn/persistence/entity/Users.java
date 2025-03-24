package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.se06203.besgtn.utils.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "users")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("users")
public class Users {
    @Id
    @Builder.Default
    private String id = new ObjectId().toString();
    private String userName;
    private String fullName;
    @Field(targetType = FieldType.STRING)
    private String birthday; // yyyy-MM-dd
    private String email;
    private String password;
    private String phone;
    @Field(targetType = FieldType.STRING)
    @Enumerated(EnumType.STRING)
    private Constants.Gender gender;
    private String hobbies;
    private String Occupation;
    private List<String> roles;
}
