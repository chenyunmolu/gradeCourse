package com.example.gradeCourse.controller;


import com.example.gradeCourse.entity.Student;
import com.example.gradeCourse.service.StudentService;
import com.example.gradeCourse.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/students")
public class StudentController {




    @Autowired
    public StudentService studentService;

    @RequestMapping("/getMyWork")
    public Result getMyWork(@RequestParam("stuid")String stuid){
        return studentService.getMyWork(stuid);
    }

    @RequestMapping("/updateStudentPwd")
    public Result updateStudentPwd(@RequestParam("oldPwd")String oldPwd,@RequestParam("newPwd")String newPwd,@RequestParam("stuid")String stuid){
        return studentService.updateStudentPwd(oldPwd,newPwd,stuid);
    }


    @RequestMapping("/updateStudentProfile")
    public Result updateStudentProfile(@RequestBody Student student){
        return studentService.updateStudentProfile(student);
    }

    @RequestMapping("/getStudentProfile")
    public Result getStudentProfile(@RequestParam("stuid")String stuid){
        return studentService.getStudentProfile(stuid);
    }


    @RequestMapping("")
    public Result getAllStudents(){
        return studentService.getAllStudents();
    }



    @RequestMapping("/currentPageStudent")
    public Result getCurrentPageStudent(@RequestParam(value = "page-num",defaultValue = "1")Integer pageNum, @RequestParam(value = "page-size",defaultValue = "10")Integer pageSize){
        return studentService.getCurrentPageStudents(pageNum,pageSize);
    }

    @RequestMapping("/saveStudent")
    public Result saveStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    //学生上传头像
    @RequestMapping(value = "/uploadAvatar",method = RequestMethod.POST)
    public Result uploadAvatar(@RequestParam("avatarfile") MultipartFile file,@RequestParam("stuid")String stuid){
        return studentService.uploadAvatar(file,stuid);
    }

    //学生上传作业的压缩包
    @RequestMapping(value = "/uploadWork",method = RequestMethod.POST)
    public Result uploadWork(@RequestBody MultipartFile file,HttpServletRequest request,@RequestParam("stuid")String stuid,@RequestParam("workid")String workid){
        return studentService.uploadWork(file,request,stuid,workid);
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Result upload(@RequestBody MultipartFile file, HttpServletRequest req) {
            return studentService.upload(file,req);
    }


    @RequestMapping(value = "/importTemplate")
    public void importTemplate(HttpServletResponse response){
        studentService.importTemplate(response);

    }

    @RequestMapping(value = "/exportStudent")
    public void exportStudent(HttpServletResponse response){
       studentService.exportStudent(response);

    }

    @RequestMapping("/importStudent")
    public Result  importStudent(String filepath){
        return studentService.importStudent(filepath);
    }


    @RequestMapping("/updateStudent")
    public Result updateStudent(@RequestBody Student student,@RequestParam("stuid")String stuid){
        return studentService.updateStudent(student,stuid);
    }


    @RequestMapping("/deleteStudent")
    public Result deleteStudent(@RequestBody Student student){
        return studentService.deleteStudent(student);
    }

    @RequestMapping("/searchStudent")
    public Result searchStudent(@RequestParam("keyword")String keyword,@RequestParam(value = "page-num",defaultValue = "1")Integer pageNum, @RequestParam(value = "page-size",defaultValue = "10")Integer pageSize){
        return studentService.searchStudent(keyword,pageNum,pageSize);
    }
}
