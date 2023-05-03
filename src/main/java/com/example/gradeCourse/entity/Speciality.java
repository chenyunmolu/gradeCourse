package com.example.gradeCourse.entity;

public class Speciality {
    private String specid;

    private String specname;

    private Teacher teacher;

    public String getSpecid() {
        return specid;
    }

    public void setSpecid(String specid) {
        this.specid = specid == null ? null : specid.trim();
    }

    public String getSpecname() {
        return specname;
    }

    public void setSpecname(String specname) {
        this.specname = specname == null ? null : specname.trim();
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}