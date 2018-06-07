/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-5 下午10:45
 *
 */

package com.febers.iuestc.utils;

/**
 * Created by 23033 on 2018/3/28.
 */

public class ApiUtil {
    public final static String BEFORE_LOGIN_URL = "http://portal.uestc.edu.cn/";   //主页
    public final static String LOGIN_URL = "http://idas.uestc.edu.cn/authserver/login?service=http%3A%2F%2Fportal.uestc.edu.cn%2F";    //登录
    public final static String JWXT_URL = "http://eams.uestc.edu.cn/eams/home!submenus.action?menu.id=";    //教务系统
    public final static String COURSE_TABLE_URL = "http://eams.uestc.edu.cn/eams/courseTableForStd!courseTable.action";    //课表

    public static final String XIFU_LOGIN_URL = "https://api.bionictech.cn/app/v4/login";   //  登录喜付
    public static final String XIFU_PROFILE_URL = "https://api.bionictech.cn/app/v5/profile";   //获取绑定的学号
    public static final String XIFU_ECARD_BALANCE = "https://api.bionictech.cn/ykt_biz/external/v1/query_goods_info";   //一卡通余额
    public static final String XIFU_ELEC_BALANCE = "https://api.bionictech.cn/school/h5/electricity/getElectricityFeeBalance.action";   //电费余额
    public static final String XIFU_RECORD_URL = "https://api.bionictech.cn/app/web/v1/ykt_consumes_list";  //消费详情
    public static final String ALL_GRADE_URL = "http://eams.uestc.edu.cn/eams/teach/grade/course/person!historyCourseGrade.action?projectType=MAJOR";   //所有学期成绩
    public static final String CALENDER_URL = "http://www.jwc.uestc.edu.cn/web/News!view.action?id=1224";

    public static final String UNDER_NEWS_ZYGG = "http://www.jwc.uestc.edu.cn/web/News!queryHard.action";   //本科重要公告
    public static final String UNDER_NEWS_JXXW = "http://www.jwc.uestc.edu.cn/web/News!queryList.action?partId=40"; //本科教学新闻
    public static final String UNDER_NEWS_SJJX = "http://www.jwc.uestc.edu.cn/web/News!queryList.action?partId=38#";    //本科实践教学
    public static final String POST_NEWS_TZGG = "http://gr.uestc.edu.cn/tongzhi/119";   //研究生通知公告（教学管理）
    public static final String POST_NEWS_SXJY = "http://gr.uestc.edu.cn/tongzhi/121";   //研究生思想教育
    public static final String POST_NEWS_XSGL = "http://gr.uestc.edu.cn/tongzhi/122";   //研究生学生管理

    public static String getNewsUrl(int type, int position) {
        if (type == 0) {
            switch (position) {
                case 0:
                    return UNDER_NEWS_ZYGG;
                case 1:
                    return UNDER_NEWS_JXXW;
                case 2:
                    return UNDER_NEWS_SJJX;
                default:
                    break;
            }
        }
        if (type == 1) {
            switch (position) {
                case 0:
                    return POST_NEWS_TZGG;
                case 1:
                    return POST_NEWS_SXJY;
                case 2:
                    return POST_NEWS_XSGL;
                default:
                    break;
            }
        }
        return null;
    }
}
