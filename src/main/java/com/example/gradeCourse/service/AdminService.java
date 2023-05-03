package com.example.gradeCourse.service;


import com.example.gradeCourse.entity.Admin;
import com.example.gradeCourse.entity.Teacher;
import com.example.gradeCourse.mapper.AdminMapper;
import com.example.gradeCourse.mapper.UserMapper;
import com.example.gradeCourse.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class AdminService {

    @Value("${avatar-save-path}")
    private String avatarSavePath;

    @Autowired
    public AdminMapper adminMapper;

    @Autowired
    public UserMapper userMapper;

    public Result updateAdminPwd(String oldPwd, String newPwd, String adminid){
        try{
            PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
            String rawPwd=adminMapper.getAdminPwd(adminid);
            if(!passwordEncoder.matches(oldPwd,rawPwd)){
                return new Result("0","修改密码失败,旧密码错误！");
            }else{
                int flag=adminMapper.updateAdminPwd(passwordEncoder.encode(newPwd),adminid);
                if(flag==0){
                    return new Result("0","修改密码失败,旧密码是正确的");
                }else{
                    return new Result("1","修改密码成功！");
                }

            }

        }catch (Exception e){
            return new Result("0","修改密码失败!!!!");
        }
    }

    public Result updateAdminProfile(Admin admin){
        try {
            int flag=adminMapper.updateAdminProfile(admin);
            if(flag==0){
                return new Result("0","抱歉，修改管理员个人信息失败！");
            }else {
                return new Result("1","恭喜，修改管理员个人信息成功！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","抱歉，修改管理员个人信息失败！！！！！！");
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    //上传头像
    public Result uploadAvatar(MultipartFile uploadFile, String adminid) {

        String realPath = avatarSavePath;
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        String newName="";
        if(uploadFile.getContentType().equals("image/png")){
            newName= UUID.randomUUID().toString() +".png";;
        }else{
            newName=UUID.randomUUID().toString() +".jpg";;
        }
        System.out.println(newName);
        try {
            uploadFile.transferTo(new File(folder, newName));
            String avatar="/api/gradeCourse/avatar/"+format+newName;
            System.out.println(avatar);

            int flag=userMapper.updateAvatar(avatar,adminid);
            if(flag==0){
                return new Result("0", "上传头像失败！");
            }else {
                return new Result("1", "上传头像成功！",avatar);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new Result("0", "上传头像失败！！！！！！！");
        }

    }


    //获取管理员的个人信息
    public Result getAdminProfile(String adminid){
        try {
            Admin admin=adminMapper.findById(adminid);
            if (admin==null){
                return new Result("0","获取管理员的个人信息失败！没有该管理员！");
            }else{
                return new Result("1","获取教师个人信息成功！",admin);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","获取管理员的个人信息失败！！！！！！！！");
        }
    }
}
