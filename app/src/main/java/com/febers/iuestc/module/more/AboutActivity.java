package com.febers.iuestc.module.more;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.lucasurbas.listitemview.ListItemView;
import com.tencent.bugly.beta.Beta;

public class AboutActivity extends BaseSwipeActivity implements ListItemView.OnClickListener {

    private static final String TAG = "AboutActivity";
    private ListItemView itemViewWebHome, itemViewUpdate, itemViewDeveloper, itemViewEmail;

    @Override
    protected int setView() {
        return R.layout.activity_about;
    }

    @Override
    protected int setToolbar() {
        return R.id.tb_about;
    }

    @Override
    protected String setToolbarTitle() {
        return "关于";
    }

    @Override
    protected void findViewById() {
        itemViewUpdate = findViewById(R.id.item_view_update);
        itemViewWebHome = findViewById(R.id.item_view_web_home);
        itemViewDeveloper = findViewById(R.id.item_view_developer);
        itemViewEmail = findViewById(R.id.item_view_email);
    }

    @Override
    protected void initView() {
        itemViewUpdate.setOnClickListener(this);
        itemViewWebHome.setOnClickListener(this);
        itemViewDeveloper.setOnClickListener(this);
        itemViewEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_view_update:
                Beta.checkUpgrade();
                break;
            case R.id.item_view_web_home:
                Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_web)));
                startActivity(Intent.createChooser(i1, "访问App主页"));
                break;
            case R.id.item_view_developer:
                Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.developer_github)));
                startActivity(Intent.createChooser(i2, "访问开发者Github主页"));
                break;
            case R.id.item_view_email:
                sendMail();
                break;
            default:
                break;
        }
    }

    private void sendMail() {
        try {
            Intent i3 = new Intent(Intent.ACTION_SENDTO);
            i3.setData(Uri.parse("mailto:febers418@qq.com"));
            i3.putExtra(Intent.EXTRA_SUBJECT, "i成电用户反馈");
            startActivity(i3);
        } catch (Exception e) {
            //说明没有安装邮箱应用
            e.printStackTrace();
        }
    }
}
