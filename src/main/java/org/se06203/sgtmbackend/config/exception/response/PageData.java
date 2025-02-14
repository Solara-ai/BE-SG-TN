package org.se06203.sgtmbackend.config.exception.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageData<E> {
    private final List<E> content;
    private final Long totalElements;
    private final Integer totalPages;
    private final Boolean hasPrevious;
    private final Boolean hasNext;
    private final Integer number;
    private final Integer size;

    @JsonCreator
    public PageData(
            @JsonProperty("content") List<E> content,
            @JsonProperty("totalElements") Long totalElements,
            @JsonProperty("totalPages") Integer totalPages,
            @JsonProperty("hasPrevious") Boolean hasPrevious,
            @JsonProperty("hasNext") Boolean hasNext,
            @JsonProperty("number") Integer number,
            @JsonProperty("size") Integer size) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.hasPrevious = hasPrevious;
        this.hasNext = hasNext;
        this.number = number;
        this.size = size;
    }

    public static <T> PageData<T> empty() {
        return PageData.<T>builder()
                .content(new LinkedList<>())
                .totalElements(0L)
                .totalPages(0)
                .hasPrevious(false)
                .hasNext(false)
                .build();
    }

}