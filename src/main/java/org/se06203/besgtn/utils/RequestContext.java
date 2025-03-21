package org.se06203.besgtn.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

import java.util.Set;

/**
 * This class should be request scope to avoid concurrency issue
 * Need to be created a new instance for each request (may be in filter)
 * Can be used to store user data for each request
 */

@Setter
@Getter
public class RequestContext {
    
    private String correlationId;
    private String token;
    private Long userId;
    private String sessionId;
    private String userFullName;
    private Set<Long> dataScopes;

    @JsonIgnore
    public HttpHeaders getCurrenRequestContextHeaders() {
        var httpHeaders = new HttpHeaders();
        httpHeaders.add(Constants.InternalHttpHeader.CORRELATION_ID.getValue(),
                correlationId);
        httpHeaders.addIfAbsent(HttpHeaders.AUTHORIZATION,
               token);
        return httpHeaders;
    }

    @Override
    public String toString() {
        return String.format("RequestContext = [correlationId=%s, userId=%s, sessionId=%s, userFullName=%s]",
                correlationId, userId, sessionId, userFullName);
    }
}
