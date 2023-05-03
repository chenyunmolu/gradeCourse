package com.example.gradeCourse.controller;


import com.example.gradeCourse.entity.ReleaseWork;
import com.example.gradeCourse.service.ReleaseWorkService;
import com.example.gradeCourse.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/releasework")
public class ReleaseWorkController {




    @Autowired
    public ReleaseWorkService releaseWorkService;

    @RequestMapping("")
    public Result getAllWorks(){
        return releaseWorkService.getAllWorks();
    }


    @RequestMapping("/getWorkByTid")
    public Result getWorkByTid(@RequestParam("tid")String tid){
        return releaseWorkService.getWorkByTid(tid);
    }

    @RequestMapping("/saveWork")
    public Result saveWork(@RequestBody ReleaseWork releaseWork){
        return releaseWorkService.saveWork(releaseWork);
    }

    @RequestMapping("/updateWork")
    public Result updateWork(@RequestBody ReleaseWork releaseWork){
        return releaseWorkService.updateWork(releaseWork);
    }

    @RequestMapping("/deleteWork")
    public Result deleteWork(@RequestBody ReleaseWork releaseWork){
        return releaseWorkService.deleteWork(releaseWork);
    }

    @RequestMapping("/searchWork")
    public Result searchWork(@RequestParam("keyword")String keyword,@RequestParam("tid")String tid){
        return releaseWorkService.searchWork(keyword,tid);
    }

    @RequestMapping("/uploadWorkImg")
    public Result uploadWorkImg(@RequestBody MultipartFile file, HttpServletRequest request){
        return releaseWorkService.uploadWorkImg(file,request);
    }

    //教师上传相关资料的压缩包
    @RequestMapping(value = "/uploadRelevantMaterial",method = RequestMethod.POST)
    public Result uploadRelevantMaterial(@RequestBody MultipartFile file,@RequestParam("workid")String workid){
        return releaseWorkService.uploadRelevantMaterial(file,workid);
    }

}
