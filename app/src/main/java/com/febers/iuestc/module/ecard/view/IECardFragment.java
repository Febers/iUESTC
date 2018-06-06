package com.febers.iuestc.module.ecard.view;

import com.febers.iuestc.module.ecard.model.BeanECardPayRecord;

import java.util.List;

public interface IECardFragment {
    void showLoginResult(String msg);
    void showECardBalance(String balance);
    void showElecBalance(String balance);
    void showPayRecord(List<BeanECardPayRecord.data.consumes> consumesList);
    void showErrorMsg(String msg);
}
