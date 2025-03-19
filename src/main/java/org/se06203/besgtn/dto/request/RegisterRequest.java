package org.se06203.besgtn.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.se06203.besgtn.utils.validation.ValidEmail;
import org.se06203.besgtn.utils.validation.ValidPasscode;
import org.se06203.besgtn.utils.validation.ValidPhoneNumber;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterRequest {

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
    private String gender;

    @NotNull
    private String hobbies;

    @NotNull
    private String Occupation;

    @NotNull
    private String birthday;
}
