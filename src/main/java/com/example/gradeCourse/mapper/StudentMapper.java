package com.example.gradeCourse.mapper;


import com.example.gradeCourse.entity.ReleaseWork;
import com.example.gradeCourse.entity.Student;
import com.example.gradeCourse.entity.SubmitWork;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface StudentMapper {


    @Select("select releasework.workid,releasework.workname,releasework.workrequirement,releasework.starttime,releasework.endtime,releasework.tid,releasework.allowLateSubmission,releasework.relevantMaterial from releasework,student where releasework.classid=student.classid and student.stuid=#{stuid}")
    @Results({
            @Result(property = "aClass",column = "classid",
                    one = @One(select = "com.example.gradeCourse.mapper.ClassMapper.findById")),
            @Result(property = "teacher",column = "tid",
            one=@One(select = "com.example.gradeCourse.mapper.TeacherMapper.findById"))
    })
    public List<ReleaseWork> getMyWork(@Param("stuid")String stuid);

    @Select("select userpwd from user where userid=#{userid}")
    public String getUserPwd(@Param("userid")String stuid);

    @Update("update user set userpwd=#{userpwd} where userid=#{userid}")
    public int updateStudentPwd(@Param("userpwd")String newPwd,@Param("userid")String stuid);


    @Update("update student set sname=#{student.sname},ssex=#{student.ssex},telphone=#{student.telphone},email=#{student.email} where stuid=#{student.stuid}")
    public int updateStudentProfile(@Param("student")Student student);

    @Select("select * from student where stuid=#{stuid}")
    @Results({
            @Result(property = "aClass",column = "classid",
                    one = @One(select = "com.example.gradeCourse.mapper.ClassMapper.findById"))
    })
    public Student getStudentProfile(@Param("stuid")String stuid);





    @Select("select * from student")
    @Results({
            @Result(property = "aClass",column = "classid",
                    one = @One(select = "com.example.gradeCourse.mapper.ClassMapper.findById"))
    })
    public List<Student> findAll();

    @Select("select * from student")
    @Results({
            @Result(property = "aClass",column = "classid",
                    one = @One(select = "com.example.gradeCourse.mapper.ClassMapper.findById")),
            @Result(property = "classid",column = "classid")
    })
    public List<Student> findAll2();


    @Select("select * from student where stuid=#{stuid}")
    @Results({
            @Result(property = "aClass",column = "classid",
                    one = @One(select = "com.example.gradeCourse.mapper.ClassMapper.findById"))
    })
    public List<Student> findByid(@Param("stuid")String stuid);


    @Insert("insert into student set stuid=#{student.stuid},sname=#{student.sname},classid=#{student.aClass.classid},telphone=#{student.telphone},email=#{student.email},ssex=#{student.ssex}")
    public int saveStudent(@Param("student")Student student);


    @Insert({"<script>",
            "insert into student(stuid, sname,classid,ssex,email,telphone) values ",
            "<foreach collection='students' item='student' separator=','>",
            "(#{student.stuid}, #{student.sname},#{student.classid},#{student.ssex},#{student.email},#{student.telphone})",
            "</foreach>",
            "</script>"})
    public int saveAllStudents(@Param("students") List<Student> students);


    @Update("update student set stuid=#{student.stuid},sname=#{student.sname},classid=#{student.aClass.classid},telphone=#{student.telphone},email=#{student.email},ssex=#{student.ssex} where stuid=#{rawid}")
    public  int updateStudent(@Param("student")Student student,@Param("rawid")String rawid);


    @Delete("delete from student where stuid=#{student.stuid}")
    public  int deleteStudent(@Param("student")Student student);


    @Select("select * from student s,class c where s.classid=c.classid and (s.stuid like concat('%',#{keyword},'%') or s.sname like concat('%',#{keyword},'%') or c.classname like concat('%',#{keyword},'%'))")
    @Results({
            @Result(property = "aClass",column = "classid",
                    one = @One(select = "com.example.gradeCourse.mapper.ClassMapper.findById"))
    })
    public List<Student> searchStudent(@Param("keyword")String keyword);

    //插入提交作业的一条记录
    @Insert("insert into submitwork set stuid=#{stuid},workid=#{workid},submittime=#{submittime},workrename=#{oldname},systemGrade=#{systemGrade},serverHtmlFilePath=#{serverHtmlFilePath},systemFirstGrade=#{systemFirstGrade},duplicate=#{duplicate}")
    public int insertSubmitWork(@Param("stuid")String stuid, @Param("workid")String workid, @Param("submittime")Date date,@Param("oldname")String oldname,@Param("systemGrade")String systemGrade,@Param("serverHtmlFilePath")String serverHtmlFilePath,@Param("systemFirstGrade")String systemFirstGrade,@Param("duplicate")boolean duplicate);

    //查询提交作业的记录
    @Select("select * from submitwork where stuid=#{stuid} and workid=#{workid}")
    public List<SubmitWork> getSubmitWorkByStuidAndWorkid(@Param("stuid")String stuid,@Param("workid")String workid);

    //根据班级查找所有学生
    @Select("select * from student where classid=#{classid}")
    @Results({
            @Result(property = "aClass",column = "classid",
                    one = @One(select = "com.example.gradeCourse.mapper.ClassMapper.findById"))
    })
    public List<Student> getStudentByClassid(@Param("classid")String classid);

}
