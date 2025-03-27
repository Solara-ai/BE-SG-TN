package org.se06203.besgtn.dto.response.tasks;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetTotalTaskAndPlanResponse {
    private String totalTasks;
    private String totalPlans;
}
