package com.example.gradeCourse.controller;


import com.example.gradeCourse.entity.Speciality;
import com.example.gradeCourse.service.SpecialityService;
import com.example.gradeCourse.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spec")
public class SpecialityController {

    @Autowired
    public SpecialityService specialityService;

    @RequestMapping("")
    public Result getAllSpeciality(){
        return specialityService.getAllSpeciality();
    }

    @RequestMapping("/saveSpec")
    public Result saveSpeciality(@RequestBody Speciality speciality){
        return specialityService.saveSpeciality(speciality);
    }

    @RequestMapping("/updateSpec")
    public Result updateSpeciality(@RequestBody Speciality speciality, @RequestParam("specid")String specid){
        return specialityService.updateSpeciality(speciality,specid);
    }

    @RequestMapping("/deleteSpec")
    public Result deleteSpeciality(@RequestBody Speciality speciality){
        return specialityService.deleteSpeciality(speciality);
    }

    @RequestMapping("/searchSpec")
    public Result searchSpeciality(@RequestParam("keyword")String keyword){
        return specialityService.searchSpeciality(keyword);
    }
}
