package com.febers.iuestc.module.user.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.febers.iuestc.R;

public class AboutActivity extends MaterialAboutActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull Context context) {
        return makeAboutCard();
    }

    private MaterialAboutList makeAboutCard() {
        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(getString(R.string.developer_name))
                .subText("UESTC")
                .icon(R.drawable.ic_person_black_24dp)
                .setOnClickAction(() -> {})
                .build());
        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Email")
                .subText(getString(R.string.developer_email))
                .icon(R.drawable.ic_email_black_24dp)
                .setOnClickAction(() -> {})
                .build());
        MaterialAboutCard authorCard = authorCardBuilder.title("开发者").build();

        MaterialAboutCard.Builder versionCardBuilder = new MaterialAboutCard.Builder();
        versionCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text(getString(R.string.app_name))
                .icon(R.drawable.ginkgo_blue)
                .setOnClickAction(() -> {})
                .build());
        versionCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Version")
                .subText(getString(R.string.app_version))
                .icon(R.drawable.ic_error_outline_black_24dp)
                .setOnClickAction(() -> {})
                .build());
        versionCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("主页")
                .subText(getString(R.string.app_web))
                .icon(R.drawable.ic_classroom_black)
                .setOnClickAction(() ->
                    {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_web)));
                        startActivity(Intent.createChooser(i, "请选择浏览器"));
                    })
                .build());
        MaterialAboutCard versionCard = versionCardBuilder.build();

        return new MaterialAboutList.Builder()
                .addCard(versionCard)
                .addCard(authorCard)
                .build();
    }
    @Nullable
    @Override
    protected CharSequence getActivityTitle() {
        return "关于";
    }
}
