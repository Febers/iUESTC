package com.febers.iuestc.module.course.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.module.course.contract.CourseContract;
import com.febers.iuestc.module.login.model.ILoginModel;
import com.febers.iuestc.module.login.model.LoginModel;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.utils.MySharedPreferences;
import com.febers.iuestc.utils.ApiUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 23033 on 2018/3/23.
 */

public class CourseModel implements ICourseModel{

    private static final String TAG = "CourseModel";
    private List<BeanCourse> beanCourseList = new ArrayList<>();
    private CourseContract.Presenter mCoursePresenter;
    private String courseHtml;
    private Context context = BaseApplication.getContext();
    private int tryTime = 1;

    public CourseModel(CourseContract.Presenter coursePresenter) {
        mCoursePresenter = coursePresenter;
    }

    @Override
    public void getCourseListRequest(final Boolean isRefresh) throws Exception{
        beanCourseList.clear();
        if (!BaseApplication.isLogin()) {
            Log.d(TAG, "getCourseListRequest: 未登录");
            return;
        }
        Boolean get_course = MySharedPreferences.getInstance().get(context
                .getString(R.string.sp_get_course), false);
        if (get_course && (!isRefresh)) {
            //加载本地文件
            loadLocalFile();
            return;
        }
        //否则请求数据
        new Thread( () ->{
            getCourseHtml();
        }).start();
    }

    /**
     * 获取课表信息
     * post格式如下
     ignoreHead:1
     setting.kind:std
     startWeek:1
     semester.id:如果为0则表示当前学期
     ids:143831
     ids来自http://eams.uestc.edu.cn/eams/courseTableForStd.action里的一行代码
     */
    private void getCourseHtml() {
        try {
            OkHttpClient client = SingletonClient.getInstance();
            Request request = new Request.Builder()
                    .url("http://eams.uestc.edu.cn/eams/courseTableForStd.action")
                    .build();
            Response response_ids = client.newCall(request).execute();
            String text_ids = response_ids.body().string();
            String ids = getIds(text_ids);

            FormBody body = new FormBody.Builder()
                    .add("ignoreHead", "1")
                    .add("setting.kind", "std")
                    .add("startWeek", "1")
                    .add("semester.id", "0")
                    .add("ids", ids)
                    .build();
            request = new Request.Builder()
                    .post(body)
                    .url(ApiUtil.COURSE_TABLE_URL)
                    .build();

            Response response = client.newCall(request).execute();
            courseHtml = response.body().string();
            if (courseHtml.contains("登录规则")) {
                ILoginModel loginModel = new LoginModel();
                if (tryTime == 1) {
                    Boolean reLogin  = loginModel.reloginService();
                    if (!reLogin) {
                        mCoursePresenter.errorResult("登录状态发生改变，请重新登录");
                        return;
                    }
                    getCourseHtml();
                    tryTime++;
                    return;
                }
            }
        } catch (SocketTimeoutException e) {
            mCoursePresenter.errorResult("网络超时");
        } catch (IOException e) {
            e.printStackTrace();
            MySharedPreferences.getInstance().put("get_course", false);
            return;
        } catch (Exception e) {
            Log.d(TAG, "检测到异常！");
            MySharedPreferences.getInstance().put("get_course", false);
            return;
        }
        //解析源码成List格式，发送给presenter，并存储
        getCourseTable(courseHtml);
    }

    private void getCourseTable(String html) {
        try {
            int pStart = html.indexOf("activity = new TaskActivity");
            if (pStart == -1) {
                mCoursePresenter.courseResult("课表为空", new ArrayList<BeanCourse>());
                return;
            }
            int pEnd = html.lastIndexOf("table0.marshalTable");
            String s1 = html.substring(pStart, pEnd);

            pStart = s1.indexOf("activity = new TaskActivity");
            pEnd = s1.lastIndexOf("activity = new TaskActivity");

            List<String> partList = new ArrayList<>();
            for (int pNext = 0; ; ) {
                pStart = pNext;
                if (pStart == pEnd) {   //说明只剩最后一节课的信息
                    partList.add(s1.substring(pEnd, s1.length()-1));
                    break;
                }
                pNext = s1.indexOf("activity = new TaskActivity", pStart+1);
                partList.add(s1.substring(pStart, pNext-1));
            }
            //保存课程数目
            MySharedPreferences.getInstance().put("course_count", partList.size());
            for (int i = 0; i < partList.size(); i++) {
                getPerCourse(partList.get(i), i);
            }
            mCoursePresenter.courseResult("更新", beanCourseList);
        } catch (Exception e) {
            e.printStackTrace();
            mCoursePresenter.errorResult("获取课表出错");
            Log.d(TAG, "getCourseTable: 第一个解析方法出错");
            return;
        }
        MySharedPreferences.getInstance().put("get_course", true);
    }

