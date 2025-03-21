package org.se06203.besgtn.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelatedResource {
    private Long id;
    private String url;
    private String type;
    private String name;
}
