/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-7 下午4:53
 *
 */

package com.febers.iuestc.module.course.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.course.presenter.CourseContract;
import com.febers.iuestc.entity.BeanCourse;
import com.febers.iuestc.module.course.presenter.CoursePresenterImpl;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.febers.iuestc.util.CourseTimeUtil;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.util.RandomUtil;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.view.custom.CustomCourseDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CourseFragment extends BaseFragment implements CourseContract.View{

    private static final String TAG = "CourseFragment";

    private int[][] mCourseArray = {
            {R.id.bt_course_001, R.id.bt_course_023, R.id.bt_course_045, R.id.bt_course_067, R.id.bt_course_089, R.id.bt_course_01011},
            {R.id.bt_course_101, R.id.bt_course_123, R.id.bt_course_145, R.id.bt_course_167, R.id.bt_course_189, R.id.bt_course_11011},
            {R.id.bt_course_201, R.id.bt_course_223, R.id.bt_course_245, R.id.bt_course_267, R.id.bt_course_289, R.id.bt_course_21011},
            {R.id.bt_course_301, R.id.bt_course_323, R.id.bt_course_345, R.id.bt_course_367, R.id.bt_course_389, R.id.bt_course_31011},
            {R.id.bt_course_401, R.id.bt_course_423, R.id.bt_course_445, R.id.bt_course_467, R.id.bt_course_489, R.id.bt_course_41011},
            {R.id.bt_course_501, R.id.bt_course_523, R.id.bt_course_545, R.id.bt_course_567, R.id.bt_course_589, R.id.bt_course_51011},
            {R.id.bt_course_601, R.id.bt_course_623, R.id.bt_course_645, R.id.bt_course_667, R.id.bt_course_689, R.id.bt_course_61011}};

    private int[] mBackground = {R.drawable.cornerbg_blue, R.drawable.cornerbg_green, R.drawable.cornerbg_orange,
            R.drawable.cornerbg_purple, R.drawable.cornerbg_cyan};

    private CourseContract.Presenter mPresenter = new CoursePresenterImpl(this);
    private List<BeanCourse> mCourseList = new ArrayList<>();
    private List<Button> buttonList = new ArrayList<>();
    private List<Integer> weeks = new ArrayList<>();
    private CustomCourseDialog customCourseDialog;
    private OptionsPickerView mPickerView;
    private TextView tvNowWeek;
    private int index = 0;
    private int nowWeek;

    @Override
    protected int setContentView() {
        return R.layout.fragment_course;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_course);
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        initPicker();
        tvNowWeek = findViewById(R.id.tv_course_title);
        setTitle(0);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        if (isRefresh) {
            if (!BaseApplication.checkNetConnecting()) {
                onError("当前网络不可用");
                return;
            }
            showProgressDialog();
        }
        mPresenter.courseRequest(isRefresh);
    }

    @Override
    public void showUnderCourse(BaseEvent<List<BeanCourse>> event) {
        mCourseList = event.getDate();
        dismissProgressDialog();
        getActivity().runOnUiThread( () -> {
            if (event.getCode() == BaseCode.CLEAR) {
                for (int i = 0; i < buttonList.size(); i++) {
                    buttonList.get(i).setVisibility(View.GONE);
                }
                return;
            }
            if (event.getCode() == BaseCode.ERROR) {
                onError("刷新课表出错,请尝试再次获取");
                return;
            }
            if (event.getCode() == BaseCode.UPDATE) {
                onError("刷新课表成功");
            }
            //与上次打开相比,更新周数
            int inteval = CourseTimeUtil.intevalWeek();
            nowWeek = nowWeek + inteval;

            for (int i = 0; i < mCourseList.size(); i++) {
                //通过判断周数判断是否要显示课程
                String week = mCourseList.get(i).getWeek();
                String result = CourseTimeUtil.checkIsInNowWeek(week, nowWeek);
                if (result.equals("false")) {
                    continue;
                }
                //第一个数代表数组的第一维(星期几),第二个数代表第几节课
                //时间格式要注意每个数字前面都有一个空格
                String stTime = mCourseList.get(i).getTime();
                //星期
                String stWeekTime = stTime.substring(1,2);
                //完整的节数
                String stDetailTime = stTime.substring(3,stTime.length());
                //前两节
                String stPre2DetailTime = stTime.substring(3,4);
                int positionDay = Integer.valueOf(stWeekTime);
                //为了确定在课表中的位置，stPre2DetailTime取第一个数字
                int positionDetailTime = Integer.valueOf(stPre2DetailTime);
                //positionDetailTime/2 是因为一节课占两格
                int postionDetailTimeInTable = positionDetailTime/2;
                if (stDetailTime.equals("1011")) {
                    //解决最后两节课时坐标计算错误的问题
                    postionDetailTimeInTable = 5;
                }
                Button btn = findViewById(mCourseArray[positionDay][postionDetailTimeInTable]);
                //修改高度
                if (positionDetailTime == 8 && stDetailTime.contains("11")) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btn.getLayoutParams();
                    params.height = ((int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 2*96, getResources().getDisplayMetrics()));
                    btn.setLayoutParams(params);
                } else if (positionDetailTime == 8 && stDetailTime.contains("10")) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btn.getLayoutParams();
                    params.height = ((int)TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 144, getResources().getDisplayMetrics()));
                    btn.setLayoutParams(params);
                }

                btn.setText(mCourseList.get(i).getDetail());
                btn.setTextColor(Color.parseColor("#ffffff"));
                btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                if (Build.VERSION.SDK_INT >= 17) {
                    btn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                btn.setBackgroundResource(mBackground[RandomUtil.getRandomFrom0(5)]);
                if (result.contains("noNow")) {
                    btn.setBackgroundResource(R.drawable.cornerbg_gray);
                }
                btn.setVisibility(View.VISIBLE);
                index = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    int a = index;
                    @Override
                    public void onClick(View v) {
                        List<BeanCourse> courseList = new ArrayList<>();
                        int ic = CustomSharedPreferences.getInstance().get("course_count", 10);
                        for (int jc = 0; jc < ic; jc++) {
                            SharedPreferences spLocalCourse = getContext().getSharedPreferences("local_course", 0);
                            String s = spLocalCourse.getString("beanCourse" + jc, "");
                            String[] ss = s.split(",");
                            List<String> list = Arrays.asList(ss);
                            BeanCourse beanCourse = new BeanCourse(list.get(1), list.get(3),
                                    list.get(5), list.get(6), list.get(7) + list.get(8));
                            courseList.add(beanCourse);
                        }
                        customCourseDialog = new CustomCourseDialog(getContext(), courseList.get(a));
                        customCourseDialog.show();
                    }
                });
                buttonList.add(btn);
                //获取当前标准格式的时间并保存
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sdf.format(new Date());
                CustomSharedPreferences.getInstance().put(mContext.getString(R.string.sp_course_last_time), time);
                CustomSharedPreferences.getInstance().put(mContext.getString(R.string.sp_now_week), nowWeek);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.course_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_course_choose_week:
                if (!CustomSharedPreferences.getInstance().get("is_login", false)) {
                    break;
                }
                mPickerView.show();
                break;
            case R.id.item_course_refresh:
                if (!CustomSharedPreferences.getInstance().get("is_login", false)) {
                    break;
                }
                dateRequest(true);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 选择器初始化
     * 默认共有20周
     */
    private void initPicker() {
        for (int i = 1; i < 21; i++) {
            weeks.add(i);
        }
        mPickerView = new OptionsPickerBuilder(getContext(), (options1, options2, options3, v) -> {
            Toast.makeText(getContext(), "当前周数已设置为第" + (options1+1) + "周", Toast.LENGTH_SHORT).show();
            CustomSharedPreferences.getInstance().put(mContext.getString(R.string.sp_now_week), (options1+1));
            CustomSharedPreferences.getInstance().put("set_week", true);
            setTitle(options1+1);
            dateRequest(false);
        })
                .setTitleText("选择当前周数")
                .setOutSideCancelable(false)
                .setCyclic(true, false, false)
                .setCancelColor(getResources().getColor(R.color.colorAccent))
                .setBgColor(getResources().getColor(R.color.lightgray))
                .build();
        mPickerView.setPicker(weeks);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Boolean isLogin = CustomSharedPreferences.getInstance().get(mContext.getString(R.string.sp_is_login), false);
            if (!isLogin) {
                showUnderCourse(new BaseEvent<>(BaseCode.CLEAR, new ArrayList<>()));
                return;
            }
            Boolean firstGet = CustomSharedPreferences.getInstance().get(mContext
                    .getString(R.string.sp_course_first_get), true);
            if (firstGet) {
                if (!BaseApplication.checkNetConnecting()) {
                    return;
                }
                dateRequest(true);
                CustomSharedPreferences.getInstance().put(mContext
                        .getString(R.string.sp_course_first_get), false);
                return;
            }
            dateRequest(false);
        }
    }

    private void setTitle(int week) {
        nowWeek = 1;
        if (week == 0) {
            nowWeek = CustomSharedPreferences.getInstance().get(BaseApplication.getContext().getString(R.string.sp_now_week), 1);
            if (nowWeek == 0) nowWeek = 1;
        } else {
            nowWeek = week;
        }
        getActivity().runOnUiThread(()-> tvNowWeek.setText("第" + nowWeek + "周"));
    }

    @Override
    public void statusToFail() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}