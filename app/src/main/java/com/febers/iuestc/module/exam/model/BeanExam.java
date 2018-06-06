package com.febers.iuestc.module.exam.model;

public class BeanExam {
    private String num = "";
    private String name = "";
    private String date = "";
    private String time = "";
    private String position = "";
    private String seat = "";
    private String status = "";
    private String other = "";
    private Boolean noPost = true;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Boolean getNoPost() {
        return noPost;
    }

    public void setNoPost(Boolean noPost) {
        this.noPost = noPost;
    }
}
