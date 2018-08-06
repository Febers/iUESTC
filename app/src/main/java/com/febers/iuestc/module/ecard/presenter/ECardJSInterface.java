/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-4 下午8:01
 *
 */

package com.febers.iuestc.module.ecard.presenter;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanEduECard;
import com.febers.iuestc.module.ecard.model.ECardModel;
import com.febers.iuestc.module.ecard.model.IECardModel;
import com.febers.iuestc.module.ecard.view.ECardActivity;

public class ECardJSInterface extends ECardContract.Presenter {

    private static final String TAG = "ECardJSInterface";
    public ECardJSInterface(ECardContract.View view) {
        super(view);
    }

    @Override
    public void homePageResult(BaseEvent event) {
        mEduView.showhomePageResult(event);
    }

    @Override
    public void DetailPageResult(BaseEvent<BeanEduECard> event) {
        mEduView.showDetailPageResult(event);
    }

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        IECardModel eCardModel = new ECardModel(this);
        try {
            eCardModel.resolveHtml(html);
        } catch (Exception e) {
            e.printStackTrace();
            mEduView.onError("获取一卡通信息出错");
        }
    }
}
