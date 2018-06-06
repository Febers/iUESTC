package com.febers.iuestc.module.ecard.model;

public interface IECardModel {
    void loginECardService(String phone, String pw) throws Exception;
    void balanceService() throws Exception;
    void localDataService(int recordSize);
}
