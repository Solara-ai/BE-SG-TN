package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "users")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    private String id;
    private String userName;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private List<String> roles;
}
