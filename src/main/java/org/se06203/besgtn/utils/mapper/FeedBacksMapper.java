package org.se06203.besgtn.utils.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.se06203.besgtn.dto.response.FeedBackDetailRes;
import org.se06203.besgtn.persistence.entity.FeedBacks;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedBacksMapper {
    FeedBackDetailRes mapToFeedBackDetailRes(FeedBacks feedBack);
}
