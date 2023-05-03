package com.example.gradeCourse.model;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginCredentials {

    @NotEmpty(message = "请输入用户名")
    private String userName;
    @NotEmpty(message = "请输入密码")
    private String password;
}
