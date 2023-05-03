package com.example.gradeCourse.controller;


import com.example.gradeCourse.entity.SubmitWork;
import com.example.gradeCourse.service.SubmitWorkService;
import com.example.gradeCourse.util.Result;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/submitWork")
public class SubmitWorkController {


    @Autowired
    public SubmitWorkService submitWorkService;

    @RequestMapping(value = "/updateSubmitWork",method = RequestMethod.POST)
    public Result updateSubmitWork(@RequestBody SubmitWork submitWork){
        return submitWorkService.updateSubmitWork(submitWork);
    }
}
