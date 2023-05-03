package com.example.gradeCourse.mapper;


import com.example.gradeCourse.entity.ReleaseWork;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ReleaseWorkMapper {

    @Select("select * from releasework")
    public List<ReleaseWork> findAll();

    @Select("select * from releasework where workid=#{workid}")
    public ReleaseWork findByWorkid(@Param("workid")String workid);

    @Select("select * from releasework where tid=#{tid}")
    @Results({
            @Result(property = "aClass",column = "classid",
                    one = @One(select = "com.example.gradeCourse.mapper.ClassMapper.findById")),
            @Result(property = "classid",column = "classid"),
            @Result(property = "teacher",column = "tid",
                    one = @One(select = "com.example.gradeCourse.mapper.TeacherMapper.findById")),
            @Result(property = "tid",column = "tid"),
    })
    public List<ReleaseWork> findByTid(@Param("tid")String tid);


    @Insert("insert into releasework set workname=#{work.workname},workrequirement=#{work.workrequirement},starttime=#{work.starttime},endtime=#{work.endtime},tid=#{work.tid},classid=#{work.aClass.classid},allowLateSubmission=#{work.allowLateSubmission},filepath=#{work.filePath},oldFileName=#{work.oldFileName},serverFilePath=#{work.serverFilePath}")
    public int saveWork(@Param("work")ReleaseWork releaseWork);


    @Update("update releasework set workname=#{work.workname},workrequirement=#{work.workrequirement},starttime=#{work.starttime},endtime=#{work.endtime},classid=#{work.aClass.classid},allowLateSubmission=#{work.allowLateSubmission},filepath=#{work.filePath},oldFileName=#{work.oldFileName},serverFilePath=#{work.serverFilePath} where workid=#{work.workid}")
    public int updateWork(@Param("work")ReleaseWork releaseWork);



    @Delete("delete from releasework where workid=#{work.workid}")
    public int deleteWork(@Param("work")ReleaseWork releaseWork);

    @Select("select * from releasework,class where releasework.classid=class.classid and tid=#{tid} and (workname like concat('%',#{keyword},'%') or workrequirement like concat('%',#{keyword},'%') or class.classname like concat('%',#{keyword},'%'))")
    @Results({
            @Result(property = "aClass",column = "classid",
                    one = @One(select = "com.example.gradeCourse.mapper.ClassMapper.findById"))
    })
    public List<ReleaseWork> searchWork(@Param("keyword")String keyword,@Param("tid")String tid);

    @Update("update releasework set relevantMaterial=#{relevantMaterial} where workid=#{workid}")
    public int updateRelevantMaterial(@Param("relevantMaterial")String relevantMaterial,@Param("workid")String workid);



}
