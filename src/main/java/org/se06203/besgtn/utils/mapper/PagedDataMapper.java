package org.se06203.besgtn.utils.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.se06203.besgtn.config.response.PagedData;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PagedDataMapper {

    default <T> PagedData<T> mapToPagedData(Page<T> page) {
        return PagedData.<T>builder()
                .pageNo(page.getNumber())
                .elementPerPage(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .elementList(page.getContent())
                .build();
    }
}
