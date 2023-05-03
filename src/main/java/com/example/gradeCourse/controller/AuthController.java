package com.example.gradeCourse.controller;


import com.example.gradeCourse.entity.User;
import com.example.gradeCourse.mapper.RoleMapper;
import com.example.gradeCourse.model.LoginCredentials;
import com.example.gradeCourse.security.JWTUtil;
import com.example.gradeCourse.security.MyAuthenticationManager;
import com.example.gradeCourse.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMapper roleMapper;


    @Autowired
    private MyAuthenticationManager authManager;

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body, HttpServletResponse response) {
        String plainPassword = null;
        Map<String, Object> result = new HashMap<>();
        // plainPassword = RSAUtil.decryptRSA(body.getPassword(), this.privateKeyString);
        // if (null == plainPassword) {
        //     result.put("errorMessage", "密码解密异常");
        //     response.setStatus(401);
        //     return result;
        // }

        try {
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
                    body.getUserName(),
                    body.getPassword());
            // plainPassword);
            authManager.authenticate(authInputToken);

            // If this point is reached it means Authentication was successful
            // Generate the JWT
            String token = jwtUtil.generateToken(body.getUserName());

            User user = (User) userService.loadUserByUserid(body.getUserName());

            user.setRoles(roleMapper.selectRoleById(user.getUserid()));
            user.setUserpwd(null);

            result.put("user", user);
            result.put("token", token);
            // Respond with the JWT

            System.out.println(result);
            return result;
        } catch (Exception e) {
            logger.info("认证失败，用户名或密码错误");
            result.put("errorMessage", "认证失败，用户名或密码错误");
            response.setStatus(401);
            return result;
        }

    }


}
