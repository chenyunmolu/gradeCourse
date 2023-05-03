package com.example.gradeCourse.entity;

import java.util.Date;

public class SubmitWork {
    private String id;

    private String stuid;

    private String workid;

    private Date submittime;

    private String workrename;

    private String systemGrade;

    private String serverHtmlFilePath;

    private String systemFirstGrade;

    private boolean duplicate;

    public String getSystemFirstGrade() {
        return systemFirstGrade;
    }

    public void setSystemFirstGrade(String systemFirstGrade) {
        this.systemFirstGrade = systemFirstGrade;
    }

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

    public String getWorkrename() {
        return workrename;
    }

    public void setWorkrename(String workrename) {
        this.workrename = workrename;
    }

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

    private String lastGrade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid == null ? null : stuid.trim();
    }

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid == null ? null : workid.trim();
    }

    public Date getSubmittime() {
        return submittime;
    }

    public void setSubmittime(Date submittime) {
        this.submittime = submittime;
    }

    @Override
    public String toString() {
        return "SubmitWork{" +
                "id='" + id + '\'' +
                ", stuid='" + stuid + '\'' +
                ", workid='" + workid + '\'' +
                ", submittime=" + submittime +
                ", workrename='" + workrename + '\'' +
                ", systemGrade='" + systemGrade + '\'' +
                ", serverHtmlFilePath='" + serverHtmlFilePath + '\'' +
                ", systemFirstGrade='" + systemFirstGrade + '\'' +
                ", duplicate=" + duplicate +
                ", lastGrade='" + lastGrade + '\'' +
                '}';
    }
}