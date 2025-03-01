package org.se06203.sgtmbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.se06203.sgtmbackend.ultis.validation.ValidEmail;
import org.se06203.sgtmbackend.ultis.validation.ValidPasscode;

@Data
@Builder
public class EmailRequest {

    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @ValidPasscode
    private String password;
}
