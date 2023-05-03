package com.example.gradeCourse.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Student {

    @Excel(name = "学号",orderNum = "1",width = 20)
    @NotNull(message = "学号不能为空！")
    private String stuid;

    @Excel(name = "姓名",orderNum = "2")
    @NotNull(message = "姓名不能为空！")
    private String sname;




    private String classid;

    @ExcelEntity(id="classid")
    private Class aClass;

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    @Excel(name = "学生性别",orderNum = "4", replace = { "男_0", "女_1" }, suffix = "生")
    private String ssex;

    @Excel(name = "邮箱",orderNum = "5",width = 20)
    private String email;

    @Excel(name = "电话",orderNum = "6",width = 20)
    private String telphone;

    private String remark;

    private String operator;

    private Date operatedtime;

    private String submitStatus;


    private String submitWorkid;

    public String getSubmitWorkid() {
        return submitWorkid;
    }

    public void setSubmitWorkid(String submitWorkid) {
        this.submitWorkid = submitWorkid;
    }


    private String serverHtmlFilePath;

    private boolean duplicate;

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public String getServerHtmlFilePath() {
        return serverHtmlFilePath;
    }

    public void setServerHtmlFilePath(String serverHtmlFilePath) {
        this.serverHtmlFilePath = serverHtmlFilePath;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public String getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(String timeStatus) {
        this.timeStatus = timeStatus;
    }

    private String timeStatus;

    public String getSystemGrade() {
        return systemGrade;
    }

    public void setSystemGrade(String systemGrade) {
        this.systemGrade = systemGrade;
    }

    public String getLastGrade() {
        return lastGrade;
    }

    public void setLastGrade(String lastGrade) {
        this.lastGrade = lastGrade;
    }

    private String systemGrade;

    private String lastGrade;

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid == null ? null : stuid.trim();
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public String getSsex() {
        return ssex;
    }

    public void setSsex(String ssex) {
        this.ssex = ssex == null ? null : ssex.trim();
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOperatedtime() {
        return operatedtime;
    }

    public void setOperatedtime(Date operatedtime) {
        this.operatedtime = operatedtime;
    }
}