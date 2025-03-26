package org.se06203.besgtn.dto.response.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.se06203.besgtn.utils.Constants;

@Data
@Builder
@AllArgsConstructor
public class TaskItem {
    private String id;
    private String title;
    private String description;
    private Constants.TypeTask status;
}
