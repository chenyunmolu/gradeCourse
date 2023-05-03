package com.example.gradeCourse.mapper;


import com.example.gradeCourse.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Select("select * from user where userid=#{userid}")
    public User find(String userid);

    @Select("select * from user")
    public List<User> findAll();


    @Select("select r.id, r.name from tb_role r left join user_role ur on r.id = ur.role_id where ur.user_id = #{userId}")
    public List<Role> selectUserRoles(@Param("userId")String userId);

    @Insert("INSERT INTO user(userid, username, userpwd,usertype,avatar) VALUES(#{userId}, #{realName}, #{password},#{usertype},#{avatar})")
    public int insertUser(@Param("userId")String userId,@Param("realName")String realName,@Param("password")String password,@Param("usertype")String usertype,@Param("avatar")String avatar);


    @Insert({"<script>",
            "insert into user_role(userid, roleid) values ",
            "<foreach collection='roleIds' item='roleId' separator=','>",
            "(#{userId}, #{roleId})",
            "</foreach>",
            "</script>"})
    public int insertUserRoles(@Param("userId")String userId,@Param("roleIds")List<String> roleIds);

    @Select("select * from user where username=#{userName}")
    public User loadUserByUsername(@Param("userName")String userName);

    @Update("update user set avatar=#{avatar} where userid=#{userid}")
    public int updateAvatar(@Param("avatar")String avatar,@Param("userid")String userid);

    @Delete("delete from user where userid=#{userid}")
    public int deleteUser(@Param("userid")String userid);
}
