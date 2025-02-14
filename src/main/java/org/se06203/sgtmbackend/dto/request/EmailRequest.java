package org.se06203.sgtmbackend.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.se06203.sgtmbackend.ultis.validation.ValidEmail;
import org.se06203.sgtmbackend.ultis.validation.ValidPasscode;

@Data
@Builder
public class EmailRequest {

    @NonNull
    @ValidEmail
    private String email;

    @NonNull
    @ValidPasscode
    private String password;
}
