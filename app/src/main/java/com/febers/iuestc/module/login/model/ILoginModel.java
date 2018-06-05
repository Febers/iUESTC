package com.febers.iuestc.module.login.model;

/**
 * Created by 23033 on 2018/3/25.
 */

public interface ILoginModel {
    void loginService(String id , String pw) throws Exception;
    Boolean reloginService() throws Exception;
}
