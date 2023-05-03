package com.example.gradeCourse.mapper;


import com.example.gradeCourse.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdminMapper {

    @Select("select * from admin where adminid=#{adminid}")
    public Admin findById(@Param("adminid")String adminid);

    @Update("update admin set name=#{admin.name},telphone=#{admin.telphone},email=#{admin.email},sex=#{admin.sex} where adminid=#{admin.adminid}")
    public int updateAdminProfile(@Param("admin")Admin admin);

    @Select("select userpwd from user where userid=#{userid}")
    public String getAdminPwd(@Param("userid")String adminid);

    @Update("update user set userpwd=#{userpwd} where userid=#{userid}")
    public int updateAdminPwd(@Param("userpwd")String newPwd,@Param("userid")String adminid);
}
