package org.se06203.sgtmbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.se06203.sgtmbackend.ultis.validation.ValidEmail;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyEmailRequest
{
    @NotNull
    @ValidEmail
    private String email;
}
