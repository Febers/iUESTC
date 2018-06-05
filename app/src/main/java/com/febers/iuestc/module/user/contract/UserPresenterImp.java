package com.febers.iuestc.module.user.contract;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.utils.MySharedPreferences;

/**
 * Created by 23033 on 2018/3/27.
 */

public class UserPresenterImp extends UserContract.Presenter{


    public UserPresenterImp(UserContract.View view) {
        super(view);
    }

    @Override
    public void userDetailRequest() {
        Boolean isLogin = MySharedPreferences.getInstance().get(BaseApplication.getContext()
                .getString(R.string.sp_is_login), false);
        if (isLogin) {
            String name = "已登录";
            String id = MySharedPreferences.getInstance().get(BaseApplication.getContext()
                    .getString(R.string.sp_user_id), "");
            if (mView != null) {
                mView.showUserDetail(name, id);
            }
        }
    }
}
