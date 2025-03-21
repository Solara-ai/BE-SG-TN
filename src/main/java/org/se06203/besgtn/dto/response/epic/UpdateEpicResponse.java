package org.se06203.besgtn.dto.response.epic;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEpicResponse {
    private String name;
    private String description;
    private String type;
}
