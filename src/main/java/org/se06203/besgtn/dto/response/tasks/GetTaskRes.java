package org.se06203.besgtn.dto.response.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetTaskRes {
    private String epicId;
    private String epicName;
    private String description;
    private List<TaskItem> tasks;
}
