package org.se06203.sgtmbackend.persistence.entity;


import lombok.*;

import org.se06203.sgtmbackend.ultis.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection="user_role")

public class UserRoles {
    @Id
    private String id;

    private String userId;

    private Constants.role role;
}
