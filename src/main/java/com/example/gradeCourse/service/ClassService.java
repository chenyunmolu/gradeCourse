package com.example.gradeCourse.service;


import com.example.gradeCourse.entity.Class;
import com.example.gradeCourse.mapper.ClassMapper;
import com.example.gradeCourse.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    public ClassMapper classMapper;


    public Result getAllClasses(){
        try{

            List<Class> classes=classMapper.findAll();
            if (classes.size()==0){
                return new Result("0","查询班级失败，没有数据");
            }else {
                return new Result("1","查询班级成功！",classes);
            }
        }catch (Exception e){
            return new Result("0","查询班级失败!!!!");
        }
    }

    public Result searchClass(String keyword){
        try {
            List<Class> classes=classMapper.searchClass(keyword);
            if (classes.size()==0){
                return new Result("0","查询班级失败了，没有数据");
            }
            return new Result("1","查询班级成功！",classes);

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","查询班级失败了");
        }
    }


    public Result saveClass(Class aClass){
        try {
            int flag=classMapper.saveClass(aClass);
            if (flag==0){
                return new Result("0","添加班级失败");
            }
            return new Result("1","添加班级成功！");

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","添加班级失败了");
        }
    }

    public Result updateClass(Class aClass,String rawid){
        try {
            int flag=classMapper.updateClass(aClass,rawid);
            if (flag==0){
                return new Result("0","修改班级失败");
            }
            return new Result("1","修改班级成功！");

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","修改班级失败了");
        }
    }


    public Result deleteClass(Class aClass){
        try {
            int flag=classMapper.deleteClass(aClass);
            if (flag==0){
                return new Result("0","删除班级失败");
            }
            return new Result("1","删除班级成功！");

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","删除班级失败了");
        }
    }
}
