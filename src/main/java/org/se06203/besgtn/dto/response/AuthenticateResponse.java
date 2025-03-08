package org.se06203.besgtn.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticateResponse {
    private String token;
    private String refreshToken;
}
