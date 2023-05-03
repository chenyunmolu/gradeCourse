package com.example.gradeCourse.service;


import com.example.gradeCourse.entity.ReleaseWork;
import com.example.gradeCourse.mapper.ReleaseWorkMapper;
import com.example.gradeCourse.util.ImageCompareTool;
import com.example.gradeCourse.util.Result;
import com.example.gradeCourse.util.UnzipUtil;
import com.example.gradeCourse.util.WebDriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReleaseWorkService {

    @Value("${img-save-path}")
    private String imgSavePath;

    @Value("${file-save-path}")
    private String fileSavePath;

    @Autowired
    public ReleaseWorkMapper releaseWorkMapper;


    public Result getAllWorks(){
        try{
            List<ReleaseWork> releaseWorks=releaseWorkMapper.findAll();
            if(releaseWorks.size()==0){
                return new Result("0","查询失败，没有数据！");
            }else {
                return new Result("1","查询成功",releaseWorks);
            }
        }catch (Exception e){
            return new Result("0","查询失败!!!!!!!!");
        }
    }


    public  Result getWorkByWorkid(String workid){
        try{
            ReleaseWork releaseWork=releaseWorkMapper.findByWorkid(workid);
            if(releaseWork==null){
                return new Result("0","查询失败，没有数据！");
            }else {
                return new Result("1","查询成功",releaseWork);
            }
        }catch (Exception e){
            return new Result("0","查询失败!!!!!!!!");
        }
    }

    public  Result getWorkByTid(String tid){
        try{
            List<ReleaseWork> releaseWork=releaseWorkMapper.findByTid(tid);
            if(releaseWork.size()==0){
                return new Result("1","查询失败，没有数据！",0);
            }else {
                return new Result("1","查询成功",releaseWork);
            }
        }catch (Exception e){
            return new Result("0","查询失败!!!!!!!!");
        }
    }

    public Result saveWork(ReleaseWork releaseWork){
        try{
            int flag=releaseWorkMapper.saveWork(releaseWork);
            if(flag==0){
                return new Result("0","保存失败！");
            }else{
                return new Result("1","保存成功！");
            }
        }catch (Exception e){
            return new Result("0","插入失败！！！！！！！！！！");
        }
    }

    public Result updateWork(ReleaseWork releaseWork){
        try{
            int flag=releaseWorkMapper.updateWork(releaseWork);
            if(flag==0){
                return new Result("0","修改失败！");
            }else{
                return new Result("1","修改成功！");
            }
        }catch (Exception e){
            return new Result("0","修改失败！！！！！！！！！！");
        }
    }

    public Result deleteWork(ReleaseWork releaseWork){
        try{
            int flag=releaseWorkMapper.deleteWork(releaseWork);
            if(flag==0){
                return new Result("0","删除失败！");
            }else{
                return new Result("1","删除成功成功！");
            }
        }catch (Exception e){
            return new Result("0","删除失败！！！！！！！！！！");
        }
    }

    public  Result searchWork(String keyword,String tid){
        try{
            List<ReleaseWork> releaseWorks=releaseWorkMapper.searchWork(keyword,tid);
            if(releaseWorks.size()==0){
                return new Result("0","查询失败，没有数据！");
            }else {
                return new Result("1","查询成功",releaseWorks);
            }
        }catch (Exception e){
            return new Result("0","查询失败!!!!!!!!");
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    public  Result uploadWorkImg(MultipartFile uploadFile, HttpServletRequest req){
        String realPath = imgSavePath;
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        String filePath = "";
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        String oldName = uploadFile.getOriginalFilename();
        if(!oldName.substring(oldName.lastIndexOf("."),oldName.length()).equals(".jpg") && !oldName.substring(oldName.lastIndexOf("."),oldName.length()).equals(".png")){
            return new Result("0","上传失败，上传文图片类型必须为jpg、png类型");
        }
        String newName = UUID.randomUUID().toString() +
                oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            uploadFile.transferTo(new File(folder, newName));
            filePath = realPath + format + newName;
            return new Result("1","上传图片成功",filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result("0", "上传图片失败！！！！");
        }
    }

    //上传相关资料的压缩包
    public  Result uploadRelevantMaterial(MultipartFile file,String workid){
        String realpath=fileSavePath;
        String format=sdf.format(new Date());
        File file1=new File(realpath+format);
        String filepath="";
        if(!file1.isDirectory()){
            file1.mkdirs();
        }

        String oldname=file.getOriginalFilename();
        if(!oldname.substring(oldname.lastIndexOf("."),oldname.length()).equals(".zip")){
            return new Result("0","上传失败，上传文件类型必须为zip类型");
        }
        String newname=UUID.randomUUID().toString()+oldname.substring(oldname.lastIndexOf("."),oldname.length());
        try {
            file.transferTo(new File(file1,newname));
            filepath=realpath+format+newname;
            System.out.println(filepath);
            String relevantMaterial="/api/gradeCourse/uploadFile/"+format+newname;

            //修改提交记录
            int flag=releaseWorkMapper.updateRelevantMaterial(relevantMaterial,workid);
            if(flag==0){
                return new Result("0","作业上传失败！数据没有发生变化！");
            }else{
                return new Result("1","作业上传成功！");
            }

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","上传作业失败！！！！");

        }




    }


}

