package com.example.gradeCourse.controller;


import com.example.gradeCourse.entity.Class;
import com.example.gradeCourse.service.ClassService;
import com.example.gradeCourse.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classes")
public class ClassController {


    @Autowired
    public ClassService classService;


    @RequestMapping("")
    public Result getAllClasses(){
        return classService.getAllClasses();
    }


    @RequestMapping("/searchClass")
    public Result searchClass(@RequestParam("keyword")String keyword){
        return classService.searchClass(keyword);
    }

    @RequestMapping("/saveClass")
    public Result saveClass(@RequestBody Class aClass){
        return classService.saveClass(aClass);
    }

    @RequestMapping("/updateClass")
    public Result updateClass(@RequestBody Class aClass, @RequestParam("rawid")String rawid){
        return classService.updateClass(aClass,rawid);
    }

    @RequestMapping("deleteClass")
    public Result deleteClass(@RequestBody Class aClass){
        return classService.deleteClass(aClass);
    }
}
