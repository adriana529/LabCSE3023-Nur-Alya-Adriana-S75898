package com.lab.bean;

import java.io.Serializable;

public class StudentBean implements Serializable {

    private String matricNo;
    private String fullName;
    private String password;
    private String base64Image;

    public StudentBean() {
    }

    public String getMatricNo() {
        return matricNo;
    }

    public void setMatricNo(String matricNo) {
        this.matricNo = matricNo;
    }

    public String getFullName() {
        return fullName;
    }

    // ✅ FIXED: Parameter is renamed to 'fullName' to match assignment perfectly
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }
}