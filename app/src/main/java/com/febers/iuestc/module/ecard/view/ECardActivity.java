/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-30 下午1:42
 *
 */

package com.febers.iuestc.module.ecard.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.MyApplication;
import com.febers.iuestc.view.adapter.AdapterUser;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.entity.BeanEduECard;
import com.febers.iuestc.entity.BeanUserItem;
import com.febers.iuestc.module.ecard.presenter.ECardContract;
import com.febers.iuestc.module.ecard.presenter.ECardJSInterface;
import com.febers.iuestc.module.login.view.LoginActivity;
import com.febers.iuestc.net.WebViewConfigure;
import com.febers.iuestc.util.WebViewUtil;
import com.febers.iuestc.view.custom.CustomListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 直接使用userDetail的Adapter
 */
public class ECardActivity extends BaseSwipeActivity implements ECardContract.View{
    private static final String TAG = "ECardActivity";

    private List<BeanUserItem> ecardItemList = new ArrayList<>();
    private AdapterUser adapterECard;
    private WebView webView;
    private TextView tvBalance;
    private Button btnLost;
    private SmartRefreshLayout smartRefreshLayout;
    private BeanUserItem item1, item2, item3, item4;

    @Override
    protected int setView() {
        return R.layout.activity_ecard;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_ecard);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        smartRefreshLayout = findViewById(R.id.srl_ecard);
        tvBalance = findViewById(R.id.tv_ecard__act_bal);
        btnLost = findViewById(R.id.btn_ecard_lost);
        webView = findViewById(R.id.web_ecard);
        CustomListView listView = findViewById(R.id.lv_ecard_item);
        adapterECard = new AdapterUser(this, R.layout.item_user_detail, initList());
        listView.setAdapter(adapterECard);
        btnLost.setOnClickListener((View v) -> {

        });
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.autoRefresh();
        smartRefreshLayout.setOnRefreshListener((RefreshLayout refreshLayout) -> {
                dateRequest(true);
        });
        dateRequest(false);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        ECardJSInterface eCardJSInterface = new ECardJSInterface(this);
        if (!isRefresh) {
            eCardJSInterface.localDateRequest();
            return;
        }
        if (!MyApplication.checkNetConnecting()) {
            onError("当前网络无连接");
            return;
        }

        new WebViewConfigure.Builder(this, webView)
                .setOpenUrlOut(false)
                .setNoImgClient()
                .setProcessHtml(true, eCardJSInterface, "HTMLOUT")
                .builder();
        webView.loadUrl("http://ecard.uestc.edu.cn/");
    }

    @Override
    public void showHomePageResult(BaseEvent event) {
        runOnUiThread(()-> {
            webView.loadUrl("http://ecard.uestc.edu.cn/web/guest/personal");
        });
    }

    @Override
    public void showDetailPageResult(BaseEvent<BeanEduECard> event) {
        if (event.getCode() == BaseCode.ERROR) {
            onError("获取一卡通信息失败");
            return;
        }
        if (event.getCode() == BaseCode.UPDATE) {
            smartRefreshLayout.finishRefresh(true);
        }

        BeanEduECard eCard = event.getDate();
        item1.setValue(eCard.getNumber());
        item2.setValue(eCard.getStatus());
        item3.setValue(eCard.getValueDate());
        item4.setValue(eCard.getNoGet());
        runOnUiThread( ()-> {
            tvBalance.setText(eCard.getBalance());
            adapterECard.notifyDataSetChanged();
        });
    }

    @Override
    public void statusToFail() {
        smartRefreshLayout.finishRefresh(false);
        startActivityForResult(new Intent(ECardActivity.this, LoginActivity.class), BaseCode.STATUS);
    }

    @Override
    protected void onDestroy() {
        WebViewUtil.destroyWebView(webView);
        super.onDestroy();
    }

    @Override
    public void onError(String error) {
        smartRefreshLayout.finishRefresh(false);
        super.onError(error);
    }

    private List<BeanUserItem> initList() {
        item1 = new BeanUserItem("卡号", "...");
        item2 = new BeanUserItem("状态", "...");
        item3 = new BeanUserItem("有效期", "...");
        item4 = new BeanUserItem("未领取", "...");
        ecardItemList.clear();
        ecardItemList.add(item1);
        ecardItemList.add(item2);
        ecardItemList.add(item3);
        ecardItemList.add(item4);
        return ecardItemList;
    }
}
