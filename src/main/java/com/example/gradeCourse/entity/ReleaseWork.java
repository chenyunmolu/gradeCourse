package com.example.gradeCourse.entity;


import java.util.Date;

public class ReleaseWork {
    private String workid;

    private String workname;

    private String workrequirement;

    private Date starttime;

    private Date endtime;

    private String tid;

    private Teacher teacher;

    private String submitStatus;



    private String timeStatus;

    private String allowLateSubmission;

    private String filePath;



    private String oldFileName;

    private String serverFilePath;

    private String relevantMaterial;

    public String getRelevantMaterial() {
        return relevantMaterial;
    }

    public void setRelevantMaterial(String relevantMaterial) {
        this.relevantMaterial = relevantMaterial;
    }

    public String getServerFilePath() {
        return serverFilePath;
    }

    public void setServerFilePath(String serverFilePath) {
        this.serverFilePath = serverFilePath;
    }

    public String getOldFileName() {
        return oldFileName;
    }

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAllowLateSubmission() {
        return allowLateSubmission;
    }

    public void setAllowLateSubmission(String allowLateSubmission) {
        this.allowLateSubmission = allowLateSubmission;
    }

    public String getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(String timeStatus) {
        this.timeStatus = timeStatus;
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    private String classid;

    private Class aClass;

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid == null ? null : workid.trim();
    }

    public String getWorkname() {
        return workname;
    }

    public void setWorkname(String workname) {
        this.workname = workname == null ? null : workname.trim();
    }

    public String getWorkrequirement() {
        return workrequirement;
    }

    public void setWorkrequirement(String workrequirement) {
        this.workrequirement = workrequirement == null ? null : workrequirement.trim();
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }
}