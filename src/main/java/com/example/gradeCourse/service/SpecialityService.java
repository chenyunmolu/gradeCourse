package com.example.gradeCourse.service;


import com.example.gradeCourse.entity.Speciality;
import com.example.gradeCourse.mapper.SpecialityMapper;
import com.example.gradeCourse.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityService {

    @Autowired
    public SpecialityMapper specialityMapper;

    public Result getAllSpeciality(){
        try{

            List<Speciality> specialities=specialityMapper.findAll();
            if (specialities.size()==0){
                return new Result("0","查询失败，没有数据");
            }else{
                return new Result("1","查询成功",specialities);
            }
        }catch (Exception e){
            return new Result("0","查询失败！");
        }
    }

    public  Result saveSpeciality(Speciality speciality){
        try{
            int flag=specialityMapper.saveSpeciality(speciality);
            if(flag==0){
                return new Result("0","添加数据失败");
            }else {
                return new Result("1","添加数据成功");
            }
        }catch (Exception e){
            return new Result("0","添加数据失败!!!!");
        }
    }

    public Result updateSpeciality(Speciality speciality,String specid){

        try{
            int flag=specialityMapper.updateSpeciality(speciality,specid);
            if(flag==0){
                return new Result("0","修改数据失败");
            }else {
                return new Result("1","修改数据成功");
            }
        }catch (Exception e){
            return new Result("0","修改数据失败!!!!");
        }

    }

    public Result deleteSpeciality(Speciality speciality){

        try{
            int flag=specialityMapper.deleteSpeciality(speciality);
            if(flag==0){
                return new Result("0","删除数据失败");
            }else {
                return new Result("1","删除数据成功");
            }
        }catch (Exception e){
            return new Result("0","删除数据失败!!!!");
        }

    }

    public Result searchSpeciality(String keyword){
        try {
            List<Speciality> specialities=specialityMapper.searchSpeciality(keyword);
            if (specialities.size()==0){
                return new Result("0","查询失败，没有数据");

            }
            return new Result("1","查询成功！",specialities);

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","查询失败!");
        }
    }



}
