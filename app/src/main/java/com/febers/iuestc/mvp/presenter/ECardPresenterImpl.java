/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-13 下午2:39
 *
 */

package com.febers.iuestc.mvp.presenter;

import com.febers.iuestc.entity.BeanECardPayRecord;
import com.febers.iuestc.mvp.model.ECardModel;
import com.febers.iuestc.mvp.model.IECardModel;

import java.util.List;

public class ECardPresenterImpl extends ECardContract.Presenter{

    private static final String TAG = "ECardPresenterImpl";

    private IECardModel ieCardModel = new ECardModel(this);

    public ECardPresenterImpl(ECardContract.View view) {
        super(view);
    }

    @Override
    public void loginECardRequest(String phone, String pw) {
        try{
            ieCardModel.loginECardService(phone, pw);
        } catch (Exception e) {
            e.printStackTrace();
            mView.onError("绑定账号出错");
        }
    }

    @Override
    public void balanceRequest() {
        try {
            ieCardModel.balanceService();
        } catch (Exception e) {
            e.printStackTrace();
            mView.onError("获取一卡通数据出错");
        }
    }

    @Override
    public void localDataRequest(int recordSize) {
        ieCardModel.localDataService(recordSize);
    }

    @Override
    public void eCardBalanceResult(String result) {
        if (mView == null) {
            return;
        }
        mView.showECardBalance(result);
    }

    @Override
    public void loginResult(String result) {
        mView.showLoginResult(result);
    }

    @Override
    public void elecBalanceResult(String result) {
        if (mView == null) {
            return;
        }
        mView.showElecBalance(result);
    }

    @Override
    public void recordResult(List<BeanECardPayRecord.data.consumes> consumesList) {
        //消费记录
        if (consumesList == null || mView == null) {
            return;
        }
        mView.showPayRecord(consumesList);
    }
}
