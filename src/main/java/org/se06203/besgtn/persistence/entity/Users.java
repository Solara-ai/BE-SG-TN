package org.se06203.besgtn.persistence.entity;

import lombok.Data;
import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    private String id;
    private String userName;
    private String fullName;
    private String birthday;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String hobbies;
    private String Occupation;
    private List<String> roles;
}
