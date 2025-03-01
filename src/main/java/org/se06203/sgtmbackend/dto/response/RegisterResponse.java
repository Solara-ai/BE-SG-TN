package org.se06203.sgtmbackend.dto.response;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterResponse {

    private String token;
    private String refreshToken;
}
