package com.febers.iuestc.entity2;

import java.util.List;

public class CourseBean {

    /**
     * code : 201
     * data : [{"courseName":"混合集成电路与器件","courseId":"G0217220.02","teacher":"潘泰松","room":"第二教学楼107","time":[["2","4"],["2","5"]],"date":["1-9周"]},{"courseName":"混合集成电路与器件","courseId":"G0217220.02","teacher":"潘泰松","room":"第二教学楼107","time":[["0","8"],["0","9"]],"date":["1-9周"]},{"courseName":"疯狂的电池","courseId":"R0202320.01","teacher":"谢小东","room":"第二教学楼403","time":[["3","8"],["3","9"],["3","10"],["3","11"]],"date":["1-9周"]},{"courseName":"毛泽东思想和中国特色社会主义理论体系概论","courseId":"B1800360.84","teacher":"张凤军","room":"第二教学楼107","time":[["3","2"],["3","3"]],"date":["1-13周"]},{"courseName":"毛泽东思想和中国特色社会主义理论体系概论","courseId":"B1800360.84","teacher":"张凤军","room":"第二教学楼107","time":[["1","4"],["1","5"]],"date":["1-13周"]},{"courseName":"电子材料与元器件","courseId":"F0217140.05","teacher":"胡永达","room":"第二教学楼205","time":[["3","4"],["3","5"]],"date":["1-7周"]},{"courseName":"电子材料与元器件","courseId":"F0217140.05","teacher":"胡永达","room":"第二教学楼205","time":[["1","8"],["1","9"],["1","10"]],"date":["1-7周"]},{"courseName":"电子材料与元器件","courseId":"F0217140.05","teacher":"陆海鹏","room":"第二教学楼205","time":[["3","4"],["3","5"]],"date":["8-14周"]},{"courseName":"电子材料与元器件","courseId":"F0217140.05","teacher":"陆海鹏","room":"第二教学楼205","time":[["1","8"],["1","9"],["1","10"]],"date":["8-14周"]},{"courseName":"物联网传感技术","courseId":"F0216920.01","teacher":"金立川","room":"第二教学楼103","time":[["4","2"],["4","3"]],"date":["1-9周"]},{"courseName":"物联网传感技术","courseId":"F0216920.01","teacher":"金立川","room":"第二教学楼103","time":[["1","2"],["1","3"]],"date":["1-9周"]}]
     * time : 2019-01-09T06:40:20Z
     * msg : Post Success
     */

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
         * courseName : 混合集成电路与器件
         * courseId : G0217220.02
         * teacher : 潘泰松
         * room : 第二教学楼107
         * time : [["2","4"],["2","5"]]
         * date : ["1-9周"]
         */

        private String courseName;
        private String courseId;
        private String teacher;
        private String room;
        private List<List<String>> time;
        private List<String> date;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public List<List<String>> getTime() {
            return time;
        }

        public void setTime(List<List<String>> time) {
            this.time = time;
        }

        public List<String> getDate() {
            return date;
        }

        public void setDate(List<String> date) {
            this.date = date;
        }
    }
}
