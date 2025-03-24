package org.se06203.besgtn.utils.mapper;

import org.mapstruct.*;
import org.se06203.besgtn.dto.request.ScheduleDto.CategoriesItem;
import org.se06203.besgtn.persistence.entity.Categories;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriesMapper {

    @Mapping(target = "categoryId", source = "id")
    @Mapping(target = "categoryName", source = "name")
    CategoriesItem mapCategoriesToGetCategories(Categories categories);
}
