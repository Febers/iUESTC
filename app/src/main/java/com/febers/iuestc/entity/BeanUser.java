package com.febers.iuestc.entity;

public class BeanUser {
    private String ChineseName = "";
    private String EnglishName;
    private String id = "";
    private String sex;
    private String grade;   //年级
    private String studyYear;   //学制
    private String type;    //学生类别
    private String school;  //学院
    private String major;   //专业
    private String intoTime;    //入校时间
    private String outTime; //离校时间
    private String userClass;   //行政班级
    private String position;    //校区
    private String phone;   //移动电话
    private String address; //联系地址

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getChineseName() {
        return ChineseName;
    }

    public void setChineseName(String chineseName) {
        this.ChineseName = chineseName;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        this.EnglishName = englishName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(String studyYear) {
        this.studyYear = studyYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getIntoTime() {
        return intoTime;
    }

    public void setIntoTime(String intoTime) {
        this.intoTime = intoTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
