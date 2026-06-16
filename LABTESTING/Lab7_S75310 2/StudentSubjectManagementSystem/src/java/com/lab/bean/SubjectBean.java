package com.lab.bean;

import java.io.Serializable;

public class SubjectBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int    id;
    private String matricNo;
    private String subjectCode;
    private String subjectName;

    public SubjectBean() {}

    public int    getId()                      { return id; }
    public void   setId(int id)                { this.id = id; }

    public String getMatricNo()                { return matricNo; }
    public void   setMatricNo(String v)        { this.matricNo = v; }

    public String getSubjectCode()             { return subjectCode; }
    public void   setSubjectCode(String v)     { this.subjectCode = v; }

    public String getSubjectName()             { return subjectName; }
    public void   setSubjectName(String v)     { this.subjectName = v; }
}