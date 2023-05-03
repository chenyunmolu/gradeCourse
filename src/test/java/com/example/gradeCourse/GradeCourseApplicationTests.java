package com.example.gradeCourse;

import com.example.gradeCourse.entity.Role;
import com.example.gradeCourse.entity.User;
import com.example.gradeCourse.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class GradeCourseApplicationTests {

    @Autowired
    public UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    public void addUser(){
        User user=new User();
        user.setUserid("201911105045");
        user.setUsername("黄俊");
        user.setUserpwd("123456");
        user.setUsertype("学生");
        user.setAvatar("/api/gradeCourse/avatar/自然.jpg");

        Role role2=new Role("3","学生");

        List<Role> roles=new ArrayList<>();
        roles.add(role2);
        user.setRoles(roles);
        userService.userRegister(user);
    }

}
