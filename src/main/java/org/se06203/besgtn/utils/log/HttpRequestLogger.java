package org.se06203.besgtn.utils.log;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.utils.HttpRequestWrapper;
import org.se06203.besgtn.utils.ObjectMapperHelper;
import org.se06203.besgtn.utils.RequestContext;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.util.HashMap;
import java.util.UUID;

import static org.se06203.besgtn.utils.Constants.InternalHttpHeader.CORRELATION_ID;
import static org.se06203.besgtn.utils.Constants.InternalHttpHeader.START_TIME;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpRequestLogger extends OncePerRequestFilter {

    @Resource(name = "baseContext")
    private RequestContext requestContext;

    private final StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();


    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.contains("api-docs")
                || path.contains("/swagger-config")
                || path.contains("/swagger-resources")
                || path.contains("/swagger-ui");
    }

    @Override
    @SneakyThrows
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) {

        if (!StringUtils.hasText(request.getHeader(CORRELATION_ID.getValue()))) {
            requestContext.setCorrelationId(UUID.randomUUID().toString());
        } else {
            requestContext.setCorrelationId(request.getHeader(CORRELATION_ID.getValue()));
        }

        MDC.put(CORRELATION_ID.getValue(), requestContext.getCorrelationId());
        MDC.put(START_TIME.getValue(), String.valueOf(System.currentTimeMillis()));
        response.setHeader(CORRELATION_ID.getValue(), requestContext.getCorrelationId());

        if (multipartResolver.isMultipart(request)) {
            var multipartRequest = multipartResolver.resolveMultipart(request);
            var bodyMap = new HashMap<>();
            multipartRequest.getParameterMap().forEach((key, values) -> {
                bodyMap.put(key, values[0]);
            });
            multipartRequest.getFileMap().forEach((key, file) -> {
                bodyMap.put(key, file.getOriginalFilename());
            });

            log.info("""
                                                        
                            ---> Receive: {}.{}
                            {}
                            """,
                    request.getMethod(), request.getRequestURL() + (StringUtils.hasText(request.getQueryString()) ? "?" + request.getQueryString() : ""),
                    ObjectMapperHelper.prettyPrint(bodyMap));

            filterChain.doFilter(request, response);
            return;
        }

        var requestCached = new HttpRequestWrapper(request);
        var bodyData = requestCached.getCache();
        if (bodyData.length > 0) {
            log.info("""
                                                        
                            ---> Receive: {}.{}
                            {}
                            """,
                    request.getMethod(), request.getRequestURL() + (StringUtils.hasText(request.getQueryString()) ? "?" + request.getQueryString() : ""),
                    ObjectMapperHelper.prettyPrint(bodyData));
        } else {
            log.info("""
                                                        
                            ---> Receive: {}.{}
                            """,
                    request.getMethod(), request.getRequestURL() + (StringUtils.hasText(request.getQueryString()) ? "?" + request.getQueryString() : ""));
        }

        filterChain.doFilter(requestCached, response);
    }
}
