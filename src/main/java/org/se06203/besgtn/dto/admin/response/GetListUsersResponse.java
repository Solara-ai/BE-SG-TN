package org.se06203.besgtn.dto.admin.response;

import lombok.*;
import org.bson.types.ObjectId;
import org.se06203.besgtn.utils.Constants;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class GetListUsersResponse {

    private String id = ObjectId.get().toString();
    private String name;
    private String email;
    private String phone;
    private Instant createdAt;
    private String password;
    private Constants.Gender gender;
    private String birthday;

}