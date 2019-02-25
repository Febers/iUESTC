package com.febers.iuestc.entity2;

import com.google.gson.annotations.SerializedName;

public class UserBean {

    private int code;
    private DataBean data;
    private String time;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {

        private String stuID;
        private String stuName;
        private String enName;
        private String gender;
        private String grade;
        private String plan;
        private String project;
        private String level;
        private String category;
        private String department;
        private String profession;
        private String direction;
        private String enrollDate;
        private String graduateDate;
        private String manager;
        private String waysOfLearning;
        private String eduForm;
        private String status;
        private String registered;
        private String atSchool;
        @SerializedName("class")
        private String classX;
        private String campus;

        public String getStuID() {
            return stuID;
        }

        public void setStuID(String stuID) {
            this.stuID = stuID;
        }

        public String getStuName() {
            return stuName;
        }

        public void setStuName(String stuName) {
            this.stuName = stuName;
        }

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getPlan() {
            return plan;
        }

        public void setPlan(String plan) {
            this.plan = plan;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getEnrollDate() {
            return enrollDate;
        }

        public void setEnrollDate(String enrollDate) {
            this.enrollDate = enrollDate;
        }

        public String getGraduateDate() {
            return graduateDate;
        }

        public void setGraduateDate(String graduateDate) {
            this.graduateDate = graduateDate;
        }

        public String getManager() {
            return manager;
        }

        public void setManager(String manager) {
            this.manager = manager;
        }

        public String getWaysOfLearning() {
            return waysOfLearning;
        }

        public void setWaysOfLearning(String waysOfLearning) {
            this.waysOfLearning = waysOfLearning;
        }

        public String getEduForm() {
            return eduForm;
        }

        public void setEduForm(String eduForm) {
            this.eduForm = eduForm;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRegistered() {
            return registered;
        }

        public void setRegistered(String registered) {
            this.registered = registered;
        }

        public String getAtSchool() {
            return atSchool;
        }

        public void setAtSchool(String atSchool) {
            this.atSchool = atSchool;
        }

        public String getClassX() {
            return classX;
        }

        public void setClassX(String classX) {
            this.classX = classX;
        }

        public String getCampus() {
            return campus;
        }

        public void setCampus(String campus) {
            this.campus = campus;
        }
    }
}
