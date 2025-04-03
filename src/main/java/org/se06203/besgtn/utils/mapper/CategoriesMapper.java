package org.se06203.besgtn.utils.mapper;

import org.mapstruct.*;
import org.se06203.besgtn.dto.response.scheduleDto.CategoriesItem;
import org.se06203.besgtn.persistence.entity.Categories;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoriesMapper {

    @Mapping(target = "categoryId", source = "id")
    @Mapping(target = "categoryName", source = "name")
    @Mapping(target = "categoryColor", source = "color")
    CategoriesItem mapCategoriesToGetCategories(Categories categories);
}
