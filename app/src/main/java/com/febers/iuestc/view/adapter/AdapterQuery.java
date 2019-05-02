package com.febers.iuestc.view.adapter;

import android.content.Context;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanBook;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.base.CommonBaseAdapter;

import java.util.List;

public class AdapterQuery extends CommonBaseAdapter<BeanBook> {

    public AdapterQuery(Context context, List datas) {
        this(context, datas, false);
    }

    public AdapterQuery(Context context, List datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BeanBook beanBook, int i) {
        viewHolder.setText(R.id.tv_item_lib_name, beanBook.getName());
        viewHolder.setText(R.id.tv_item_lib_info, beanBook.getInfo());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_lib_query;
    }
}
