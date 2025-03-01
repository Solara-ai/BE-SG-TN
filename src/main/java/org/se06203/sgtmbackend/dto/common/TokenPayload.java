package org.se06203.sgtmbackend.dto.common;

import lombok.Builder;
import lombok.Data;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenPayload {
    private String firstName;
    private String lastName;
    private String email;
}
