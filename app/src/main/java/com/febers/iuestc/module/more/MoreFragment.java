package com.febers.iuestc.module.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.util.SPUtil;
import com.febers.iuestc.view.adapter.AdapterSetting;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.entity.BeanSetting;
import com.febers.iuestc.module.exam.view.ExamActivity;
import com.febers.iuestc.module.grade.view.GradeActivity;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.febers.iuestc.module.news.view.NewsActivity;
import com.febers.iuestc.module.service.view.BusActivity;
import com.febers.iuestc.module.service.view.CalenderActivity;
import com.febers.iuestc.module.user.view.UserActivity;
import com.febers.iuestc.module.login.model.LogoutHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.febers.iuestc.base.Constants.IS_LOGIN;
import static com.febers.iuestc.base.Constants.USER_ID;
import static com.febers.iuestc.base.Constants.USER_NAME;

public class MoreFragment extends BaseFragment {

    private TextView tvName;
    private TextView tvId;

    @Override
    protected int setContentView() {
        return R.layout.fragment_more;
    }

    @Override
    protected int setToolbar() {
        return R.id.user_toolbar;
    }

    @Override
    protected int setMenu() {
        return R.menu.more_menu;
    }

    @Override
    protected void initView() {
        tvName = findViewById(R.id.tv_fragment_user_name);
        tvId = findViewById(R.id.tv_fragment_user_id);

        RecyclerView rvMoreSetting0 = findViewById(R.id.rv_user_setting0);
        rvMoreSetting0.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterSetting adapterSetting0 = new AdapterSetting(getContext(), initSettingList0());
        rvMoreSetting0.setAdapter(adapterSetting0);
        adapterSetting0.setOnItemClickListener((viewHolder, beanSetting, i) -> onClickListViewItem0(i));

        RecyclerView rvMoreSetting1 = findViewById(R.id.rv_user_setting1);
        rvMoreSetting1.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterSetting adapterSetting1 = new AdapterSetting(getContext(), initSettingList1());
        rvMoreSetting1.setAdapter(adapterSetting1);
        adapterSetting1.setOnItemClickListener((viewHolder, beanSetting, i) -> onClickListViewItem1(i));

        RecyclerView rvMoreSetting2 = findViewById(R.id.rv_user_setting2);
        rvMoreSetting2.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterSetting adapterSetting2 = new AdapterSetting(getContext(), initSettingList2());
        rvMoreSetting2.setAdapter(adapterSetting2);
        adapterSetting2.setOnItemClickListener((viewHolder, beanSetting, i) -> onClickListViewItem2(i));

        ConstraintLayout layout = findViewById(R.id.ll_user);
        layout.setOnClickListener(v ->  {
            if (!SPUtil.INSTANCE().get(getString(R.string.sp_is_login), false)) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            } else {
                startActivity(new Intent(getActivity(), UserActivity.class));
            }
        });

        dataRequest(true);
    }


    private void onClickListViewItem0(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), ExamActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), GradeActivity.class));
                break;
        }
    }

    private void onClickListViewItem1(int position) {
        switch (position) {
//            case 0:
//                Intent intent = new Intent(getActivity(), ServiceActivity.class);
//                intent.putExtra("position", 0);
//                startActivity(intent);
//                break;
            case 0:
                Intent i1 = new Intent(getActivity(), NewsActivity.class);
                i1.putExtra("type", 0);
                startActivity(i1);
                break;
            case 1:
                startActivity(new Intent(getActivity(), BusActivity.class));
                break;
            case 2:
                startActivity(new Intent(getActivity(), CalenderActivity.class));
                break;
//            case 3:
//                startActivity(new Intent(getActivity(), NoticeActivity.class));
//                break;
            default:
                break;
        }
    }

    private void onClickListViewItem2(int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getActivity(), ThemeActivity.class));
                break;
            case 1:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            default:
                break;
        }
    }

    private List<BeanSetting> initSettingList0() {
        List<BeanSetting> settingList = new ArrayList<>();
        BeanSetting stExam = new BeanSetting("考试安排", R.mipmap.ic_pen_yellow);
        BeanSetting stGrade = new BeanSetting("我的成绩", R.mipmap.ic_tend_pink);
        settingList.add(stExam);
        settingList.add(stGrade);
        return settingList;
    }

    private List<BeanSetting> initSettingList1() {
        List<BeanSetting> settingList = new ArrayList<>();
        BeanSetting stService = new BeanSetting("快捷查询", R.mipmap.ic_book_blue);
        BeanSetting stUnderJW = new BeanSetting("本科教务", R.mipmap.ic_news_red);
        BeanSetting stPostJW = new BeanSetting("研究生教务", R.mipmap.ic_news_purple);
        BeanSetting stBus = new BeanSetting("校车服务", R.mipmap.ic_bus_blue2);
        BeanSetting stCalendar = new BeanSetting("校历", R.mipmap.ic_cal_green2);
        BeanSetting stNotice = new BeanSetting("通知公告", R.mipmap.ic_news_red);

//        settingList.add(stService);
        settingList.add(stUnderJW);
//        beanSettingList.add(stPostJW);
        settingList.add(stBus);
        settingList.add(stCalendar);
//        settingList.add(stNotice);
        return settingList;
    }

    private List<BeanSetting> initSettingList2() {
        List<BeanSetting> settingList = new ArrayList<>();
        BeanSetting stTheme = new BeanSetting("主题风格", R.mipmap.ic_theme_red);
        BeanSetting stAbout = new BeanSetting("关于", R.mipmap.ic_about_blue2);
        settingList.add(stTheme);
        settingList.add(stAbout);
        return settingList;
    }

    @Override
    public void dataRequest(Boolean isRefresh) {
        if (SPUtil.INSTANCE().get(IS_LOGIN,false)) {
            String userName = SPUtil.INSTANCE()
                    .get(USER_NAME, "");
            String userId = SPUtil.INSTANCE()
                    .get(USER_ID, "");
            if (userName.trim().isEmpty()) {
                userName = "已登录";
            }
            tvName.setText(userName);
            tvId.setText(userId);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_menu_logout:
                if (!SPUtil.INSTANCE().get(IS_LOGIN,false)) {
                    break;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("您要注销信息门户登录吗?")
                        .setPositiveButton("注销",  (dialogInterface, i) -> {
                            logout();
                        })
                        .setNegativeButton("取消", (dialogInterface, i) -> {
                        });
                builder.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        dataRequest(true);
    }

    private void logout() {
        LogoutHelper.logout();
        tvName.setText("未登录");
        tvId.setText("");
    }

    public static MoreFragment newInstance(String param1) {
        Bundle args = new Bundle();
        args.putString(PARAMETER, param1);
        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
