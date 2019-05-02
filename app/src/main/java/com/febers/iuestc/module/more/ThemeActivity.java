package com.febers.iuestc.module.more;

import com.febers.iuestc.R;
import com.febers.iuestc.util.SPUtil;
import com.febers.iuestc.view.adapter.AdapterTheme;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.entity.BeanTheme;
import com.febers.iuestc.entity.EventTheme;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.febers.iuestc.base.Constants.THEME_CODE;

public class ThemeActivity extends BaseSwipeActivity {

    @Override
    protected int setView() {
        return R.layout.activity_theme;
    }

    @Override
    protected int setToolbar() {
        return R.id.tb_theme;
    }

    @Override
    protected String setToolbarTitle() {
        return "主题风格";
    }

    @Override
    protected void initView() {
        RecyclerView rvTheme = findViewById(R.id.rv_theme);
        rvTheme.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        rvTheme.setLayoutManager(new LinearLayoutManager(this));
        AdapterTheme adapterTheme = new AdapterTheme(this, initThemeDate());
        rvTheme.setAdapter(adapterTheme);
        adapterTheme.setOnItemClickListener((viewHolder, beanTheme, i) -> {
            if (!beanTheme.getUsing()) {
                themeChange(i);
                EventBus.getDefault().post(new EventTheme(true, i));
            }
        });
    }

    private List<BeanTheme> initThemeDate() {
        List<BeanTheme> themeList = new ArrayList<>();
        BeanTheme defaultTheme = new BeanTheme(R.color.colorPrimary, "原生");
        BeanTheme night = new BeanTheme(R.color.black, "黑色");
        BeanTheme green = new BeanTheme(R.color.green, "绿色");
        BeanTheme Red = new BeanTheme(R.color.red, "红色");
        BeanTheme purple = new BeanTheme(R.color.purple, "紫色");
        BeanTheme orange = new BeanTheme(R.color.orange, "橙色");
        BeanTheme grey = new BeanTheme(R.color.grey, "灰色");
        BeanTheme teal = new BeanTheme(R.color.teal, "鸭绿");
        BeanTheme pink = new BeanTheme(R.color.pink, "粉色");
        BeanTheme blue = new BeanTheme(R.color.blue, "蓝色");
        themeList.add(defaultTheme);
        themeList.add(night);
        themeList.add(green);
        themeList.add(Red);
        themeList.add(purple);
        themeList.add(orange);
        themeList.add(grey);
        themeList.add(teal);
        themeList.add(pink);
        themeList.add(blue);
        int themeCode = SPUtil.INSTANCE().get(THEME_CODE, 9);
        themeList.get(themeCode).setUsing(true);
        return themeList;
    }


    private void themeChange(int code) {
        SPUtil.INSTANCE().put(THEME_CODE, code);
        this.recreate();
    }
}
