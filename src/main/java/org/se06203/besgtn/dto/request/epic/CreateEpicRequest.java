package org.se06203.besgtn.dto.request.epic;

import lombok.*;
import org.se06203.besgtn.utils.Constants;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateEpicRequest {
    private String name;
    private String description;
    private Constants.TypeEpic type;
}
