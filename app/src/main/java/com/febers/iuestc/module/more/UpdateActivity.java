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
import android.view.View;
import android.widget.Button;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.entity.BeanUpdate;
import com.febers.iuestc.util.NetFileSizeUtil;
import com.febers.iuestc.view.custom.CustomUpdateDialog;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

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
        Button btnCacal = updateDialog.getBtnCancal();
        btnEnter.setOnClickListener((View v)-> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(update.getDownloadUrl()));
            startActivity(intent);
        });
        btnCacal.setOnClickListener((View v) -> {
            updateDialog.dismiss();
            finish();
        });
        updateDialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Beta.unregisterDownloadListener();
    }
}
