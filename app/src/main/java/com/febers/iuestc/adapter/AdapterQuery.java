/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:03
 *
 */

package com.febers.iuestc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanBook;
import com.febers.iuestc.module.library.view.LibDetailActivity;

import java.util.List;

public class AdapterQuery extends RecyclerView.Adapter<AdapterQuery.ViewHolder> {

    private static final String TAG = "AdapterQuery";
    private Context mContext;
    private List<BeanBook> bookList;
    private String keyword;

    public AdapterQuery(List<BeanBook> bookList) {
        this.bookList = bookList;
        this.keyword = keyword;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookName;
        TextView tvBookInfo;
        CardView cvBookItem;

        public ViewHolder(View itemView) {
            super(itemView);

            tvBookName = itemView.findViewById(R.id.tv_item_lib_name);
            tvBookInfo = itemView.findViewById(R.id.tv_item_lib_info);
            cvBookItem = itemView.findViewById(R.id.cv_book_item);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lib_query, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeanBook book = bookList.get(position);
//        holder.tvBookName.setText(Html.fromHtml(keywordToRed(book.getTitle())));
        holder.tvBookName.setText(Html.fromHtml(book.getName()));
        holder.tvBookInfo.setText(book.getInfor());
        holder.cvBookItem.setOnClickListener( (View v) -> {
            Log.i(TAG, "onBindViewHolder: click");
            Intent intent = new Intent(mContext, LibDetailActivity.class);
            intent.putExtra("url", bookList.get(position).getUrl());
            mContext.startActivity(intent);
        });
    }

    public void removeAll() {
        bookList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    //把搜索结果中关键字变红
    private String keywordToRed(String title) {
        if (!title.contains(keyword)) {
            return title;
        }
        String result;
        String kw = keyword;
        int index = title.indexOf(kw);
        System.out.println(index);
        int i = kw.length();
        System.out.println(i);
        if (index == 0) {
            //关键字在最前面
            title = title.substring(kw.length(), title.length());
            result = "<font color='#FF4081'>" + kw + "</font>" + title;
        } else if (index == (title.length()-kw.length())) {
            //关键字在最后面
            title = title.substring(0, title.length()-kw.length());
            result = title + "<font color='#FF4081'>" + kw + "</font>";
        } else {
            String left = title.substring(0, i);
            String right = title.substring(i+kw.length(), title.length());
            result = left + "<font color='#FF4081'>" + kw + "</font>" + right;
        }
        return result;
    }
}

