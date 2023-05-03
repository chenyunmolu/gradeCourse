package com.example.gradeCourse.entity;

import java.util.Date;

public class Syscode {
    private Long codeid;

    private String codeno;

    private String codename;

    private String codevalue;

    private String codecontent;

    private String operator;

    private Date operatedtime;

    public Long getCodeid() {
        return codeid;
    }

    public void setCodeid(Long codeid) {
        this.codeid = codeid;
    }

    public String getCodeno() {
        return codeno;
    }

    public void setCodeno(String codeno) {
        this.codeno = codeno == null ? null : codeno.trim();
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename == null ? null : codename.trim();
    }

    public String getCodevalue() {
        return codevalue;
    }

    public void setCodevalue(String codevalue) {
        this.codevalue = codevalue == null ? null : codevalue.trim();
    }

    public String getCodecontent() {
        return codecontent;
    }

    public void setCodecontent(String codecontent) {
        this.codecontent = codecontent == null ? null : codecontent.trim();
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