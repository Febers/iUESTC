package com.febers.iuestc.module.login.model;

import android.content.Context;
import android.util.Log;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.module.login.contract.LoginContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.utils.CustomSharedPreferences;
import com.febers.iuestc.utils.ApiUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 23033 on 2018/3/25.
 */

public class LoginModel implements ILoginModel{

    private static final String TAG = "LoginModel";
    private String mLt;
    private String mExecution;
    private String mId;
    private String mPw;
    private String mLoginHtml;
    private OkHttpClient client;
    private LoginContract.Presenter loginPresenter;
    private Context context = BaseApplication.getContext();

    public LoginModel(LoginContract.Presenter presenter) {
        this.loginPresenter = presenter;
    }

    public LoginModel() {}

    @Override
    public void loginService(String id, String pw) {
        mId = id;
        mPw = pw;
        new Thread(() ->{
            try {
                client = SingletonClient.getInstance();
                Request request = new Request.Builder()
                        .url(ApiUtil.BEFORE_LOGIN_URL)
                        .build();
                Response response = client.newCall(request).execute();
                String loginHtml = response.body().string();

                Document document = Jsoup.parse(loginHtml);
                Elements elements = document.select("input[type=hidden]");
                Element lt = elements.get(0);
                mLt = lt.attr("value");
                Element execution = elements.get(2);
                mExecution = execution.attr("value");

                loginWithLAndE(mId, mPw, mLt, mExecution);
            } catch (SocketTimeoutException e) {
                loginPresenter.loginResult(LoginResult.LOGIN_TIMEOUT);
                e.printStackTrace();
            }catch (IOException e) {
                loginPresenter.loginResult(LoginResult.LOGIN_CONDITION_FAIL);
                e.printStackTrace();
                return;
            } catch (IndexOutOfBoundsException e) {
                loginPresenter.loginResult(LoginResult.LOGIN_CONDITION_FAIL);
                e.printStackTrace();
                return;
            }
        }).start();
    }

    /**
     * 在已经登录的情况下重新登录，以进行后续操作
     * 切记不能在主线程调用
     */
    @Override
    public Boolean reloginService() {
        Boolean success = false;
        if (!CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_is_login), false)) {
            return false;
        }

        mId = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_user_id), "");
        mPw = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_user_pw), "");
        try {
            client = SingletonClient.getInstance();
            Request request = new Request.Builder()
                    .url(ApiUtil.BEFORE_LOGIN_URL)
                    .build();
            Response response = client.newCall(request).execute();
            String loginHtml = response.body().string();

            Document document = Jsoup.parse(loginHtml);
            Elements elements = document.select("input[type=hidden]");
            Element lt = elements.get(0);
            mLt = lt.attr("value");
            Element execution = elements.get(2);
            mExecution = execution.attr("value");

            success = loginWithLAndE(mId, mPw, mLt, mExecution);
        } catch (SocketTimeoutException e) {
            return false;
        }catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return success;
    }

    private Boolean loginWithLAndE(String id, String pw, String lt, String exec) {
        FormBody formBody = new FormBody.Builder()
                .add("username",id)
                .add("password",pw)
                .add("lt",lt)
                .add("dllt","userNamePasswordLogin")
                .add("execution",exec)
                .add("_eventId","submit")
                .add("rmShown","1")
                .build();
        Request request = new Request.Builder()
                .post(formBody)
                .url(ApiUtil.LOGIN_URL)
                .build();
        OkHttpClient client = SingletonClient.getInstance();
        try {
            Response response = client.newCall(request).execute();
            mLoginHtml = response.body().string();

        } catch (SocketTimeoutException e) {
            loginPresenter.loginResult(LoginResult.LOGIN_TIME_OUT);
            return false;
        } catch (IOException e){
            loginPresenter.loginResult(LoginResult.LOGIN_CONDITION_FAIL);
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            loginPresenter.loginResult(LoginResult.LOGIN_CONDITION_FAIL);
            e.printStackTrace();
            return false;
        }
        return checkLogin(mLoginHtml);
    }

    private Boolean checkLogin(String html) {
        try {
            Document document = Jsoup.parse(html);
            Elements elements = document.select("li");
            if (elements.size() == 0) {
                if (loginPresenter != null) {
                    loginPresenter.loginResult(LoginResult.LOGIN_CONDITION_FAIL);
                }
                return false;
            }
            for(Element e:elements) {
                if (e.text().contains("个人") || e.text().contains("教务")){
                    CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_user_id), mId);
                    CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_user_pw), mPw);
                    CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_is_login), true);
                    getDetailForName();
                    if (loginPresenter != null) {
                        loginPresenter.loginResult(LoginResult.LOGIN_SUCCESS);
                    }
                    return true;
                }
            }
            if (html.contains("有误")) {
                if (loginPresenter != null) {
                    loginPresenter.loginResult(LoginResult.LOGIN_PW_FAIL);
                    return false;
                }
            }
        } catch (Exception e) {
            if (loginPresenter != null) {
                loginPresenter.loginResult(LoginResult.LOGIN_RESOLVE_RESULT_FAIL);
                return false;
            }
        }
        return false;
    }

    private void getDetailForName() {
        new Thread( () -> {
            try {
                Request request = new Request.Builder()
                        .url("http://eams.uestc.edu.cn/eams/stdDetail.action")
                        .build();
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                Document document = Jsoup.parse(result);
                Elements els = document.select("div[id=\"tabPage1\"]");
                if (els.size() == 0) {
                    return;
                }
                String[] detail = els.get(0).text().split(" ");
                CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_user_name), detail[4]);
            } catch(Exception e) {
                e.printStackTrace();
                Log.d(TAG, "getDetailForName: 获取姓名失败");
            }
        }).start();
    }
}