    /**
     * 通过传入的每节课的字符串，提取每节课
     * 通过循环的方式先获取前几个属性
     */
    private void getPerCourse(String stCourse, int i) {
        List<String> courseDetail = new ArrayList<>();
        try {
            int pStart = stCourse.indexOf("\"");
            int pNext = 0;
            for (int j = 0; j < 7; j++) {
                pNext = stCourse.indexOf("\"", pStart+1);
                //多个老师之间逗号隔开，会影响下面的解析，所以把出现的逗号都改成顿号
                courseDetail.add(stCourse.substring(pStart+1, pNext).replaceAll(",", "、"));
                pStart = stCourse.indexOf("\"", pNext+1);
            }
            int pEquote = stCourse.indexOf("=", pNext);
            Character day = stCourse.charAt(pEquote+1);
            courseDetail.add(day.toString());

            int pPlusStart = stCourse.indexOf("+", pNext);
            int pFenhao = stCourse.indexOf(";", pPlusStart);
            int pPlusEnd = stCourse.lastIndexOf("+");

            String stTime = "";
            for (int k = 0; k < 10; k++) {
                stTime += stCourse.substring(pPlusStart+1, pFenhao);
                if (pPlusStart == pPlusEnd) {
                    break;
                }
                pPlusStart = stCourse.indexOf("+", pPlusStart+1);
                pFenhao = stCourse.indexOf(";", pPlusStart);
            }
            courseDetail.add(stTime);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getPerCourse: 第二个解析方法出错");
            return;
        }
        //保存每节课的信息,同时去掉两边的[]以便还原
        SharedPreferences spLocalCourse = BaseApplication.getContext().getSharedPreferences("local_course", 0);
        SharedPreferences.Editor editor = spLocalCourse.edit();
        editor.putString("beanCourse" + i, courseDetail.toString().replace("[", "").replace("]", ""));
        editor.commit();

        //teacher, name, classroom, week, time
        BeanCourse beanCourse = new BeanCourse(courseDetail.get(1), courseDetail.get(3), courseDetail.get(5), getWeeks(courseDetail.get(6)), " "+courseDetail.get(7)+" "+courseDetail.get(8));
        beanCourseList.add(beanCourse);
    }

    //加载本地文件获得的课程列表
    @Override
    public void loadLocalFile() {
        try {
            List<BeanCourse> courseList = new ArrayList<>();
            int i = MySharedPreferences.getInstance().get("course_count", 10);
//            Log.d(TAG, "loadLocalFile: 课程数目为" + i);
            for (int j = 0; j < i; j++) {
                SharedPreferences spLocalCourse = BaseApplication.getContext().getSharedPreferences("local_course", 0);
                String s = spLocalCourse.getString("beanCourse"+j, "");
//                String s = MySharedPreferences.getInstance().get("beanCourse"+j, "");
                String [] ss = s.split(",");
                ss[6] = getWeeks(ss[6]);    //转换成单双周
                List<String> list = Arrays.asList(ss);
                BeanCourse beanCourse = new BeanCourse(list.get(1), list.get(3), list.get(5), list.get(6), list.get(7)+list.get(8));
//                Log.d(TAG, "loadLocalFile: " + beanCourse.getECardBalance());
                courseList.add(beanCourse);
            }
            mCoursePresenter.courseResult("本地", courseList);
        } catch (Exception e) {
            Log.d(TAG, "loadLocalFile: 读取本地文件出现错误");
        }
    }

    /**
     *判断周数类型，返回的结果有三种类型
     * @return  三周周类型
     */
    private String getWeeks(String rawCode) {
        int startWeek = 1;
        int endWeek = 2;
        String  weekType = "";
//        rawCode = "01010101010101010100000000000000000000000000000000000";
        startWeek = rawCode.indexOf("1") - 1;
        endWeek = rawCode.lastIndexOf("1") - 1;
        String temp = rawCode.charAt(startWeek + 2) + "";

        if (temp.equals("1")) {
            weekType = "周";
        } else if (startWeek % 2 == 0) {
            weekType = "周(双周)";
        } else if (startWeek % 2 != 0) {
            weekType = "周(单周)";
        }
        return startWeek + "-" + endWeek + weekType;
    }

    /**
     * 获取在网页中的ids
     * http://eams.uestc.edu.cn/eams/courseTableForStd.action?_=1524663800243
     */
    private String getIds(String sourceCode) {
        int a = sourceCode.indexOf("ids");
        int start = sourceCode.indexOf("\"", a+4);
        int end = sourceCode.indexOf("\"", start+1);
        return sourceCode.substring(start+1, end);
    }
}
