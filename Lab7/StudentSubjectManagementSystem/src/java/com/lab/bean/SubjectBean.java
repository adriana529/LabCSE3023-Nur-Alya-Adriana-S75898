package com.lab.bean;

import java.io.Serializable;

public class SubjectBean implements Serializable {
    private String subjectCode;
    private String subjectName;
    private String matricNo;

    public SubjectBean() {}

    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getMatricNo() { return matricNo; }
    public void setMatricNo(String matricNo) { this.matricNo = matricNo; }
}