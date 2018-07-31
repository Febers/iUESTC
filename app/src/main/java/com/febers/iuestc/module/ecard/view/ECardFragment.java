/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-7 下午4:53
 *
 */

package com.febers.iuestc.module.ecard.view;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterRecord;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.module.ecard.presenter.ECardContract;
import com.febers.iuestc.entity.BeanECardPayRecord;
import com.febers.iuestc.module.ecard.presenter.ECardPresenterImpl;
import com.febers.iuestc.util.CustomSharedPreferences;
import com.febers.iuestc.util.LogoutUtil;
import com.febers.iuestc.view.custom.CustomLoginDialog;
import com.febers.iuestc.view.custom.CustomProgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

public class ECardFragment extends BaseFragment implements ECardContract.View{

    private static final String TAG = "ECardFragment";

    private ECardContract.Presenter mPresenter = new ECardPresenterImpl(this);
    private Boolean isLogin = CustomSharedPreferences.getInstance()
            .get(mContext.getString(R.string.sp_ecard_is_login), false);
    private List<BeanECardPayRecord.data.consumes> list;
    private CustomProgressDialog progressDialogRefresh;
    private CustomProgressDialog progressDialogLogin;
    private SmartRefreshLayout mSmartRefreshLayout;
    private CardView cvECardBalance, cvECardRecord;
    private CustomLoginDialog mLoginDialog;
    private AdapterRecord adapterRecord;
    private RecyclerView mRVPayRecord;
    private TextView mTVECardBalance;
    private TextView mTVElecBalance;
    private TextInputEditText tieId;
    private String stId;
    private String stPw;

    @Override
    protected int setContentView() {
        return R.layout.fragment_ecard;
    }

    @Override
    protected void lazyLoad() {
        loadLocalDate();
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_ecard_fragment);
        toolbar.inflateMenu(R.menu.ecard_menu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        LinearLayoutManager llmPayRecord = new LinearLayoutManager(getContext());
        llmPayRecord.setSmoothScrollbarEnabled(true);
        mRVPayRecord = findViewById(R.id.rv_ecard_record);
        mRVPayRecord.setLayoutManager(llmPayRecord);
        mRVPayRecord.setOnClickListener(v ->
            startActivity(new Intent(getActivity(), ECardRecrodActivity.class)));

        cvECardRecord = findViewById(R.id.cv_ecard_record);
        if (!isLogin) {
            cvECardRecord.setVisibility(View.GONE);
        }
        cvECardRecord.setOnClickListener( v ->
            startActivity(new Intent(getActivity(), ECardRecrodActivity.class))
        );
        mTVECardBalance = findViewById(R.id.tv_ecard_banlance);
        mTVElecBalance = findViewById(R.id.tv_electric_banlance);
        cvECardBalance = findViewById(R.id.cv_ecard_balance);
        cvECardBalance.setOnClickListener( (v) -> {
            if (!isLogin) {
                showLoginDialog();
            }
        });
        mSmartRefreshLayout = findViewById(R.id.srl_ecard);
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener( (refreshLayout) -> {
            if (!isLogin) {
                mSmartRefreshLayout.finishRefresh(false);
                return;
            }
            getECardBalance(true);
        });
    }

    private void showLoginDialog() {
        if (mLoginDialog == null) {
            mLoginDialog = new CustomLoginDialog(getActivity());
            tieId = mLoginDialog.getTieUserId();
            mLoginDialog.setTitle("绑定喜付账号");
            mLoginDialog.setOnLoginListener( (v) -> {
                stId = mLoginDialog.getStId();
                stPw = mLoginDialog.getStPw();
                switch (v.getId()) {
                    case R.id.bt_dialog_login_enter:
                        if (!BaseApplication.checkNetConnecting()) {
                            onError("当前网络不可用");
                            return;
                        }
                        if (stId.equals("") || stPw.equals("")) {
                            onError("请输入正确的格式");
                            return;
                        }
                        loginECard(stId, stPw);
                        showLoginProgressDialog();
                        break;
                    case R.id.bt_dialog_login_cancel:
                        mLoginDialog.dismiss();
                        break;
                    default:
                        break;
                }
            });
        }
        getActivity().runOnUiThread( () ->
                mLoginDialog.show()
        );
        progressDialogRefresh = new CustomProgressDialog(getActivity(), "正在刷新数据");
    }

