package com.febers.iuestc.module.exam.view;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.util.SPUtil;
import com.febers.iuestc.util.ToastUtil;
import com.febers.iuestc.view.adapter.AdapterExam;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.module.exam.presenter.ExamContract;
import com.febers.iuestc.entity.BeanExam;
import com.febers.iuestc.module.exam.presenter.ExamPresenterImpl;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * examType 1为期末考试， 2为期中考试
 */
public class ExamActivity extends BaseSwipeActivity implements ExamContract.View{

    private static final String TAG = "ExamActivity";

    private ExamContract.Presenter examPresenter = new ExamPresenterImpl(this);
    private List<BeanExam> examList = new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayout;
    private AdapterExam adapterExam;
    private RecyclerView rvExam;
    private ImageView ivEmpty;

    private RadioGroup rgExam;
    private int examType = 1;

    @Override
    protected int setView() {
        return R.layout.activity_exam;
    }

    @Override
    protected int setToolbar() {
        return R.id.tb_exam;
    }

    @Override
    protected String setToolbarTitle() {
        return "我的考试";
    }

    @Override
    protected int setMenu() {
        return R.menu.exam_menu;
    }

    @Override
    protected void findViewById() {
        rvExam = findViewById(R.id.rv_exam);
        rgExam = findViewById(R.id.rg_exam);
        ivEmpty = findViewById(R.id.iv_exam_empty);
        smartRefreshLayout = findViewById(R.id.srl_exam);
    }

    @Override
    protected void initView() {
        adapterExam = new AdapterExam(this, examList);
        rvExam.setLayoutManager(new LinearLayoutManager(this));
        rvExam.setAdapter(adapterExam);
        rgExam.setOnCheckedChangeListener( (RadioGroup group, int checkedId) -> {
                switch (checkedId) {
                    case R.id.rb_exam_final:
                        examType = 1;
                        smartRefreshLayout.autoRefresh();
                        break;
                    case R.id.rb_exam_middle:
                        examType = 2;
                        smartRefreshLayout.autoRefresh();
                        break;
                    default:
                        break;
                }
        });

        dataRequest(false);     //获取上次保存信息
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.autoRefresh();
        smartRefreshLayout.setOnRefreshListener( (RefreshLayout refreshLayout) -> {
           dataRequest(true);   //获取新的考试信息
        });
    }

    @Override
    public void showExam(BaseEvent<List<BeanExam>> event) {
        runOnUiThread( () -> {
            if (event.getDate().size() == 0) {
                ivEmpty.setVisibility(View.VISIBLE);
                if (event.getCode() == BaseCode.UPDATE) {
                    ToastUtil.showShortToast("考试情况未发布");
                }
            } else {
                ivEmpty.setVisibility(View.GONE);
            }
            if (event.getCode() == BaseCode.UPDATE) {
                smartRefreshLayout.finishRefresh(true);
            }
            adapterExam.setNewData(event.getDate());
        });
    }

    @Override
    public void dataRequest(Boolean isRefresh) {
        if (!SPUtil.INSTANCE().get(getString(R.string.sp_is_login), false)) {
            if (isRefresh) {
                statusLoss();
                return;
            }
            return;
        }
        getExam(isRefresh, examType);
    }

    private void getExam(Boolean isRefresh, int type) {
        if (isRefresh) {
            if (!MyApp.checkNetConnecting()) {
                onError("当前网络不可用");
                return;
            }
        }
        examPresenter.examRequest(isRefresh, type);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_exam_refresh:
                getExam(true, examType);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void statusLoss() {
        smartRefreshLayout.finishRefresh(false);
        startActivityForResult(new Intent(ExamActivity.this, LoginActivity.class), BaseCode.STATUS);
    }

    @Override
    public void onError(String error) {
        smartRefreshLayout.finishRefresh(false);
        super.onError(error);
    }
}
