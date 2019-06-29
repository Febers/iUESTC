package com.febers.iuestc.module.course.model;

import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseModel;
import com.febers.iuestc.entity.BeanCourse;
import com.febers.iuestc.module.course.presenter.CourseContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.util.SPUtil;
import com.febers.iuestc.util.ApiUtil;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.febers.iuestc.base.Constants.COURSE_GET;
import static com.febers.iuestc.util.CourseUtil.resolveRepeatCourse;

public class CourseModelImpl extends BaseModel implements CourseContract.Model {

    private static final String TAG = "CourseModelImpl";

    private CourseContract.Presenter coursePresenter;

    public CourseModelImpl(CourseContract.Presenter coursePresenter) {
        super(coursePresenter);
        this.coursePresenter = coursePresenter;
    }

    @Override
    public void updateCourseService(Boolean isRefresh) {
        boolean courseGot = SPUtil.INSTANCE().get(COURSE_GET, false);
        new Thread( () -> {
            if (courseGot && (!isRefresh)) {
                getSavedCourse();
            } else {
                getHttpData();
            }
        }).start();
    }

    /**
     * 获取本科生课表信息
     * post格式如下
     ignoreHead:1
     setting.kind:std
     startWeek:1
     semester.id:如果为0则表示当前学期
     ids:143831
     ids来自http://eams.uestc.edu.cn/eams/courseTableForStd.action里的一行代码
     */
    @Override
    protected void getHttpData() {
        List<BeanCourse> courseList;
        try {
            OkHttpClient client = SingletonClient.getInstance();
            Request request = new Request.Builder()
                    .url(ApiUtil.UNDER_COURSE_IDS_URL)
                    .build();
            Response response_ids = client.newCall(request).execute();
            String text_ids = response_ids.body().string();
            if (!userAuthenticate(text_ids)) {
                return;
            }
            String ids = getUnderIds(text_ids);

            FormBody body = new FormBody.Builder()
                    .add("ignoreHead", "1")
                    .add("setting.kind", "std")
                    .add("startWeek", "1")
                    .add("semester.id", "0")
                    .add("ids", ids)
                    .build();
            request = new Request.Builder()
                    .post(body)
                    .url(ApiUtil.UNDER_COURSE_TABLE_URL)
                    .build();
            Response response = client.newCall(request).execute();
            String courseHtml = response.body().string();
            if (!userAuthenticate(courseHtml)) {
                return;
            }
            courseList = CourseResolver.resolveUnderCourseHtml(new StringBuilder(courseHtml));

        } catch (Exception e) {
            e.printStackTrace();
            coursePresenter.underCourseResult(new BaseEvent<>(BaseCode.ERROR, new ArrayList<>()));
            SPUtil.INSTANCE().put(COURSE_GET, false);
            serviceError(UNKNOWN_ERROR);
            return;
        }
        SPUtil.INSTANCE().put(COURSE_GET, true);
        BaseEvent<List<BeanCourse>> event = new BaseEvent<>(BaseCode.UPDATE, resolveRepeatCourse(courseList));
        coursePresenter.underCourseResult(event);
    }

    //加载本地文件获得的课程列表
    private void getSavedCourse() {
        try {
            List<BeanCourse> courseList = CourseStore.getByFile();
            coursePresenter.underCourseResult(new BaseEvent<>(BaseCode.LOCAL, resolveRepeatCourse(courseList)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取在网页中的ids
     */
    private String getUnderIds(String sourceCode) {
        int a = sourceCode.indexOf("ids");
        int start = sourceCode.indexOf("\"", a+4);
        int end = sourceCode.indexOf("\"", start+1);
        return sourceCode.substring(start+1, end);
    }
}
