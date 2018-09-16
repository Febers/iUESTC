/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-10 上午1:07
 *
 */

package com.febers.iuestc.module.more;

import android.content.DialogInterface;
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
import com.febers.iuestc.util.NetFileSizeUtil;
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
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
        if (upgradeInfo == null) {
            onError("当前已是最新版本");
            return;
        }
        if (upgradeInfo.title.contains("通知")) {
            createNoticeDialog(upgradeInfo.title, upgradeInfo.newFeature);
            return;
        }
        BeanUpdate update = new BeanUpdate();
        update.setBody(upgradeInfo.newFeature);
        if (Build.VERSION.SDK_INT > 24) {
            update.setSize(NetFileSizeUtil.getDescriptionUp24(upgradeInfo.fileSize));
        } else {
            update.setSize(NetFileSizeUtil.getDescription(upgradeInfo.fileSize));
        }
        update.setVersionName(upgradeInfo.versionName);
        update.setDownloadUrl(upgradeInfo.apkUrl);

        if (updateDialog == null) {
            updateDialog = new CustomUpdateDialog(this, update);
        }
        Button btnEnter = updateDialog.getBtnEnter();
        Button btnCancal = updateDialog.getBtnCancal();
        btnEnter.setOnClickListener((View v)-> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(update.getDownloadUrl()));
            startActivity(intent);
        });
        btnCancal.setOnClickListener((View v) -> {
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
