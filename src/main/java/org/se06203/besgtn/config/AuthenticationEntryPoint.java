package org.se06203.besgtn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import java.io.IOException;

@Slf4j
public class AuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.info("Authentication error: {}", authException.getMessage());

        ResponseFactory res = new ResponseFactory();
        BaseResponse  responseBody;

        if (authException.getCause() instanceof BaseRuntimeException baseEx) {
            responseBody = res.fail(baseEx).getBody();

        } else {
            responseBody = res.fail(ErrorCode.UNAUTHORIZED).getBody();
        }

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
        response.getWriter().flush();
    }
}
