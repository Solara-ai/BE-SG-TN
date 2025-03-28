package org.se06203.besgtn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RefreshTokenReq {
    private String refreshToken;
}
