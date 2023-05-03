package com.example.gradeCourse.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;

public class Class {
    private String classid;

    @Excel(name = "班级",orderNum = "3")
    @NotNull(message = "班级不能为空！")
    private String classname;

    private Speciality speciality;

    private String enrolyear;

    private String gradyear;

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid == null ? null : classid.trim();
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname == null ? null : classname.trim();
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public String getEnrolyear() {
        return enrolyear;
    }

    public void setEnrolyear(String enrolyear) {
        this.enrolyear = enrolyear == null ? null : enrolyear.trim();
    }

    public String getGradyear() {
        return gradyear;
    }

    public void setGradyear(String gradyear) {
        this.gradyear = gradyear == null ? null : gradyear.trim();
    }
}