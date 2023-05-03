package com.example.gradeCourse.service;


import com.example.gradeCourse.entity.ReleaseWork;
import com.example.gradeCourse.entity.Student;
import com.example.gradeCourse.entity.SubmitWork;
import com.example.gradeCourse.entity.User;
import com.example.gradeCourse.mapper.*;
import com.example.gradeCourse.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {

    @Value("${file-save-path}")
    private String fileSavePath;

    @Value("${avatar-save-path}")
    private String avatarSavePath;

    @Value("${excel-save-path}")
    private String excelSavePath;

    @Autowired
    public StudentMapper studentMapper;

    @Autowired
    public ReleaseWorkMapper releaseWorkMapper;

    @Autowired
    public ClassMapper classMapper;


    @Autowired
    public UserMapper userMapper;

    @Autowired
    public UserService userService;


    @Autowired
    public SubmitWorkMapper submitWorkMapper;

    public Result getMyWork(String stuid){
        try {
            List<ReleaseWork> releaseWorks=studentMapper.getMyWork(stuid);
            if(releaseWorks.size()==0){
                return new Result("1","我的作业获取成功，但是没有数据！",0);
            }else {

                //给我的作业添加状态{未提交，已提交}
                for (int i=0;i<releaseWorks.size();i++){
                    List<SubmitWork> submitWorks=studentMapper.getSubmitWorkByStuidAndWorkid(stuid,releaseWorks.get(i).getWorkid());
                    if(submitWorks.size()==0){
                        releaseWorks.get(i).setSubmitStatus("未提交");
                        if(releaseWorks.get(i).getEndtime().compareTo(new Date())>0){
                            releaseWorks.get(i).setTimeStatus("未超时");
                        }else{
                            releaseWorks.get(i).setTimeStatus("已超时");
                        }
                    }else{
                        releaseWorks.get(i).setSubmitStatus("已提交");
                        if(submitWorks.get(0).getSubmittime().compareTo(releaseWorks.get(i).getEndtime())>0){
                            releaseWorks.get(i).setTimeStatus("已迟交");
                        }else{
                            releaseWorks.get(i).setTimeStatus("已按时提交");
                        }
                    }
                }


                return new Result("1","获取成功！",releaseWorks);
            }
        }catch (Exception e){
            return new Result("0","获取作业失败！");
        }
    }


    public Result updateStudentPwd(String oldPwd,String newPwd,String stuid){
        try{
            PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
            String rawPwd=studentMapper.getUserPwd(stuid);
            if(!passwordEncoder.matches(oldPwd,rawPwd)){
                return new Result("0","修改密码失败,旧密码错误！");
            }else{
                int flag=studentMapper.updateStudentPwd(passwordEncoder.encode(newPwd),stuid );
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


    public Result updateStudentProfile(Student student){
        try{
            int flag=studentMapper.updateStudentProfile(student);
            if(flag==0){
                return new Result("0","修改个人信息失败！");
            }else{
                return new Result("1","修改个人信息成功！");
            }
        }catch (Exception e){
            return new Result("0","修改个人信息失败!!!!");
        }
    }

    public Result getStudentProfile(String stuid){
        try{
            Student student=studentMapper.getStudentProfile(stuid);
            if(student==null){
                return new Result("0","查询学生信息失败！");
            }else{
                return new Result("1","查询学生信息成功！",student);
            }
        }catch (Exception e){
            return new Result("0","查询学生信息失败！!!!");
        }
    }


    public Result getAllStudents() {
        try {
            List<Student> students = studentMapper.findAll();
            if (students.size() == 0) {
                return new Result("0", "查询失败，没有数据！");
            } else {
                return new Result("1", "查询成功！", students);
            }
        } catch (Exception e) {
            return new Result("0", "查询失败!!!!");
        }
    }

    public Result getCurrentPageStudents(Integer pageNum, Integer pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Student> students = studentMapper.findAll();
            PageInfo pageInfo = new PageInfo(students);
            if (students.size() == 0) {
                return new Result("0", "查询失败，没有数据！");
            } else {
                return new Result("1", "查询成功！", pageInfo);
            }
        } catch (Exception e) {
            return new Result("0", "查询失败!!!!");
        }
    }

    public Result saveStudent(Student student) {
        try {
            int flag = studentMapper.saveStudent(student);
            User user=userMapper.find(student.getStuid());
            if(user==null){
                Result result=userService.addStudentUser(student);
                if(result.getCode().equals("0")){
                    return result;
                }
            }
            if (flag == 0) {
                return new Result("0", "添加失败了");
            } else {
                return new Result("1", "添加成功了");
            }
        } catch (Exception e) {
            return new Result("0", "添加失败!!!!");
        }
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    //上传头像
    public Result uploadAvatar(MultipartFile uploadFile,String stuid) {

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

            int flag=userMapper.updateAvatar(avatar,stuid);
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

    //上传作业的压缩包
    public  Result uploadWork(MultipartFile file,HttpServletRequest request,String stuid,String workid){
        String realpath=fileSavePath;
        String format=sdf.format(new Date());
        File file1=new File(realpath+format);
        String filepath="";
        if(!file1.isDirectory()){
            file1.mkdirs();
        }

        String oldname=file.getOriginalFilename();
        if(!oldname.substring(oldname.lastIndexOf("."),oldname.length()).equals(".zip")){
            return new Result("0","上传失败，上传文件类型必须为zip类型");
        }
        String newname=UUID.randomUUID().toString()+oldname.substring(oldname.lastIndexOf("."),oldname.length());
        try {
            file.transferTo(new File(file1,newname));
            filepath=realpath+format+newname;

            String serverPath=request.getScheme() + "://" + request.getServerName() + ":" +
                    request.getServerPort() + "/uploadFile/" + format + newname;

            //对zip压缩包进行解压，并且存放到对应的压缩包文件夹中。
            String htmlFilePath=UnzipUtil.unzipFile(filepath,realpath+format+newname.substring(0,newname.indexOf('.')));
            System.out.println(htmlFilePath);
            //获取前端访问html页面的url
            String serverHtmlFilePath="/api/gradeCourse/uploadFile"+htmlFilePath.substring(htmlFilePath.indexOf("/2023"),htmlFilePath.length());


//            由系统打开html界面，并完成截图
            System.setProperty("webdriver.chrome.driver", "E:/Chrome/chromedriver/chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.setHeadless(true); // 设置为无头模式，不显示浏览器界面
            options.addArguments("--window-size=1920,1080");
            WebDriver driver = new ChromeDriver(options);
            driver.get(htmlFilePath);
            WebDriverUtils utils = new WebDriverUtils(driver);
            String screenshotPath=realpath+format+newname.substring(0,newname.indexOf('.'))+"/screenshot.jpg";
            utils.takeScreenshot(screenshotPath);
            driver.quit();

            //获取参考答案的实例图片
            String examplePath=releaseWorkMapper.findByWorkid(workid).getFilePath();

            //使用图像相似度算法，将学生截图与参考答案进行对比
            double simility=ImageCompareTool.compare(new File(screenshotPath),new File(examplePath));
            DecimalFormat d=new DecimalFormat("#.0");
            String realSimility=d.format(simility*100);

            //利用图像相似度算法，判断是否是雷同卷！
            boolean duplicate=false;
            List<SubmitWork> otherSubmitWorks=submitWorkMapper.getSubmitWorks(workid,stuid);
            if(otherSubmitWorks.size()!=0){
                for (int i=0;i<otherSubmitWorks.size();i++){
                    if (otherSubmitWorks.get(i).getSystemFirstGrade()!=null && String.valueOf(simility).equals(otherSubmitWorks.get(i).getSystemFirstGrade())){
                        duplicate=true;
                        break;
                    }
                }
            }


            //插入提交记录
            int flag=studentMapper.insertSubmitWork(stuid,workid,new Date(),oldname,realSimility,serverHtmlFilePath,String.valueOf(simility),duplicate);
            if(flag==0){
                return new Result("0","作业上传失败！数据插入错误");
            }else{
                return new Result("1","作业上传成功！");
            }

        }catch (Exception e){
            e.printStackTrace();
            return new Result("0","上传作业失败！！！！");

        }




    }
    public Result upload(MultipartFile uploadFile, HttpServletRequest req) {
        String realPath = excelSavePath;
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        String filePath = "";
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        String oldName = uploadFile.getOriginalFilename();
        if(!oldName.substring(oldName.lastIndexOf("."),oldName.length()).equals(".xls") && !oldName.substring(oldName.lastIndexOf("."),oldName.length()).equals(".xlsx")){
            return new Result("0","上传失败，上传文件类型必须为xls、xlsx类型");
        }
        String newName = UUID.randomUUID().toString() +
                oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            uploadFile.transferTo(new File(folder, newName));
            filePath = realPath + format + newName;
            return importStudent(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result("0", "上传信息失败！");
        }

    }

    public void importTemplate(HttpServletResponse response){
        List<Student> students = new ArrayList<>();
        ExcelUtil.exportExcel(students, "学生信息", "sheet1", Student.class, "student.xls", response);
    }


    public void exportStudent(HttpServletResponse response) {

        List<Student> students = studentMapper.findAll2();
        //导出操作
        ExcelUtil.exportExcel(students, "学生信息", "sheet1", Student.class, "student.xls", response);

    }


    public Result importStudent(String filePath) {
        try {
            //解析excel，
            List<Student> students = ExcelUtil.importExcel(filePath, 1, 1, Student.class);
            //也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
            for (int i=0;i<students.size();i++){
                students.get(i).setClassid(classMapper.findByName(students.get(i).getaClass().getClassname()).getClassid());
            }
            System.out.println("导入数据一共【" + students.size() + "】行");
            int flag = studentMapper.saveAllStudents(students);
            for (int i=0;i<students.size();i++){
                Result result=userService.addStudentUser(students.get(i));
                if(result.getCode().equals("0")){
                    return new Result("0", "导入数据失败，未知原因！");
                }
            }
            if (flag == 0) {
                return new Result("0", "导入数据失败，没有数据！");
            } else {
                return new Result("1", "导入数据成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("0", "导入数据失败,学号可能重复！");
        }
    }


    public Result updateStudent(Student student, String stuid) {
        try {
            int flag = studentMapper.updateStudent(student, stuid);
            if (flag == 0) {
                return new Result("0", "修改失败了");
            } else {
                return new Result("1", "修改成功了");
            }
        } catch (Exception e) {
            return new Result("0", "修改失败!!!!");
        }
    }

    public Result deleteStudent(Student student) {
        try {
            int flag = studentMapper.deleteStudent(student);
            User user=userMapper.find(student.getStuid());
            if(user!=null){
                int deleteFlag=userMapper.deleteUser(student.getStuid());
                if (deleteFlag == 0) {
                    return new Result("0", "删除用户失败了");
                }
            }
            if (flag == 0) {
                return new Result("0", "删除失败了");
            } else {
                return new Result("1", "删除成功了");
            }

        } catch (Exception e) {
            return new Result("0", "删除失败!!!!");
        }
    }

    public Result searchStudent(String keyword, Integer pageNum, Integer pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<Student> students = studentMapper.searchStudent(keyword);
            PageInfo pageInfo = new PageInfo(students);
            if (students.size() == 0) {
                return new Result("0", "查询失败，没有数据！");
            } else {
                return new Result("1", "查询成功！", pageInfo);
            }
        } catch (Exception e) {
            return new Result("0", "查询失败!!!!");
        }
    }
}
