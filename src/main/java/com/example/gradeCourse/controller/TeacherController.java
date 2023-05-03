package com.example.gradeCourse.controller;


import com.example.gradeCourse.entity.ReleaseWork;
import com.example.gradeCourse.entity.Teacher;
import com.example.gradeCourse.service.TeacherService;
import com.example.gradeCourse.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    public TeacherService teacherService;

    @RequestMapping("/updateTeacherPwd")
    public Result updateTeacherPwd(@RequestParam("oldPwd")String oldPwd,@RequestParam("newPwd")String newPwd,@RequestParam("tid")String tid){
        return teacherService.updateTeacherPwd(oldPwd,newPwd,tid);
    }

    @RequestMapping(value = "/updateTeacherProfile",method = RequestMethod.POST)
    public Result updateTeacherProfile(@RequestBody Teacher teacher){
        return teacherService.updateTeacherProfile(teacher);
    }

    //教师上传头像
    @RequestMapping(value = "/uploadAvatar",method = RequestMethod.POST)
    public Result uploadAvatar(@RequestParam("avatarfile") MultipartFile file, @RequestParam("tid")String tid){
        return teacherService.uploadAvatar(file,tid);
    }


    @RequestMapping("/getTeacherProfile")
    public Result getTeacherProfile(@RequestParam("tid")String tid){
        return teacherService.getTeacherProfile(tid);
    }

    @RequestMapping("")
    public Result getAllTeachers(@RequestParam(value = "page-num",defaultValue = "1")Integer pageNum, @RequestParam(value = "page-size",defaultValue = "10")Integer pageSize){

        return teacherService.getAllTeachers(pageNum,pageSize);
    }

    @RequestMapping("/pureTeachers")
    public Result getAllPureTeachers(){
        return teacherService.getAllPureTeachers();
    }

    @RequestMapping(value = "/saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher){
        return teacherService.saveTeacher(teacher);
    }

    @RequestMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody Teacher teacher){
        return teacherService.deleteTeacher(teacher);
    }

    @RequestMapping("/updateTeacher")
    public  Result updateTeacher(@RequestBody Teacher teacher,@RequestParam("rawid")String rawid){
        return teacherService.updateTeacher(teacher,rawid);

    }

    @RequestMapping("/searchTeacher")
    public Result searchTeacher(@RequestParam("keyword")String keyword,@RequestParam(value = "page-num",defaultValue = "1")Integer pageNum, @RequestParam(value = "page-size",defaultValue = "10")Integer pageSize){
        return teacherService.searchTeacher(keyword,pageNum,pageSize);
    }

    @RequestMapping("/getCurrentStudentByClassid")
    public Result getCurrentStudentByClassid(@RequestParam(value = "page-num",defaultValue = "1")Integer pageNum, @RequestParam(value = "page-size",defaultValue = "10")Integer pageSize, @RequestParam("classid")String classid,@RequestParam("workid")String workid){
        return teacherService.getCurrentPageStudentByClassid(pageNum,pageSize,classid,workid);
    }
}
