package com.example.gradeCourse.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Teacher {
    private String tid;

    private String tname;

    private String tsex;

    private String tdept;

    private String tpost;

    private String tdegree;

    private String studydirect;

    private String email;

    private String telphone;

    private String remark;

    private String operator;

    private Date operatedtime;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public String getTsex() {
        return tsex;
    }

    public void setTsex(String tsex) {
        this.tsex = tsex == null ? null : tsex.trim();
    }

    public String getTdept() {
        return tdept;
    }

    public void setTdept(String tdept) {
        this.tdept = tdept == null ? null : tdept.trim();
    }

    public String getTpost() {
        return tpost;
    }

    public void setTpost(String tpost) {
        this.tpost = tpost == null ? null : tpost.trim();
    }

    public String getTdegree() {
        return tdegree;
    }

    public void setTdegree(String tdegree) {
        this.tdegree = tdegree == null ? null : tdegree.trim();
    }

    public String getStudydirect() {
        return studydirect;
    }

    public void setStudydirect(String studydirect) {
        this.studydirect = studydirect == null ? null : studydirect.trim();
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