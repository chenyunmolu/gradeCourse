package com.example.gradeCourse.service;


import com.example.gradeCourse.entity.SubmitWork;
import com.example.gradeCourse.mapper.SubmitWorkMapper;
import com.example.gradeCourse.util.Result;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmitWorkService {

    @Autowired
    public SubmitWorkMapper submitWorkMapper;

    public Result updateSubmitWork(SubmitWork submitWork){
        try {
            int flag=submitWorkMapper.updateSubmitWork(submitWork);
            if(flag==0){
                return new Result("0","修改最终评分成绩失败！数据没有变化！");
            }else{
                return new Result("1","恭喜您，修改最终评分成绩成功！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","修改最终评分成绩失败！出现未知错误！");
        }

    }
}
