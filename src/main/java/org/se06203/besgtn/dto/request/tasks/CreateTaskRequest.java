package org.se06203.besgtn.dto.request.tasks;

import lombok.*;
import org.se06203.besgtn.utils.Constants;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateTaskRequest {

    private String title;
    private String description;
}
