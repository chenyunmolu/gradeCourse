package com.example.gradeCourse.service;


import com.example.gradeCourse.entity.*;
import com.example.gradeCourse.mapper.*;
import com.example.gradeCourse.util.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TeacherService {

    @Value("${img-save-path}")
    private String imgSavePath;

    @Value("${avatar-save-path}")
    private String avatarSavePath;

    @Autowired
    public TeacherMapper teacherMapper;

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public UserService userService;

    @Autowired
    public StudentMapper studentMapper;

    @Autowired
    public ReleaseWorkMapper releaseWorkMapper;


    public Result updateTeacherPwd(String oldPwd,String newPwd,String tid){
        try{
            PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
            String rawPwd=teacherMapper.getUserPwd(tid);
            if(!passwordEncoder.matches(oldPwd,rawPwd)){
                return new Result("0","修改密码失败,旧密码错误！");
            }else{
                int flag=teacherMapper.updateTeacherPwd(passwordEncoder.encode(newPwd),tid );
                if(flag==0){
                    return new Result("0","修改密码失败,旧密码是正确的");
                }else{
                    return new Result("1","修改密码成功！");
                }

            }

        }catch (Exception e){
            return new Result("0","修改密码失败!!!!");
        }
    }

    public Result updateTeacherProfile(Teacher teacher){
        try {
            int flag=teacherMapper.updateTeacherProfile(teacher);
            if(flag==0){
                return new Result("0","抱歉，修改教师个人信息失败！");
            }else {
                return new Result("1","恭喜，修改教师个人信息成功！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","抱歉，修改教师个人信息失败！！！！！！");
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    //上传头像
    public Result uploadAvatar(MultipartFile uploadFile,String tid) {

        String realPath = avatarSavePath;
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        String newName="";
        if(uploadFile.getContentType().equals("image/png")){
            newName=UUID.randomUUID().toString() +".png";;
        }else{
            newName=UUID.randomUUID().toString() +".jpg";;
        }
        System.out.println(newName);
        try {
            uploadFile.transferTo(new File(folder, newName));
            String avatar="/api/gradeCourse/avatar/"+format+newName;
            System.out.println(avatar);

            int flag=userMapper.updateAvatar(avatar,tid);
            if(flag==0){
                return new Result("0", "上传头像失败！");
            }else {
                return new Result("1", "上传头像成功！",avatar);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new Result("0", "上传头像失败！！！！！！！");
        }

    }


    //获取教师的个人信息
    public Result getTeacherProfile(String tid){
        try {
            Teacher teacher=teacherMapper.findById(tid);
            if (teacher==null){
                return new Result("0","获取教师的个人信息失败！没有该教师！");
            }else{
                return new Result("1","获取教师个人信息成功！",teacher);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","获取教师的个人信息失败！！！！！！！！");
        }
    }

    //根据班级获取所有学生信息(分页处理)
    public Result getCurrentPageStudentByClassid(Integer pageNum, Integer pageSize, String classid,String workid){
        try{
            PageHelper.startPage(pageNum,pageSize);
            List<Student> students=studentMapper.getStudentByClassid(classid);

            if(students.size()==0){
                return new Result("0","获取学生失败！没有对应数据！");
            }else{
                //给学生添加该作业的提交状态
                for (int i=0;i<students.size();i++){
                    List<SubmitWork> submitWorks=studentMapper.getSubmitWorkByStuidAndWorkid(students.get(i).getStuid(),workid);
                    ReleaseWork releaseWork=releaseWorkMapper.findByWorkid(workid);
                    if(submitWorks.size()>0){
                        students.get(i).setSubmitStatus("已提交");
                        //在学生的多次提交中找到最后一次的那次提交记录，获取相关数据！
                        SubmitWork submitWork=submitWorks.get(submitWorks.size()-1);

                        students.get(i).setSystemGrade(submitWork.getSystemGrade());
                        if(submitWork.getLastGrade()!=null){
                            students.get(i).setLastGrade(submitWork.getLastGrade());
                        }
                        students.get(i).setServerHtmlFilePath(submitWork.getServerHtmlFilePath());
                        students.get(i).setSubmitWorkid(submitWork.getId());
                        if(submitWorks.get(0).getSubmittime().compareTo(releaseWork.getEndtime())>0){
                            students.get(i).setTimeStatus("已迟交");
                        }else{
                            students.get(i).setTimeStatus("已按时提交");
                        }
                        students.get(i).setDuplicate(submitWork.isDuplicate());

                    }else{
                        students.get(i).setSubmitStatus("未提交");
                        students.get(i).setSystemGrade("0");
                        students.get(i).setLastGrade("0");
                        students.get(i).setServerHtmlFilePath("#");
                        students.get(i).setDuplicate(false);

                        if((new Date()).compareTo(releaseWork.getEndtime())>0){
                            students.get(i).setTimeStatus("已超时");
                        }else{
                            students.get(i).setTimeStatus("未超时");
                        }
                    }
                }

                PageInfo pageInfo=new PageInfo(students);
                return new Result("1","获取学生成功！",pageInfo);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","获取学生失败!!!!!!!!!");
        }
    }


    //得到的是分页处理过的数据
    public Result getAllTeachers(Integer pageNum,Integer pageSize){
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Teacher> teacher=teacherMapper.getAllTeachers();
            PageInfo pageInfo=new PageInfo(teacher);
            if (teacher.size()==0){
                return new Result("0","查询失败，没数据");
            }
            return  new Result("1","查询成功！",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","查询失败!");
        }
    }


    //得到的是所有的原始数据，即没有被分页的数据。
    public Result getAllPureTeachers(){
        try {
            List<Teacher> teacher=teacherMapper.getAllTeachers();
            if (teacher.size()==0){
                return new Result("0","查询失败，没数据");
            }
            return  new Result("1","查询成功！",teacher);
        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","查询失败!");
        }
    }

    public Result saveTeacher(Teacher teacher){
        try{
            int flag=teacherMapper.saveTeacher(teacher);
            User user=userMapper.find(teacher.getTid());
            if(user==null){
                Result result=userService.addTeacherUser(teacher);
                if(result.getCode().equals("0")){
                    return result;
                }
            }
            if (flag==0){
                return new Result("0","添加失败");
            }else{
                return new Result("1","添加成功！");
            }

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","添加失败");
        }
    }


    public Result deleteTeacher(Teacher teacher){
        try{
            int flag=teacherMapper.deleteTeacher(teacher);
            User user=userMapper.find(teacher.getTid());
            if(user!=null){
                int deleteFlag=userMapper.deleteUser(teacher.getTid());
                if (deleteFlag==0){
                    return new Result("0","删除用户失败");
                }
            }
            if (flag==0){
                return new Result("0","删除失败");
            }else{
                return new Result("1","删除成功！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","删除失败！");
        }
    }

    public Result updateTeacher(Teacher teacher,String rawid){
        try{
            int flag=teacherMapper.updateTeacher(teacher,rawid);
            if (flag==0){
                return new Result("0","修改失败");
            }else{
                return new Result("1","修改成功！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","修改失败！");
        }
    }
    public Result searchTeacher(String keyword,Integer pageNum,Integer pageSize){
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Teacher> teachers=teacherMapper.searchTeacher(keyword);
            PageInfo pageInfo=new PageInfo(teachers);
            if (teachers.size()==0){
                return new Result("0","查询失败，没数据哦");

            }
            return  new Result("1","查询成功！",pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","查询失败!");
        }
    }


}
