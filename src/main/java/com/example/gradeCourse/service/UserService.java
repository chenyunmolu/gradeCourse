package com.example.gradeCourse.service;


import com.example.gradeCourse.entity.Role;
import com.example.gradeCourse.entity.Student;
import com.example.gradeCourse.entity.Teacher;
import com.example.gradeCourse.entity.User;
import com.example.gradeCourse.mapper.UserMapper;
import com.example.gradeCourse.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;


    public List<User> getAllUsers(){
        List<User> users=null;
        try {
            users=userMapper.findAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return users;
    }

    public User getUserByUserid(String userid){
        return userMapper.find(userid);
    }

    @Transactional
    public void userRegister(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 密码Hash算法
        user.setUserpwd(passwordEncoder.encode(user.getPassword()));// 密码Hash处理
        List<String> roleIds = new ArrayList<>();
        user.getRoles().forEach(r -> roleIds.add(r.getId()));
        userMapper.insertUser(user.getUserid(),user.getUsername(),user.getPassword(),user.getUsertype(),user.getAvatar());
        userMapper.insertUserRoles(user.getUserid(), roleIds);
    }

    public UserDetails loadUserByUsername(String name) {
        return userMapper.loadUserByUsername(name);
    }

    public UserDetails loadUserByUserid(String id) {
        return userMapper.find(id);
    }

    public Result addStudentUser(Student student){
        try{
            User user=new User();
            user.setUserid(student.getStuid());
            user.setUsername(student.getSname());
            //默认密码是123456
            user.setUserpwd("123456");
            user.setUsertype("学生");
            user.setAvatar("/api/gradeCourse/avatar/自然.jpg");
            Role role2=new Role("3","学生");
            List<Role> roles=new ArrayList<>();
            roles.add(role2);
            user.setRoles(roles);
            userRegister(user);
            return new Result("1","添加学生用户成功！");

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","添加学生用户失败！");
        }

    }

    public Result addTeacherUser(Teacher teacher){
        try {
            User user=new User();
            user.setUserid(teacher.getTid());
            user.setUsername(teacher.getTname());
            //默认密码是123456
            user.setUserpwd("123456");
            user.setUsertype("教师");
            user.setAvatar("/api/gradeCourse/avatar/自然.jpg");
            Role role2=new Role("2","教师");
            List<Role> roles=new ArrayList<>();
            roles.add(role2);
            user.setRoles(roles);
            userRegister(user);
            return new Result("1","添加教师用户成功！");

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","添加教师用户失败！");
        }
    }
}
