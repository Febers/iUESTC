package com.febers.iuestc.module.course.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.module.course.contract.CourseContract;
import com.febers.iuestc.module.course.model.BeanCourse;
import com.febers.iuestc.module.course.model.CourseEventMessage;
import com.febers.iuestc.module.course.contract.CoursePresenterImpl;
import com.febers.iuestc.utils.CourseWeekUtil;
import com.febers.iuestc.utils.MySharedPreferences;
import com.febers.iuestc.utils.RandomUtil;
import com.febers.iuestc.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseFragment extends BaseFragment implements CourseContract.View{

    private static final String TAG = "CourseFragment";
    private CourseContract.Presenter presenter = new CoursePresenterImpl(this);

    private int[][] courseArray = {
            {R.id.bt_course_001, R.id.bt_course_023, R.id.bt_course_045, R.id.bt_course_067, R.id.bt_course_089, R.id.bt_course_01011},
            {R.id.bt_course_101, R.id.bt_course_123, R.id.bt_course_145, R.id.bt_course_167, R.id.bt_course_189, R.id.bt_course_11011},
            {R.id.bt_course_201, R.id.bt_course_223, R.id.bt_course_245, R.id.bt_course_267, R.id.bt_course_289, R.id.bt_course_21011},
            {R.id.bt_course_301, R.id.bt_course_323, R.id.bt_course_345, R.id.bt_course_367, R.id.bt_course_389, R.id.bt_course_31011},
            {R.id.bt_course_401, R.id.bt_course_423, R.id.bt_course_445, R.id.bt_course_467, R.id.bt_course_489, R.id.bt_course_41011},
            {R.id.bt_course_501, R.id.bt_course_523, R.id.bt_course_545, R.id.bt_course_567, R.id.bt_course_589, R.id.bt_course_51011},
            {R.id.bt_course_601, R.id.bt_course_623, R.id.bt_course_645, R.id.bt_course_667, R.id.bt_course_689, R.id.bt_course_61011}};

    private int[] background = {R.drawable.cornerbg_blue, R.drawable.cornerbg_green, R.drawable.cornerbg_orange,
            R.drawable.cornerbg_purple, R.drawable.cornerbg_cyan};

    private List<BeanCourse> beanCourseList;
    private int nowWeek;
    private OptionsPickerView pickerView;
    private List<Integer> weeks = new ArrayList<>();
    private TextView tvNowWeek;
    private CourseEventMessage courseEventMessage;
    private List<Button> buttonList = new ArrayList<>();
    private Context context = BaseApplication.getContext();

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
    public void getCourse(Boolean isRefresh) {
        if (isRefresh) {
            if (!BaseApplication.checkNetConnecting()) {
                Toast.makeText(getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                return;
            }
            showProgressDialog();
        }
        presenter.courseRequest(isRefresh);
    }

    @Override
    public void showCourse(CourseEventMessage message) {
        dismissProgressDialog();
        getActivity().runOnUiThread( () -> {
            if (message.getStatus().equals("清空")) {
                for (int i = 0; i < buttonList.size(); i++) {
                    buttonList.get(i).setVisibility(View.INVISIBLE);
                }
                return;
            }
            if (message.getStatus().equals("错")) {
                Toast.makeText(getContext(), "刷新课表出错,请联系开发者", Toast.LENGTH_SHORT).show();
                return;
            }
            //与上次打开相比,更新周数
            int inteval = CourseWeekUtil.intevalWeek();
            nowWeek = nowWeek + inteval;

            courseEventMessage = message;
            beanCourseList = courseEventMessage.getBeanCourseList();
            for (int i = 0; i < beanCourseList.size(); i++) {
                //通过判断周数判断是否要显示课程
                String week = beanCourseList.get(i).getWeek();
                String result = CourseWeekUtil.check(week, nowWeek);
                if (result.equals("false")) {
                    continue;
                }
                //第一个数代表数组的第一维(星期几),第二个数代表第几节课
                //时间格式要注意每个数字前面都有一个空格
                String s = beanCourseList.get(i).getTime();
                String s_1 = s.substring(1,2);  //星期
                String s_2 = s.substring(3,s.length()); //完整的节数
                String s_3 = s.substring(3,4);  //前两节
                int x = Integer.valueOf(s_1);
                int y = Integer.valueOf(s_3);   //为了确定z，y取第一个数字，即第三个字符
                int z = y/2;    //y/2是因为一节课占两格
                if (s_2.equals("1011")) {
                    //解决最后两节课时 xz计算错误的问题
                    z = 5;
                }
                Button bt = getActivity().findViewById(courseArray[x][z]);
                //修改高度
                if (y == 8 && s_2.contains("11")) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bt.getLayoutParams();
                    params.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2*96, getResources().getDisplayMetrics()));
                    bt.setLayoutParams(params);
                } else if (y == 8 && s_2.contains("10")) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bt.getLayoutParams();
                    params.height = ((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 144, getResources().getDisplayMetrics()));
                    bt.setLayoutParams(params);
                }
                bt.setText(beanCourseList.get(i).getDetail());
                bt.setTextColor(Color.parseColor("#ffffff"));
                bt.setVisibility(View.VISIBLE);
                bt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                if (Build.VERSION.SDK_INT >= 17) {
                    bt.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                bt.setBackgroundResource(background[RandomUtil.getRandomFrom0(5)]);
                if (result.contains("noNow")) {
                    bt.setBackgroundResource(R.drawable.cornerbg_gray);
                }
                buttonList.add(bt);
                //获取当前标准格式的时间并保存
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String time = sdf.format(new Date());
                MySharedPreferences.getInstance().put(context.getString(R.string.sp_course_last_time), time);
                MySharedPreferences.getInstance().put(context.getString(R.string.sp_now_week), nowWeek);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
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
                if (!MySharedPreferences.getInstance().get("is_login", false)) {
                    break;
                }
                pickerView.show();
                break;
            case R.id.item_course_refresh:
                if (!MySharedPreferences.getInstance().get("is_login", false)) {
                    break;
                }
                getCourse(true);
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
        pickerView = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(final int options1, int options2, int options3, View v) {
                Toast.makeText(getContext(), "当前周数已设置为第" + (options1+1) + "周", Toast.LENGTH_SHORT).show();
                MySharedPreferences.getInstance().put(context.getString(R.string.sp_now_week), (options1+1));
                MySharedPreferences.getInstance().put("set_week", true);
                setTitle(options1+1);
                getCourse(false);
            }
        })
                .setTitleText("选择当前周数")
                .setOutSideCancelable(false)
                .setCyclic(true, false, false)
                .setCancelColor(getResources().getColor(R.color.colorAccent))
                .setBgColor(getResources().getColor(R.color.lightgray))
                .build();
        pickerView.setPicker(weeks);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Boolean isLogin = MySharedPreferences.getInstance().get(context.getString(R.string.sp_is_login), false);
            if (!isLogin) {
                showCourse(new CourseEventMessage("清空", new ArrayList<>()));
                return;
            }
            Boolean firstGet = MySharedPreferences.getInstance().get(context
                    .getString(R.string.sp_course_first_get), true);
            if (firstGet) {
                if (!BaseApplication.checkNetConnecting()) {
                    return;
                }
                getCourse(true);
                MySharedPreferences.getInstance().put(context
                        .getString(R.string.sp_course_first_get), false);
                return;
            }
            getCourse(false);
        }
    }

    private void setTitle(int week) {
        nowWeek = 1;
        if (week == 0) {
            nowWeek = MySharedPreferences.getInstance().get(BaseApplication.getContext().getString(R.string.sp_now_week), 1);
            if (nowWeek == 0) nowWeek = 1;
        } else {
            nowWeek = week;
        }
        getActivity().runOnUiThread(()-> {
            tvNowWeek.setText("第" + nowWeek + "周");
        });
    }

}