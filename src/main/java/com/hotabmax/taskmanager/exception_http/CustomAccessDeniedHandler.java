package com.hotabmax.taskmanager.exception_http;

import com.google.gson.Gson;
import com.hotabmax.taskmanager.dtos_error.AppError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private Gson gson = new Gson();
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(gson.toJson(new AppError(HttpStatus.FORBIDDEN.value(),
                "You role not have access for this request")));
    }
}
