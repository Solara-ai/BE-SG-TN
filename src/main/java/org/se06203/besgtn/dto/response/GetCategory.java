package org.se06203.besgtn.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetCategory {
    private String categoryId;
    private String categoryName;
    private String categoryColor;
}
