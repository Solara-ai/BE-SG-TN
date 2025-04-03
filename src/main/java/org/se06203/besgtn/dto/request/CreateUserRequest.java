package org.se06203.besgtn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.se06203.besgtn.utils.Constants;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreateUserRequest {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private List<Constants.AuthorityEnum> authority;
    private String birthday;
    private Constants.Gender gender;
    private String hobbies;
    private String occupation;
}
