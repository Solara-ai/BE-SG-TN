package org.se06203.sgtmbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.se06203.sgtmbackend.ultis.validation.ValidEmail;
import org.se06203.sgtmbackend.ultis.validation.ValidPasscode;
import org.se06203.sgtmbackend.ultis.validation.ValidPhoneNumber;


@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterRequest {

    @NotNull
    private String userName;

    private String firstName;

    private String lastName;

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @ValidPasscode
    private String password;

    @NotNull
    @ValidPhoneNumber
    private String phone;

    private String gender;

    private String roles;
}
