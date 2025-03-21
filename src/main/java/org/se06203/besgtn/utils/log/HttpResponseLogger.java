package org.se06203.besgtn.utils.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.se06203.besgtn.utils.Constants;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

@Slf4j
@Aspect
@Configuration
@RequiredArgsConstructor
public class HttpResponseLogger {

    private final HttpServletRequest httpRequest;
    private final HttpServletResponse httpResponse;

    @Pointcut("within(org.se06203.besgtn.config.response.ResponseFactory)")
    public void responseEntityPointCut() {
    }

    @AfterReturning(value = "responseEntityPointCut()", returning = "response")
    public void afterReturnResponse(Object response) {
        var startTime = Long.parseLong(MDC.get(Constants.InternalHttpHeader.START_TIME.getValue()));
        var currentTime = System.currentTimeMillis();
        if (response != null) {
            var body = "";
            var status = 200;
            if (response instanceof ResponseEntity<?> responseEntity) {
//                body = ObjectMapperHelper.prettyPrint(responseEntity.getBody());
                status = responseEntity.getStatusCode().value();
            } else {
//                body = RequestUtils.decodeOrDefault(SerializationUtils.serialize(response), StandardCharsets.UTF_8,
//                        "Binary data");
            }
            log.info("""
                                                        
                            <--- Response in ms {}: {}.{} - ({})
                            {}
                            """
                    , currentTime - startTime, httpRequest.getMethod(), httpRequest.getRequestURL() + (StringUtils.hasText(httpRequest.getQueryString()) ? "?" + httpRequest.getQueryString() : ""), status
                    , body);
        } else {
            log.info("""
                                                        
                            <--- Response in ms {} with no body: {}.{} - ({})
                            """
                    , currentTime - startTime, httpRequest.getMethod(), httpRequest.getRequestURL() + (StringUtils.hasText(httpRequest.getQueryString()) ? "?" + httpRequest.getQueryString() : ""), httpResponse.getStatus());
        }
    }
}
