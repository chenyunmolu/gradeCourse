package com.example.gradeCourse.controller;

import com.example.gradeCourse.util.BusinessException;
import com.example.gradeCourse.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public Result handleException1(SQLException e) {
        String msg = "SQL 异常："+e.getMessage();
        Result result=new Result("21",msg); return result;
    }
    /**业务异常 service 类中抛出的异常 自定义 BusinessException 类*/
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Result handleException2(BusinessException e) {
        String msg = "业务异常："+e.getMessage();
        Result result=new Result("22",msg); return result;
    }
    /**系统运行时异常处理 如 NullPointerException */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result handleException3(RuntimeException e) {
        String msg = "运行时异常："+e.getMessage();
        Result result=new Result("30",msg); return result;
    }
    /**系统级异常处理 未知异常*/
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handleException4(Exception e) {
        String msg = "系统异常："+e.getMessage();
        Result result=new Result("31",msg); return result;
    }
}
