package com.example.gradeCourse.util;


import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Result {

    private String code;
    private String msg;
    private Object data;

    public Result() {

    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
