package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "users")
@Data
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
