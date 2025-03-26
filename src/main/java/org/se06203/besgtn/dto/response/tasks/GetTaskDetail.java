package org.se06203.besgtn.dto.response.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.se06203.besgtn.utils.Constants;

@Data
@Builder
@AllArgsConstructor
public class GetTaskDetail {
    private String id;
    private String epicId;
    private String epicName;
    private String title;
    private String description;
    private Constants.TypeTask status;
}
