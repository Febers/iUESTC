package com.febers.iuestc.entity2;

import java.util.List;

public class ExamBean {

    /**
     * code : 201
     * data : [[{"name":"马克思主义基本原理概论","date":"2019-01-07","detail":"第19周 星期一 09:30-11:30","address":"沙河第二教学樓312","seat":"32","status":"正常","examType":1},{"name":"微处理器系统结构与嵌入式系统设计","date":"2019-01-16","detail":"第20周 星期三 14:30-16:30","address":"第二教学楼104","seat":"53","status":"正常","examType":1},{"name":"光电子技术","date":"2019-01-04","detail":"第18周 星期五 09:30-11:30","address":"第二教学楼208","seat":"2","status":"正常","examType":1},{"name":"电介质物理与磁性物理","date":"2019-01-03","detail":"第18周 星期四 09:30-11:30","address":"第二教学楼208","seat":"2","status":"正常","examType":1},{"name":"微波技术","date":"2019-01-03","detail":"第18周 星期四 19:00-21:00","address":"第二教学楼201","seat":"9","status":"正常","examType":1}],[{"name":"电介质物理与磁性物理","date":"2018-11-24","detail":"第12周 星期六 19:00-21:00","address":"第二教学楼103","seat":"2","status":"正常","examType":2}],[],[]]
     * time : 2019-01-09T06:43:33Z
     * msg : Post Success
     */

    private int code;
    private String time;
    private String msg;
    private List<List<DataBean>> data;

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

    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 马克思主义基本原理概论
         * date : 2019-01-07
         * detail : 第19周 星期一 09:30-11:30
         * address : 沙河第二教学樓312
         * seat : 32
         * status : 正常
         * examType : 1
         */

        private String name;
        private String date;
        private String detail;
        private String address;
        private String seat;
        private String status;
        private int examType;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public int getExamType() {
            return examType;
        }

        public void setExamType(int examType) {
            this.examType = examType;
        }
    }
}
