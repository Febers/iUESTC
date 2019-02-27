/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-10 上午1:07
 *
 */

package com.febers.iuestc.module.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.entity.BeanUpdate;
import com.febers.iuestc.util.FileSizeUtil;
import com.febers.iuestc.view.custom.CustomUpdateDialog;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

/**
 * 显示更新和通知的Dialog
 * 从Bugly接收到消息有两种形式
 * 第一，以通知开头，逗号后面是通知的版本号
 * 当通知的版本号高于本机版本号时，显示通知
 */
public class UpdateActivity extends BaseActivity {

    private static final String TAG = "UpdateActivity";
    private CustomUpdateDialog updateDialog;

    @Override
    protected int setView() {
        return R.layout.activity_update;
    }

    @Override
    protected void initView() {
        UpgradeInfo upgradeInfo = null;
        //服务器不稳定时，无法检测到更新，重复一次
        for (int i = 0; i < 2; i++) {
            upgradeInfo = Beta.getUpgradeInfo();
            if (upgradeInfo != null) {
                break;
            }
        }
        if (upgradeInfo == null) {
            return;
        }
        if (upgradeInfo.title.contains("通知")) {
            createNoticeDialog(upgradeInfo.title, upgradeInfo.newFeature);
            return;
        }
        BeanUpdate update = new BeanUpdate();
        update.setBody(upgradeInfo.newFeature);
        if (Build.VERSION.SDK_INT > 24) {
            update.setSize(FileSizeUtil.getDescriptionUp24(upgradeInfo.fileSize));
        } else {
            update.setSize(FileSizeUtil.getDescription(upgradeInfo.fileSize));
        }
        update.setVersionName(upgradeInfo.versionName);
        update.setDownloadUrl(upgradeInfo.apkUrl);

        if (updateDialog == null) {
            updateDialog = new CustomUpdateDialog(this, update);
        }
        Button btnEnter = updateDialog.getBtnEnter();
        Button btnCancel = updateDialog.getBtnCancel();
        btnEnter.setOnClickListener((View v)-> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(update.getDownloadUrl()));
            startActivity(intent);
        });
        btnCancel.setOnClickListener((View v) -> {
            updateDialog.dismiss();
            finish();
        });
        updateDialog.show();
    }

    private void createNoticeDialog(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(content)
                .setPositiveButton("确定", (dialog, which) -> finish());
        builder.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Beta.unregisterDownloadListener();
    }
}
