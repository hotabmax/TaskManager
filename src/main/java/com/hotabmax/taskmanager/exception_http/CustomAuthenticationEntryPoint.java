package com.hotabmax.taskmanager.exception_http;

import com.google.gson.Gson;
import com.hotabmax.taskmanager.dtos_error.AppError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private Gson gson = new Gson();
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(gson.toJson(new AppError(HttpStatus.FORBIDDEN.value(),
                "You not authorized or not have correct JWT for access")));
    }
}
