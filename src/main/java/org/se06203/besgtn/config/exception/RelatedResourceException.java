package org.se06203.besgtn.config.exception;

import lombok.Getter;
import lombok.Setter;
import org.se06203.besgtn.config.exception.handler.BaseErrorCodeMsg;

import java.util.List;

@Setter
@Getter
public class RelatedResourceException extends BaseRuntimeException {

    private final List<RelatedResource> relatedResources;

    public RelatedResourceException(BaseErrorCodeMsg baseErrorCodeMsg, List<RelatedResource> relatedResources) {
        super(baseErrorCodeMsg);
        this.relatedResources = relatedResources;
    }
}
