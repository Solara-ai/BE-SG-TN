package org.se06203.besgtn.dto.request.epic;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateEpicRequest {
    private String name;
    private String description;
    private String type;
}
