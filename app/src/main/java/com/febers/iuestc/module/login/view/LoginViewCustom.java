/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-15 上午11:05
 *
 */

package com.febers.iuestc.module.login.view;

import android.support.design.widget.TextInputEditText;
import android.widget.Button;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.entity.BeanLoginStatus;
import com.febers.iuestc.module.login.presenter.LoginContract;
import com.febers.iuestc.module.login.presenter.LoginPresenterImpl;

import org.greenrobot.eventbus.EventBus;

public class LoginViewCustom extends BaseFragment implements LoginContract.View {

    private LoginContract.Presenter loginPresenter;
    private TextInputEditText editUserId;
    private TextInputEditText editUserPw;
    private Button btnLogin;

    @Override
    protected int setContentView() {
        return R.layout.fragment_login_custom;
    }

    @Override
    protected void findView() {
        editUserId = findViewById(R.id.tie_user_id);
        editUserPw = findViewById(R.id.tie_user_pw);
        btnLogin = findViewById(R.id.btn_activity_login);
    }

    @Override
    protected void initView() {
        loginPresenter = new LoginPresenterImpl(this);
        btnLogin.setOnClickListener(v -> {
            showProgressDialog();
            if (editUserId.getText().toString().trim().isEmpty() || editUserPw.getText().toString().trim().isEmpty()) {
                onError("请输入有效内容");
            } else {
                loginPresenter.loginRequest(editUserId.getText().toString(), editUserPw.getText().toString());
            }
        });
    }

    /**
     * 展示登录结果
     * @param event 当data的结果为账户或密码错误时，不做任何处理
     * 否则 通知Activity进行相应的处理
     */
    @Override
    public void loginResult(BaseEvent<String> event) {
        dismissProgressDialog();
        if (event.getDate().contains("帐号或密码错误")) {
            onError(event.getDate());
            return;
        }
        EventBus.getDefault().post(new BaseEvent<>(event.getCode(), new BeanLoginStatus(true)));
    }

    @Override
    public boolean onBackPressedSupport() {
        getActivity().finish();
        return true;
    }
}
