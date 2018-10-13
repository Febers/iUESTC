/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:04
 *
 */

package com.febers.iuestc.util;

public class GradeUtil {

    private static final String TAG = "GradeUtil";
    /**
     * 将分数或者等级转换为小数
     */
    public static float getScoreFromGrade(String raw) {
        try {
            if (Integer.valueOf(raw) >= 0 && Integer.valueOf(raw) <= 100) {
                return Float.valueOf(raw);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return rate(raw);
        }
        return 0f;
    }


    public static float changeSemester(String term) {
        if (term == null) return 0f;
        switch (term) {
            case "2013-20141":
                return 1314.1f;
            case "2013-20142":
                return 1314.2f;
            case "2014-20151":
                return 1415.1f;
            case "2014-20152":
                return 1415.2f;
            case "2015-20161":
                return 1516.1f;
            case "2015-20162":
                return 1516.2f;
            case "2016-20171":
                return 1617.1f;
            case "2016-20172":
                return 1617.2f;
            case "2017-20181":
                return 1718.1f;
            case "2017-20182":
                return 1718.2f;
            case "2018-20191":
                return 1819.1f;
            case "2018-20192":
                return 1819.2f;
            case "2019-20201":
                return 1920.1f;
            case "2019-20202":
                return 1920.2f;
            default:
                return 10000.0f;
        }
    }

    private static float rate(String rate) {
        if (rate == null) return 0f;
        switch (rate) {
            case "A+":
                return 95f;
            case "A":
                return 85f;
            case "A-":
                return 80f;
            case "B+":
                return 75f;
            case "B":
                return 70f;
            case "B-":
                return 65f;
            case "C":
                return 60f;
            case "D":
                return 50f;
            default:
                return 0f;
        }
    }
}
