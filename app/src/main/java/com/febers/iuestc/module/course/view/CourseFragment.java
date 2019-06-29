package com.febers.iuestc.module.course.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.febers.iuestc.MyApp;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.base.Constants;
import com.febers.iuestc.entity.BeanCourse;
import com.febers.iuestc.entity.BeanUserStatus;
import com.febers.iuestc.module.course.presenter.CourseContract;
import com.febers.iuestc.module.course.presenter.CoursePresenterImpl;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.febers.iuestc.util.SPUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseFragment extends BaseFragment implements CourseContract.View {

    private static final String TAG = "CourseFragment";

    private CourseContract.Presenter presenter = new CoursePresenterImpl(this);

    private List<Button> buttonList = new ArrayList<>();
    private List<Integer> weeks = new ArrayList<>();
    private OptionsPickerView pickerView;
    private TextView tvNowWeek;
    private ImageView ivNull;
    private int nowWeek;

    @Override
    protected int setContentView() {
        return R.layout.fragment_course;
    }

    @Override
    protected int setToolbar() {
        return R.id.tb_course;
    }

    @Override
    protected String setToolbarTitle() {
        return "";
    }

    @Override
    protected int setMenu() {
        return R.menu.course_menu;
    }

    @Override
    protected Boolean registerEventBus() {
        return true;
    }

    @Override
    public void dataRequest(Boolean isRefresh) {
        if (isRefresh) {
            if (!MyApp.checkNetConnecting()) {
                onError("当前网络不可用");
                return;
            }
            showProgressDialog();
        }
        presenter.courseRequest(isRefresh);
    }

    @Override
    protected void initView() {
        initPicker();
        tvNowWeek = findViewById(R.id.tv_course_title);
        ivNull = findViewById(R.id.iv_null_course);
        setTitle(0);
        dataRequest(false);
    }

    @Override
    public void showUnderCourse(BaseEvent<List<BeanCourse>> event) {
        dismissProgressDialog();
        activity.runOnUiThread( () -> {
            if (event.getCode() == BaseCode.ERROR) {
                onError("刷新课表出错,请尝试再次获取");
                return;
            }
            if (event.getCode() == BaseCode.CLEAR) {
                for (int i = 0; i < buttonList.size(); i++) {
                    buttonList.get(i).setVisibility(View.GONE);
                }
                ivNull.setVisibility(View.VISIBLE);
                return;
            }
            for (int i = 0; i < buttonList.size(); i++) {
                buttonList.get(i).setVisibility(View.INVISIBLE);
            }
            if (event.getCode() == BaseCode.UPDATE) {
                ivNull.setVisibility(View.GONE);
                onError("刷新课表成功");
            }
            CourseViewHelper helper = new CourseViewHelper(activity);
            helper.create(event.getDate(), nowWeek, buttonList);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_course_choose_week:
                if (!isLogin()) {
                    break;
                }
                pickerView.show();
                break;
            case R.id.item_course_refresh:
                if (!isLogin()) {
                    break;
                }
                dataRequest(true);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        boolean firstGet = SPUtil.INSTANCE().get(Constants.COURSE_FIRST_GET, true);
        if (firstGet) {
            dataRequest(true);
            SPUtil.INSTANCE().put(Constants.COURSE_FIRST_GET, false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userStatusChanged(BaseEvent<BeanUserStatus> event) {
        if (event.getCode() == BaseCode.UPDATE) {
            //刷新课表
            dataRequest(true);
        } else {
            showUnderCourse(new BaseEvent<>(BaseCode.CLEAR, new ArrayList<>()));
        }
    }

    @Override
    public void statusLoss() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    /**
     * 选择器初始化
     * 默认共有20周
     */
    private void initPicker() {
        for (int i = 1; i < 21; i++) {
            weeks.add(i);
        }
        pickerView = new OptionsPickerBuilder(getContext(), (options1, options2, options3, v) -> {
            Toast.makeText(getContext(), "当前周数已设置为第" + (options1+1) + "周", Toast.LENGTH_SHORT).show();
            SPUtil.INSTANCE().put(Constants.COURSE_NOW_WEEK, (options1+1));
            SPUtil.INSTANCE().put("set_week", true);
            setTitle(options1+1);
            dataRequest(false);
        })
                .setTitleText("选择当前周数")
                .setOutSideCancelable(false)
                .setCyclic(true, false, false)
                .setBgColor(getResources().getColor(R.color.lightgray))
                .build();
        pickerView.setPicker(weeks);
    }

    private void setTitle(int week) {
        nowWeek = 1;
        if (week == 0) {
            nowWeek = SPUtil.INSTANCE().get(Constants.COURSE_NOW_WEEK, 1);
            if (nowWeek == 0) nowWeek = 1;
        } else {
            nowWeek = week;
        }
        Objects.requireNonNull(getActivity()).runOnUiThread(()-> tvNowWeek.setText("第" + nowWeek + "周"));
    }

    private Boolean isLogin() {
        return SPUtil.INSTANCE().get(Constants.IS_LOGIN, false);
    }

    public static CourseFragment newInstance(String param1) {
        Bundle args = new Bundle();
        args.putString(PARAMETER, param1);
        CourseFragment fragment = new CourseFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
