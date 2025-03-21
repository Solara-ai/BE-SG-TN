package org.se06203.besgtn.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateTaskRequest {
    private String name;
    private String description;
    private String type;
}
