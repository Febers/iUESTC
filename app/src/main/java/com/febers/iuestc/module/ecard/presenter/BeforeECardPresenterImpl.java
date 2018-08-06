/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.ecard.presenter;

import com.febers.iuestc.entity.BeanECardPayRecord;
import com.febers.iuestc.module.ecard.model.BeforeECardModelBefore;
import com.febers.iuestc.module.ecard.model.BeforeIECardModel;

import java.util.List;

public class BeforeECardPresenterImpl extends BeforeECardContract.Presenter{

    private static final String TAG = "BeforeECardPresenterImpl";

    private BeforeIECardModel beforeIeCardModel = new BeforeECardModelBefore(this);

    public BeforeECardPresenterImpl(BeforeECardContract.View view) {
        super(view);
    }

    @Override
    public void loginECardRequest(String phone, String pw) {
        try{
            beforeIeCardModel.loginECardService(phone, pw);
        } catch (Exception e) {
            e.printStackTrace();
            mView.onError("绑定账号出错");
        }
    }

    @Override
    public void balanceRequest() {
        try {
            beforeIeCardModel.balanceService();
        } catch (Exception e) {
            e.printStackTrace();
            mView.onError("获取一卡通数据出错");
        }
    }

    @Override
    public void localDataRequest(int recordSize) {
        beforeIeCardModel.localDataService(recordSize);
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
