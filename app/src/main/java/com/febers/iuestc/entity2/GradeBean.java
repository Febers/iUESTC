package com.febers.iuestc.entity2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GradeBean {

    private int code;
    private String time;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 网球D
         * type : 大学体育IV
         * credit : 1
         * overall : 92
         * resit : --
         * final : 92
         * gpa : 4
         */

        private String name;
        private String type;
        private String credit;
        private String overall;
        private String resit;
        @SerializedName("final")
        private String finalX;
        private String gpa;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getOverall() {
            return overall;
        }

        public void setOverall(String overall) {
            this.overall = overall;
        }

        public String getResit() {
            return resit;
        }

        public void setResit(String resit) {
            this.resit = resit;
        }

        public String getFinalX() {
            return finalX;
        }

        public void setFinalX(String finalX) {
            this.finalX = finalX;
        }

        public String getGpa() {
            return gpa;
        }

        public void setGpa(String gpa) {
            this.gpa = gpa;
        }
    }
}
