package com.example.gradeCourse.mapper;


import com.example.gradeCourse.entity.Teacher;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TeacherMapper {

    @Update("update teacher set tname=#{teacher.tname},tsex=#{teacher.tsex},telphone=#{teacher.telphone},email=#{teacher.email} where tid=#{teacher.tid}")
    public int updateTeacherProfile(@Param("teacher")Teacher teacher);


    @Select("select * from teacher where tid=#{tid}")
    @Results({
            @Result(property = "tdept",column = "tdept",
                    one = @One(select = "com.example.gradeCourse.mapper.SyscodeMapper.findByjxdw")),
            @Result(property = "tpost",column = "tpost",
                    one = @One(select = "com.example.gradeCourse.mapper.SyscodeMapper.findByzhch")),
            @Result(property = "tdegree",column = "tdegree",
                    one = @One(select = "com.example.gradeCourse.mapper.SyscodeMapper.findBYxw"))
    })
    public Teacher findById(@Param("tid") String tid);


    @Select("select * from teacher t,syscode s,syscode s1,syscode s2 WHERE t.tdept=s.codevalue  AND t.tpost=s1.codevalue AND t.tdegree=s2.codevalue AND s1.codeno='zhch' AND s2.codeno='xw' AND s.codeno='jxdw' and (t.tid like concat('%',#{keyword},'%') or t.tname like concat('%',#{keyword},'%') or s.codecontent like concat('%',#{keyword},'%') or s1.codecontent like concat('%',#{keyword},'%') or s2.codecontent like concat('%',#{keyword},'%') )")
    @Results({
            @Result(property = "tdept",column = "tdept",
                    one = @One(select = "com.example.gradeCourse.mapper.SyscodeMapper.findByjxdw")),
            @Result(property = "tpost",column = "tpost",
                    one = @One(select = "com.example.gradeCourse.mapper.SyscodeMapper.findByzhch")),
            @Result(property = "tdegree",column = "tdegree",
                    one = @One(select = "com.example.gradeCourse.mapper.SyscodeMapper.findBYxw"))
    })
    public List<Teacher> searchTeacher(@Param("keyword") String keyword);


    @Select("select * from teacher")
    @Results({
            @Result(property = "tdept",column = "tdept",
                    one = @One(select = "com.example.gradeCourse.mapper.SyscodeMapper.findByjxdw")),
            @Result(property = "tpost",column = "tpost",
                    one = @One(select = "com.example.gradeCourse.mapper.SyscodeMapper.findByzhch")),
            @Result(property = "tdegree",column = "tdegree",
                    one = @One(select = "com.example.gradeCourse.mapper.SyscodeMapper.findBYxw"))
    })
    public List<Teacher> getAllTeachers();


    @Insert("insert into teacher set tid=#{teacher.tid},tname=#{teacher.tname},tdept=#{teacher.tdept},tpost=#{teacher.tpost},tdegree=#{teacher.tdegree}")
    public int saveTeacher(@Param("teacher")Teacher teacher);

    @Delete("delete from teacher where tid=#{teacher.tid}")
    public int deleteTeacher(@Param("teacher") Teacher teacher);

    @Update("update teacher set tid=#{teacher.tid},tname=#{teacher.tname},tdept=#{teacher.tdept},tpost=#{teacher.tpost},tdegree=#{teacher.tdegree} where tid=#{rawid}")
    public int updateTeacher(@Param("teacher")Teacher teacher,@Param("rawid")String rawid);

    @Select("select userpwd from user where userid=#{userid}")
    public String getUserPwd(@Param("userid")String tid);

    @Update("update user set userpwd=#{userpwd} where userid=#{userid}")
    public int updateTeacherPwd(@Param("userpwd")String newPwd,@Param("userid")String tid);



}
