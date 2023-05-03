package com.example.gradeCourse.controller;


import com.example.gradeCourse.entity.Admin;
import com.example.gradeCourse.service.AdminService;
import com.example.gradeCourse.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admins")
public class AdminController {


    @Autowired
    public AdminService adminService;

    @RequestMapping("/updateAdminPwd")
    public Result updateAdminPwd(@RequestParam("oldPwd")String oldPwd,@RequestParam("newPwd")String newPwd,@RequestParam("adminid")String adminid){
        return adminService.updateAdminPwd(oldPwd,newPwd,adminid);
    }

    @RequestMapping(value = "/updateAdminProfile",method = RequestMethod.POST)
    public Result updateAdminProfile(@RequestBody Admin admin){
        return adminService.updateAdminProfile(admin);
    }


    @RequestMapping(value = "/uploadAvatar",method = RequestMethod.POST)
    public Result uploadAvatar(@RequestParam("avatarfile") MultipartFile file, @RequestParam("adminid")String adminid){
        return adminService.uploadAvatar(file,adminid);
    }

    @RequestMapping("/getAdminProfile")
    public Result getAdminProfile(@RequestParam("adminid")String adminid){
        return adminService.getAdminProfile(adminid);
    }
}
