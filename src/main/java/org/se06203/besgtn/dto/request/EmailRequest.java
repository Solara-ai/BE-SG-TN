package org.se06203.besgtn.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.se06203.besgtn.utils.validation.ValidEmail;
import org.se06203.besgtn.utils.validation.ValidPasscode;

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
