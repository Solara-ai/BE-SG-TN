package org.se06203.besgtn.dto.response;

import lombok.*;
import org.se06203.besgtn.utils.Constants;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {
    private String id;
    private String email;
    private String fullName;
    private String phone;
    private String birthday;
    private Constants.Gender gender;
    private String hobbies;
    private String occupation;
}
