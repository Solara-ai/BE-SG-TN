package org.se06203.besgtn.dto.response.scheduleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoriesItem {
    private String categoryId;
    private String categoryName;
    private String color;
}
