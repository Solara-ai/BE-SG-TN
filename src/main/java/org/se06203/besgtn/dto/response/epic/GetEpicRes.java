package org.se06203.besgtn.dto.response.epic;

import lombok.*;
import org.se06203.besgtn.utils.Constants;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetEpicRes {
    private String id;
    private String nameEpic;
    private String description;
    private Constants.TypeEpic type;
}
