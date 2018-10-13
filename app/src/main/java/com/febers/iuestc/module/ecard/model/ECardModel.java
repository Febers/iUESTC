/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-4 下午8:03
 *
 */

package com.febers.iuestc.module.ecard.model;

import android.util.Log;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseModel;
import com.febers.iuestc.entity.BeanEduECard;
import com.febers.iuestc.module.ecard.presenter.ECardContract;
import com.febers.iuestc.module.ecard.presenter.ECardJSInterface;
import com.febers.iuestc.util.CustomSPUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ECardModel extends BaseModel implements ECardContract.Model {

    private static final String TAG = "ECardModel";

    private ECardJSInterface eCardJSInterface;

    public ECardModel(ECardJSInterface eCardJSInterface) {
        this.eCardJSInterface = eCardJSInterface;
    }

    @Override
    public void resolveHtmlService(String html) {
        if (html.contains("身份认证")) {
            eCardJSInterface.loginStatusFail();
            return;
        }
        if (html.contains("没有所需角色")) {
            Log.i(TAG, "resolveHtmlService: 没有portlet");
            eCardJSInterface.DetailPageResult(new BaseEvent<>(BaseCode.ERROR, new BeanEduECard()));
            return;
        }
        if (html.contains("重复登录")) {
            return;
        }
        if (html.contains("通知公告")) {
            eCardJSInterface.homePageResult(new BaseEvent(0, ""));
            return;
        }
        if (html.contains("余额")) {
            new Thread(()-> {
                try {
                    StringBuilder stringBuilder = new StringBuilder(html);

                    int beginBalance = stringBuilder.indexOf("div class=\"balance\"");
                    int endBalance = stringBuilder.indexOf("script type", beginBalance);
                    String stBalance = stringBuilder.substring(beginBalance, endBalance);
                    Document document = Jsoup.parse(stBalance);
                    Elements elements = document.select("td");

                    BeanEduECard eCard = new BeanEduECard();
                    for (int i = 0; i < elements.size(); i++) {
                        String item = elements.get(i).text();
                        String[] itemArr = item.split("：");
                        if (item.contains("卡号")) {
                            eCard.setNumber(itemArr[1]);
                            continue;
                        }
                        if (item.contains("状态")) {
                            eCard.setStatus(itemArr[1]);
                            continue;
                        }
                        if (item.contains("余额")) {
                            eCard.setBalance("￥"+itemArr[1].replace("元", ""));
                            continue;
                        }
                        if (item.contains("卡有效期")) {
                            eCard.setValueDate(itemArr[1]);
                            continue;
                        }
                        if (item.contains("未领取")) {
                            eCard.setNoGet(itemArr[1]);
                            continue;
                        }
                    }
                    eCardJSInterface.DetailPageResult(new BaseEvent<>(BaseCode.UPDATE, eCard));
                    saveECard(eCard);
                } catch (Exception e) {
                    eCardJSInterface.DetailPageResult(new BaseEvent<>(BaseCode.ERROR, new BeanEduECard()));
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public void localDataService() {
        BeanEduECard eCard = new BeanEduECard();
        eCard.setBalance(CustomSPUtil.getInstance().get(getStringById(R.string.sp_ecard_balance), "..."));
        eCard.setNumber(CustomSPUtil.getInstance().get(getStringById(R.string.sp_ecard_number), "..."));
        eCard.setStatus(CustomSPUtil.getInstance().get(getStringById(R.string.sp_ecard_status), "..."));
        eCard.setValueDate(CustomSPUtil.getInstance().get(getStringById(R.string.sp_ecard_value_date), "..."));
        eCard.setNoGet(CustomSPUtil.getInstance().get(getStringById(R.string.sp_ecard_no_get), "..."));
        eCardJSInterface.DetailPageResult(new BaseEvent<>(BaseCode.LOCAL, eCard));
    }

    private void saveECard(BeanEduECard eCard) {
        new Thread(()-> {
            CustomSPUtil.getInstance().put(getStringById(R.string.sp_ecard_balance), eCard.getBalance());
            CustomSPUtil.getInstance().put(getStringById(R.string.sp_ecard_number), eCard.getNumber());
            CustomSPUtil.getInstance().put(getStringById(R.string.sp_ecard_status), eCard.getStatus());
            CustomSPUtil.getInstance().put(getStringById(R.string.sp_ecard_value_date), eCard.getValueDate());
            CustomSPUtil.getInstance().put(getStringById(R.string.sp_ecard_no_get), eCard.getNoGet());
        }).start();
    }
}
