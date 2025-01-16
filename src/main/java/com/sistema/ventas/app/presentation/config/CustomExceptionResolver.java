package com.sistema.ventas.app.presentation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.ventas.app.domain.shared.exceptions.*;
import com.sistema.ventas.app.shared.utils.ResponseAPI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            ResponseAPI<Void> errorResponse;
            HttpStatus status;

            if (ex instanceof BusinessRuleViolationException) {
                status = HttpStatus.BAD_REQUEST;
                errorResponse = new ResponseAPI<>(status.value(), ex.getMessage(), null);
            } else if (ex instanceof ValueObjectValidationException) {
                status = HttpStatus.UNPROCESSABLE_ENTITY;
                errorResponse = new ResponseAPI<>(status.value(), ex.getMessage(), null);
            } else if (ex instanceof RequiredFieldMissingException) {
                status = HttpStatus.BAD_REQUEST;
                errorResponse = new ResponseAPI<>(status.value(), ex.getMessage(), null);
            } else if (ex instanceof NotFoundException) {
                status = HttpStatus.NOT_FOUND;
                errorResponse = new ResponseAPI<>(status.value(), ex.getMessage(), null);
            } else if (ex instanceof UniqueConstraintViolationException) {
                status = HttpStatus.CONFLICT;
                errorResponse = new ResponseAPI<>(status.value(), ex.getMessage(), null);
            } else if (ex instanceof AuthenticationFailedException) {
                status = HttpStatus.UNAUTHORIZED;
                errorResponse = new ResponseAPI<>(status.value(), ex.getMessage(), null);
            } else if(ex instanceof  TransactionFailedException) {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                errorResponse = new ResponseAPI<>(status.value(), ex.getMessage(), null);
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                System.out.println(ex.getMessage());
                errorResponse = new ResponseAPI<>(status.value(), "Ocurrió un error inesperado", null);
            }

            response.setStatus(status.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}