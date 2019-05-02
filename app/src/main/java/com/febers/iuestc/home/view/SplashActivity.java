package com.febers.iuestc.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.febers.iuestc.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        // 闪屏的核心代码
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this,
                    HomeActivity.class); // 从启动动画ui跳转到主ui
            startActivity(intent);
            overridePendingTransition(0, 0);
            SplashActivity.this.finish();
        }, 500); // 启动动画持续0.5秒钟
    }
}
