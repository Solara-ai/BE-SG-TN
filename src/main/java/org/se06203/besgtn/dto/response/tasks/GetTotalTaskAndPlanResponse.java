package org.se06203.besgtn.dto.response.tasks;

import lombok.*;
import org.se06203.besgtn.dto.response.scheduleDto.GetListScheduleRes;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GetTotalTaskAndPlanResponse {
    private String totalTasks;
    private String totalPlans;
    private GetListScheduleRes schedule;
}
