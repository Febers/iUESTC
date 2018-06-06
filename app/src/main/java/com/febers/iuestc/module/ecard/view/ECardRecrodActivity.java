package com.febers.iuestc.module.ecard.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.febers.iuestc.R;
import com.febers.iuestc.adapter.AdapterRecord;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.module.ecard.contract.ECardContract;
import com.febers.iuestc.module.ecard.model.BeanECardPayRecord;
import com.febers.iuestc.module.ecard.contract.ECardPresenterImp;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

import java.util.List;

public class ECardRecrodActivity extends BaseActivity implements ECardContract.View{

    private Toolbar toolbar;
    private RecyclerView rvECardeRecord;
    private AdapterRecord adapterRecord;
    private ECardContract.Presenter eCardPresenter = new ECardPresenterImp(this);

    @Override
    protected int setView() {
        SlidrConfig config = new SlidrConfig.Builder()
                .edge(true)
                .build();
        Slidr.attach(this, config);
        return R.layout.activity_ecard_recrod;
    }

    @Override
    protected void findViewById() {
        toolbar = findViewById(R.id.tb_ecard_record);
        rvECardeRecord = findViewById(R.id.rv_ecard_record_activity);
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvECardeRecord.setLayoutManager(new LinearLayoutManager(this));
        new Thread(()-> {
            getRecord();
        }).start();
    }

    private void getRecord() {
        eCardPresenter.localDataRequest(100);
    }

    @Override
    public void showLoginResult(String msg) {
    }

    @Override
    public void showECardBalance(String balance) {
    }

    @Override
    public void showElecBalance(String balance) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPayRecord(List<BeanECardPayRecord.data.consumes> consumesList) {
        runOnUiThread(()-> {
            adapterRecord = new AdapterRecord(consumesList);
            rvECardeRecord.setAdapter(adapterRecord);
        });
    }

    @Override
    public void onError(String error) {

    }
}
