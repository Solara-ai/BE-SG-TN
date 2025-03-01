package org.se06203.sgtmbackend.persistence.entity;



import lombok.*;
import org.se06203.sgtmbackend.ultis.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")

public class Users {


    @Id

    private String id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    private String gender;

    private String role;
}
