package com.example.gradeCourse.mapper;


import com.example.gradeCourse.entity.SubmitWork;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
@Mapper
public interface SubmitWorkMapper {

    @Update("update submitwork set lastGrade=#{submitWork.lastGrade} where id=#{submitWork.id}")
    public int updateSubmitWork(@Param("submitWork")SubmitWork submitWork);

    @Select("select * from submitwork where workid=#{workid} and stuid!=#{stuid}")
    public List<SubmitWork> getSubmitWorks(@Param("workid")String workid,@Param("stuid")String stuid);
}
