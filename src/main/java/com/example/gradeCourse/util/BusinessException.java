package com.example.gradeCourse.util;

import org.springframework.stereotype.Component;

@Component
public class BusinessException extends Exception {

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}
