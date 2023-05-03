package com.example.gradeCourse.mapper;


import com.example.gradeCourse.entity.Syscode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SyscodeMapper {

    //课题类型
    @Select("select codecontent from syscode where codeno='ktlx' and codevalue=#{codevalue}")
    public String findByktlx(@Param("codevalue")String codevalue);

    //课题性质
    @Select("select codecontent from syscode where codeno='ktxz' and codevalue=#{codevalue}")
    public String findByktxz(@Param("codevalue")String codevalue);

    //课题来源
    @Select("select codecontent from syscode where codeno='ktly' and codevalue=#{codevalue}")
    public String findByktly(@Param("codevalue")String codevalue);

    //课题状态
    @Select("select codecontent from syscode where codeno='ktzht' and codevalue=#{codevalue}")
    public String findByktzht(@Param("codevalue")String codevalue);

    //课题方向
    @Select("select codecontent from syscode where codeno='ktfx' and codevalue=#{codevalue}")
    public String findByktfx(@Param("codevalue")String codevalue);

    //课题类别
    @Select("select codecontent from syscode where codeno='ktlb' and codevalue=#{codevalue}")
    public String findByktlb(@Param("codevalue")String codevalue);



    //学生状态
    @Select("select codecontent from syscode where codeno='xshzht' and codevalue=#{codevalue}")
    public String findByxshzht(@Param("codevalue")String codevalue);


    @Select("select codecontent from syscode where codeno='jxdw' and codevalue=#{codevalue}")
    public String findByjxdw(@Param("codevalue") String codevalue);


    @Select("select codecontent from syscode where codeno='zhch' and codevalue=#{codevalue}")
    public String findByzhch(@Param("codevalue") String codevalue);

    @Select("select codecontent from syscode where codeno='xw' and codevalue=#{codevalue}")
    public String findBYxw(@Param("codevalue") String codevalue);

    @Select("select * from syscode where codeno=#{codeno}")
    public List<Syscode> findBycodeno(@Param("codeno")String codeno);

}
