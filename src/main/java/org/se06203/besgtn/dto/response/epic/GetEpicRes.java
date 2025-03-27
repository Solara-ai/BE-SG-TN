package org.se06203.besgtn.dto.response.epic;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetEpicRes {
    private String id;
    private String nameEpic;
    private String description;
    private String type;
}
