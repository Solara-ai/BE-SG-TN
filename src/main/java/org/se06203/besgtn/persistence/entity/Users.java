package org.se06203.besgtn.persistence.entity;

import lombok.Data;

import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
    private String id;
    private String userName;
    private String fullName;
    @Field(targetType = FieldType.STRING)
    private String birthday; // yyyy-MM-dd
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String hobbies;
    private String Occupation;
    private List<String> roles;
}
