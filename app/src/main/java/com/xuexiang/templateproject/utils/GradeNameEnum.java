package com.xuexiang.templateproject.utils;

public enum GradeNameEnum {
    WL1("物联网1班","0101"),
    WL2("物联网2班","0102")
    ;


    private String gradeName;
    private String classCode;


    GradeNameEnum(String gradeName, String classCode) {
        this.gradeName = gradeName;
        this.classCode = classCode;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
}
