package com.example.gradeCourse.mapper;


import com.example.gradeCourse.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoleMapper {

    @Select("select * from role where name=#{name}")
    public List<Role> selectRoleByName(@Param("name")String name);

    @Select("select * from role where id=(select roleid from user_role where userid=#{id})")
    public List<Role> selectRoleById(@Param("id")String id);
}
