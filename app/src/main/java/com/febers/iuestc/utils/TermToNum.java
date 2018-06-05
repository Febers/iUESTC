package com.febers.iuestc.utils;

public class TermToNum {
    /**
     * 把本科学年转换为相应的semester.id
     * @param term 学年,上学期为0，下学年为1
     * @return  semester.id
     */
    public static String toNumber(String term) {
        switch (term) {
            case "13140":
                return "1";
            case "13141":
                return "2";
            case "14150":
                return "43";
            case "14151":
                return "63";
            case "15160":
                return "84";
            case "15161":
                return "103";
            case "16170":
                return "123";
            case "16171":
                return "143";
            case "17180":
                return "163";
            case "17181":
                return "183";
            case "18190":
                return "203";
            case "18191":
                return "";
            case "1920":
                return "";
            case "19201":
                return "";
            default:
                break;
        }
        return "";
    }
}
