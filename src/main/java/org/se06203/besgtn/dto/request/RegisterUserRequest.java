package org.se06203.besgtn.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.se06203.besgtn.utils.Constants;
import org.se06203.besgtn.utils.validation.ValidEmail;
import org.se06203.besgtn.utils.validation.ValidPasscode;
import org.se06203.besgtn.utils.validation.ValidPhoneNumber;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterUserRequest {

    @NotNull
    private String fullName;

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @ValidPasscode
    private String password;

    @NotNull
    @ValidPhoneNumber
    private String phone;

    @NotNull
    private Constants.Gender gender;

    @NotNull
    private String hobbies;

    @NotNull
    private String occupation;

    @NotNull
    @Schema(example = "yyy-mm-dd")
    private String birthday;
}
