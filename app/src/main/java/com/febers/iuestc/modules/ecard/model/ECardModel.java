/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午7:35
 *
 */

package com.febers.iuestc.modules.ecard.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.febers.iuestc.base.BaseApplication;
import com.febers.iuestc.R;
import com.febers.iuestc.modules.ecard.contract.ECardContract;
import com.febers.iuestc.net.SingletonClient;
import com.febers.iuestc.utils.CustomSharedPreferences;
import com.febers.iuestc.utils.ApiUtil;
import com.febers.iuestc.utils.UnicodeUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ECardModel implements IECardModel{

    private static final String TAG = "ECardModel";
    private ECardContract.Presenter ieCardPresenter;
    private String loginResultMsg;
    private Context context = BaseApplication.getContext();

    public ECardModel(ECardContract.Presenter presenter) {
        ieCardPresenter = presenter;
    }

    private int tryTime = 1;

    /**
     * 登录返回数据如下:
     */
    @Override
    public void loginECardService(final String phone, final String pw){
        new Thread( () -> {
            try {
                OkHttpClient client = SingletonClient.getInstance();
                //模拟喜付登录

                FormBody bodyLogin = new FormBody.Builder()
                        .add("mobile", phone)
                        .add("appversion", "5.2.6")
                        .add("app_version", "5.2.6")
                        .add("osversion", Build.VERSION.RELEASE)
                        .add("os", "android")
                        .add("password", pw)
                        .add("mobiletype", Build.MODEL)
                        .build();
                Request request = new Request.Builder()
                        .url(ApiUtil.XIFU_LOGIN_URL)
                        .post(bodyLogin)
                        .build();
                Response response = client.newCall(request).execute();
                String loginResult = response.body().string();

                String userId = checkLoginResult(loginResult);
                if (userId.equals("null")) {
                    ieCardPresenter.loginResult("登录失败");
                    return;
                }
                CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_user_phone), phone);
                CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_user_pw), pw);
                CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_is_login), true);
                CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_user_id), userId);
                //获取一卡通学号等信息
                FormBody bodyECard = new FormBody.Builder()
                        .add("userid", userId)
                        .add("osversion", Build.VERSION.RELEASE)
                        .add("os", "android")
                        .add("no_ykt_balance", "yes")
                        .add("mobiletype", Build.MODEL)
                        .add("appversion", "5.2.6")
                        .add("app_version", "5.2.6")
                        .build();
                Request requestECard = new Request.Builder()
                        .post(bodyECard)
                        .url(ApiUtil.XIFU_PROFILE_URL)
                        .build();
                Response responseECard = client.newCall(requestECard).execute();
                String result = responseECard.body().string();
                //获取绑定的学号
                String stuNo = getStuNo(result);
                CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_bind_number), stuNo);
                ieCardPresenter.loginResult(loginResultMsg);
            } catch (SocketTimeoutException e) {
                ieCardPresenter.errorResult("网络请求超时");
            }catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                ieCardPresenter.errorResult("登录错误");
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 获取更多
     * appversion	5.2.6
     app_version	5.2.6
     osversion	7.0
     userid	87289
     os	android
     moblietype	MI 5
     no_ykt_balance	yes
     */
    @Override
    public void balanceService() {
        new Thread( () -> {
            OkHttpClient client = SingletonClient.getInstance();
            String stuNo = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_ecard_bind_number), "null");
            String userId = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_ecard_user_id), "null");
            if (stuNo.equals("null") || userId.equals("null")) {
                Log.d(TAG, "resolveECardBalance, return");
                return;
            }
            try {
                //获取一卡通余额以及卡状态
                FormBody bodyBa = new FormBody.Builder()
                        .add("school_id", "1")
                        .add("student_no", stuNo)
                        .build();
                Request requestBan = new Request.Builder()
                        .post(bodyBa)
                        .url(ApiUtil.XIFU_ECARD_BALANCE)
                        .build();
                Response responseBan = client.newCall(requestBan).execute();
                String stBan = responseBan.body().string();
                Boolean b = resolveECardBalance(stBan);
                if (b) {
                    getElecBalance();
                    getRecord();
                }
            } catch (SocketTimeoutException e) {
                ieCardPresenter.errorResult("网络请求超时");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void getElecBalance() {
        new Thread( () -> {
            try {
                OkHttpClient client = SingletonClient.getInstance();
                //获取电费余额
                Request requestElec = new Request.Builder()
                        .url(ApiUtil.XIFU_ELEC_BALANCE)
                        .get()
                        .build();
                Response responseElec = client.newCall(requestElec).execute();
                String stElec = responseElec.body().string();
                resolveElecBalance(stElec);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void getRecord() {
        String stuNo = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_ecard_bind_number), "null");
        new Thread( () -> {
            try {
                OkHttpClient client = SingletonClient.getInstance();
                //获取消费记录
                FormBody bodyRecord = new FormBody.Builder()
                        .add("school_id", "1")
                        .add("stuempno", stuNo)
                        .build();
                Request requestRecord = new Request.Builder()
                        .url(ApiUtil.XIFU_RECORD_URL)
                        .post(bodyRecord)
                        .build();
                Response responseRecord = client.newCall(requestRecord).execute();
                String stRecord = responseRecord.body().string();

                getRecordList(stRecord);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 检查登录之后返回的信息并返回userid
     */
    private String checkLoginResult(String sourceCode) {
        String userId;
        Gson gson = new Gson();
        BeanLoginECardResult resultBean = gson.fromJson(sourceCode, BeanLoginECardResult.class);
        BeanLoginECardResult.data data = resultBean.getData();
        loginResultMsg = resultBean.getRetmsg();
        if (resultBean.getRetmsg().contains("成功")) {
            userId = data.getUserid();
        } else {
            userId = "null";
        }
        return userId;
    }

    /**
     * 获取绑定的学号
     */
    private String getStuNo(String sourceCode) {
        Gson gson = new Gson();
        BeanECardDetail resultBean = gson.fromJson(sourceCode, BeanECardDetail.class);
        BeanECardDetail.data data = resultBean.getData();
        if (data.getStudentno() != null) {
            return data.getStudentno();
        }
        return "null";
    }

    /**
     * 解析并发送消息
     */
    private Boolean resolveECardBalance(String sourceCode) {
        try {
            Gson gson = new Gson();
            BeanECardBalance beanECardBalance = gson.fromJson(sourceCode, BeanECardBalance.class);
            BeanECardBalance.data data = beanECardBalance.getData();
            if (beanECardBalance.getRetmsg().contains("未登录")) {
                if (tryTime == 1) {
                    if (!reLogin()) {
                        return false;
                    } else {
                        balanceService();
                    }
                    tryTime++;
                }
            } else if (!beanECardBalance.getRetmsg().contains("成功")) {
                ieCardPresenter.errorResult("获取一卡通余额失败");
                return false;
            }
            //保存到本地
            CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_ec_balance), data.getBalance());
            ieCardPresenter.eCardBalanceResult(data.getBalance());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 解析并发送消息
     */
    private void resolveElecBalance(String sourceCode) {
        try {
            Gson gson = new Gson();
            BeanElecBalance beanElecBalance = gson.fromJson(sourceCode, BeanElecBalance.class);
            if (!beanElecBalance.getMsg().contains("成功")) {
                ieCardPresenter.errorResult("获取电费出错");
                return;
            }
            BeanElecBalance.data data = beanElecBalance.getData();
            //保存到本地
            CustomSharedPreferences.getInstance().put(context.getString(R.string.sp_ecard_el_balance), data.getAmount());
            ieCardPresenter.elecBalanceResult(data.getAmount());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析并发送消息
     */
    private void getRecordList(String sourceCode) {
        BeanECardPayRecord payRecord = new Gson().fromJson(sourceCode, BeanECardPayRecord.class);
        if(payRecord.getRetmsg().contains("登录")) {
            return;
        }
        List<BeanECardPayRecord.data.consumes> list = resolveRecord(sourceCode, 2);
        if (list == null) {
            return;
        }
        //如果解析到list，则将sourceCode保存到本地
        SharedPreferences spLocalRecord = BaseApplication.getContext().getSharedPreferences("local_pay_record", 0);
        SharedPreferences.Editor editor = spLocalRecord.edit();
        editor.putString("record", sourceCode);
        editor.commit();
        ieCardPresenter.recordResult(list);
    }

    @Override
    public void localDataService(int recordSize) {
        if (!CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_ecard_is_login), false)) {
            Log.d(TAG, "localDataService: 2");
            return;
        }
        String localBalance = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_ecard_ec_balance), "0.0");
        String localElecBalance = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_ecard_el_balance), "0.0");
        ieCardPresenter.eCardBalanceResult(localBalance);
        ieCardPresenter.elecBalanceResult(localElecBalance);

        SharedPreferences spLocalRecord = BaseApplication.getContext().getSharedPreferences("local_pay_record", 0);
        String localRecord = spLocalRecord.getString("record", "null");
        if (localRecord.equals("null")) {
            return;
        }
        List<BeanECardPayRecord.data.consumes> list = resolveRecord(localRecord, recordSize);
        ieCardPresenter.recordResult(list);
    }

    /**
     * 解析消费详情字符串，然后以list的形式返回
     * 使用json解析会出现很多问题
     * 因为返回的数据包含了很多不规范的字符
     */
    private List<BeanECardPayRecord.data.consumes> resolveRecord(String sourceCode, int size) {
        List<BeanECardPayRecord.data.consumes> consumesList = new ArrayList<>();
        try {
            //获取消费情况的字符串
            int start = sourceCode.indexOf("[");
            int end = sourceCode.indexOf("]");
            String stConsumes = sourceCode.substring(start+1, end);
            int ps = 0, pe = 0;
            //只解析15条数据
            outer:
            for (int i = 0; i < size; i++) {
                ps = stConsumes.indexOf("{", ps);
                pe = stConsumes.indexOf("}", pe);
                String single = stConsumes.substring(ps+1, pe);
                try {
                    //消费时间
                    int start_3 = single.indexOf(":");
                    int end_3 = single.indexOf(",");
                    String s_3 = single.substring(start_3+1, end_3);
                    //消费后余额
                    int start_4 = single.indexOf(":", end_3+1);
                    int end_4 = single.indexOf(",", end_3+1);
                    String s_4 = single.substring(start_4+1, end_4);
                    //消费金额
                    int start_5 = single.indexOf(":", end_4+1);
                    int end_5 = single.indexOf(",", end_4+1);
                    String s_5 = single.substring(start_5+1, end_5);
                    //消费地点
                    int start_6 = single.indexOf(":", end_5+1);
                    int end_6 = single.indexOf(",", end_5+1);
                    String s_6 = single.substring(start_6+1, end_6);
                    s_6 = UnicodeUtil.UnicodeToChinese(s_6);

                    BeanECardPayRecord.data.consumes consume = new BeanECardPayRecord.data.consumes();
                    consume.setTranstime(s_3);
                    consume.setAftbala(s_4);
                    consume.setAmount(s_5);
                    consume.setPosition(s_6);
                    consumesList.add(consume);
                } catch (Exception e) {
                    break outer;
                }
                ps+=1;
                pe+=1;
            }
        } catch (StringIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        return consumesList;
    }

    /**
     * 在登录状态丢失时，重新登录
     * @return
     */
    private Boolean reLogin() {
        Log.d(TAG, "reloginService: ");
        if (!CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_ecard_is_login), false)) {
            return false;
        }
        String phone = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_ecard_user_phone), "");
        String pw = CustomSharedPreferences.getInstance().get(context.getString(R.string.sp_ecard_user_pw), "");
        OkHttpClient client = SingletonClient.getInstance();
        //模拟喜付登录
        String loginResult = "";
        FormBody bodyLogin = new FormBody.Builder()
                .add("mobile", phone)
                .add("appversion", "5.2.6")
                .add("app_version", "5.2.6")
                .add("osversion", Build.VERSION.RELEASE)
                .add("os", "android")
                .add("password", pw)
                .add("mobiletype", Build.MODEL)
                .build();
        Request request = new Request.Builder()
                .url(ApiUtil.XIFU_LOGIN_URL)
                .post(bodyLogin)
                .build();
        try {
            Response response = client.newCall(request).execute();
            loginResult = response.body().string();
        } catch (SocketTimeoutException e) {
            ieCardPresenter.errorResult("网络请求超时");
        } catch (IOException e) {
            ieCardPresenter.errorResult("获取账号状态出错");
            e.printStackTrace();
            return false;
        }
        loginResult = UnicodeUtil.UnicodeToChinese(loginResult);
        if (loginResult.contains("成功")) {
            return true;
        }
        ieCardPresenter.errorResult("账号状态改变，请重新登录");
        return false;
    }
}
