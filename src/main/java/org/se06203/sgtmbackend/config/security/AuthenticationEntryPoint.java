package org.se06203.sgtmbackend.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.se06203.sgtmbackend.config.ErrorCode;
import org.se06203.sgtmbackend.config.exception.response.ResponseWrapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import java.io.IOException;

@Slf4j
public class AuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException {
        log.info("error: {}", arg2.getMessage());
        ResponseWrapper<?> res;
        if (arg2 instanceof UsernameNotFoundException) {
            res = ResponseWrapper.error(ErrorCode.NOT_FOUND, "user", "login");
        } else {
            res = ResponseWrapper.error(ErrorCode.UNAUTHORIZED);
        }

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(res));
        response.getWriter().flush();
    }
}
