package com.example.gradeCourse.mapper;

import com.example.gradeCourse.entity.Class;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ClassMapper {

    @Select("select * from class")
    @Results({
            @Result(property = "speciality",column = "specid",
                    one = @One(select = "com.example.gradeCourse.mapper.SpecialityMapper.findById"))
    })
    public List<Class> findAll();

    @Select("select * from class where classid=#{classid}")
    @Results({
            @Result(property = "speciality",column = "specid",
                    one = @One(select = "com.example.gradeCourse.mapper.SpecialityMapper.findById"))
    })
    public Class findById(@Param("classid")String classid);

    @Select("select * from class where classname=#{classname}")
    public Class findByName(@Param("classname")String classname);


    @Insert("insert into class set classid=#{aClass.classid},classname=#{aClass.classname},specid=#{aClass.speciality.specid},enrolyear=#{aClass.enrolyear},gradyear=#{aClass.gradyear}")
    public int saveClass(@Param("aClass")Class aClass);

    @Update("update class set classid=#{aClass.classid},classname=#{aClass.classname},specid=#{aClass.speciality.specid},enrolyear=#{aClass.enrolyear},gradyear=#{aClass.gradyear} where classid=#{rawid}")
    public int updateClass(@Param("aClass")Class aClass,@Param("rawid")String rawid);

    @Delete("delete from class where classid=#{aClass.classid}")
    public int deleteClass(@Param("aClass")Class aClass);



    @Select("select * from class,speciality where class.specid=speciality.specid and  (class.classid like concat('%',#{keyword},'%') or class.classname like concat('%',#{keyword},'%') or class.enrolyear like concat('%',#{keyword},'%') or class.gradyear like concat('%',#{keyword},'%') or speciality.specname like concat('%',#{keyword},'%'))")
    @Results({
            @Result( id = true,property = "classid",column = "classid"),
            @Result(property = "classname",column = "classname"),
            @Result(property = "speciality",column = "specid",javaType = Class.class,
                    one = @One(select = "com.example.gradeCourse.mapper.SpecialityMapper.findById"))
    })
    public List<Class> searchClass(@Param("keyword")String keyword);
}
