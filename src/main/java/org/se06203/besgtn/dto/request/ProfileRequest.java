package org.se06203.besgtn.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.se06203.besgtn.utils.Constants;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
    private String fullName;
    private String phone;
    @Schema(example = "yyyy-MM-dd")
    private String birthday;
    @Schema(example = "<MALE,FEMALE>")
    private Constants.Gender gender;
    private String hobbies;
    private String Occupation;
    private String password;
}