    private void loginECard(String stId, String stPw) {
        mPresenter.loginECardRequest(stId, stPw);
    }

    @Override
    public void showLoginResult(final String msg) {
        dismissLoginProgressDialog();
        if (msg.contains("成功")) {
            if (mLoginDialog !=null) {
                getActivity().runOnUiThread( () ->
                        mLoginDialog.dismiss()
                );
            }
            getECardBalance(false);
            return;
        }
        if (mLoginDialog !=null) {
            getActivity().runOnUiThread( () ->
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
            );
        }
    }

    private void getECardBalance(Boolean isRefresh) {
        if (!BaseApplication.checkNetConnecting()) {
            onError("当前网络不可用");
            mSmartRefreshLayout.finishRefresh(false);
            return;
        }
        if (isRefresh) {
            showRefreshProgressDialog();
        }
        mPresenter.balanceRequest();
    }

    @Override
    public void showECardBalance(final String balance) {
        dismissRefreshProgressDialog();
        getActivity().runOnUiThread( () -> {
                mSmartRefreshLayout.finishRefresh(true);
                mTVECardBalance.setText(balance);
        });
    }

    @Override
    public void showElecBalance(final String balance) {
        getActivity().runOnUiThread( () ->
                mTVElecBalance.setText(balance)
        );
    }

    @Override
    public void showPayRecord(List<BeanECardPayRecord.data.consumes> consumesList) {
        list = consumesList;
        if (list == null) {
            return;
        }
        getActivity().runOnUiThread( () -> {
                cvECardRecord.setVisibility(View.VISIBLE);
                adapterRecord = new AdapterRecord(list);
                mRVPayRecord.setAdapter(adapterRecord);
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //隐藏再show的时候会移动到页面底部，需要手动跳到顶部，原因不明
//        nestedScrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                nestedScrollView.scrollTo(0,0);
//            }
//        });
        //上述问题在根布局添加android:descendantFocusability="blocksDescendants解决
    }

    private void loadLocalDate() {
        mPresenter.localDataRequest(2);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.ecard_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_ecard_logout:
                if (!isLogin) {
                    break;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("您是否要注销登录?")
                        .setPositiveButton("注销", (dialogInterface, i) ->
                                logout()
                        )
                        .setNegativeButton("取消", (dialogInterface, i) -> {

                        })
                        .setCancelable(false);
                builder.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        LogoutUtil.logoutECard();
        showECardBalance("未登录");
        showElecBalance("");
        if (list != null) {
            list.clear();
        }
        if (adapterRecord!=null) {
            adapterRecord.notifyDataSetChanged();
        }
        cvECardRecord.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        mSmartRefreshLayout.finishRefresh(false);
        dismissLoginProgressDialog();
        dismissRefreshProgressDialog();
        if (error.contains("重新登录")) {
            logout();
        }
        getActivity().runOnUiThread( () ->
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show()
        );
    }

    private void showRefreshProgressDialog() {
        if (progressDialogRefresh == null) {
            progressDialogRefresh = new CustomProgressDialog(getActivity(), "正在获取数据");
        }
        getActivity().runOnUiThread( () ->
                progressDialogRefresh.show()
        );
    }

    private void dismissRefreshProgressDialog() {
        if (progressDialogRefresh != null) {
            getActivity().runOnUiThread( () ->
                    progressDialogRefresh.dismiss()
            );
        }
    }

    private void showLoginProgressDialog() {
        if (progressDialogLogin == null) {
            progressDialogLogin = new CustomProgressDialog(getActivity(), "正在登录");
        }
        getActivity().runOnUiThread( () ->
                progressDialogLogin.show()
        );
    }

    private void dismissLoginProgressDialog() {
        if (progressDialogLogin != null) {
            getActivity().runOnUiThread( () ->
                    progressDialogLogin.dismiss()
            );
        }
    }
}
