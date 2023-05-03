package com.example.gradeCourse.mapper;


import com.example.gradeCourse.entity.Speciality;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SpecialityMapper {

    @Select("select * from speciality")
    @Results({
            @Result(property = "teacher",column = "specmagtid",
                    one = @One(select = "com.example.gradeCourse.mapper.TeacherMapper.findById"))
    })
    public List<Speciality> findAll();

    @Select("select * from speciality where specid=#{specid}")
    @Results({
            @Result(property = "teacher",column = "specmagtid",
                    one = @One(select = "com.example.gradeCourse.mapper.TeacherMapper.findById"))
    })
    public Speciality findById(@Param("specid")String specid);


    @Insert("insert into speciality set specid=#{speciality.specid},specname=#{speciality.specname},specmagtid=#{speciality.teacher.tid}")
    public int saveSpeciality(@Param("speciality")Speciality speciality);


    @Update("update speciality set specid=#{speciality.specid},specname=#{speciality.specname},specmagtid=#{speciality.teacher.tid} where specid=#{specid}")
    public int updateSpeciality(@Param("speciality")Speciality speciality,@Param("specid")String specid);


    @Delete("delete from speciality where specid=#{spec.specid}")
    public int deleteSpeciality(@Param("spec")Speciality speciality);

    @Select("select * from speciality,teacher where speciality.specmagtid=teacher.tid and (speciality.specid like concat('%',#{keyword},'%') or speciality.specname like concat('%',#{keyword},'%') or teacher.tname like concat('%',#{keyword},'%')) ")
    @Results({
            @Result(property = "teacher",column = "specmagtid",
                    one = @One(select = "com.example.gradeCourse.mapper.TeacherMapper.findById"))
    })
    public List<Speciality> searchSpeciality(@Param("keyword")String keyword);
}
