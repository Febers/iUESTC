/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:03
 *
 */

package com.febers.iuestc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.entity.BeanBook;

import java.util.List;

public class AdapterQuery extends RecyclerView.Adapter<AdapterQuery.ViewHolder> {

    private Context mContext;
    private List<BeanBook> bookList;
    private String keyword;

    public AdapterQuery(List<BeanBook> bookList) {
        this.bookList = bookList;
        this.keyword = keyword;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookTitle;
        TextView tvBookPosition;
        TextView tvBookDetailPosition;
        TextView tvBookGetBookCode;

        public ViewHolder(View itemView) {
            super(itemView);

            tvBookTitle = itemView.findViewById(R.id.tv_lib_query_title);
            tvBookPosition = itemView.findViewById(R.id.tv_lib_query_position);
            tvBookDetailPosition = itemView.findViewById(R.id.tv_lib_query_detail_position);
            tvBookGetBookCode = itemView.findViewById(R.id.tv_lib_query_get_book_code);
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
//        holder.tvBookTitle.setText(Html.fromHtml(keywordToRed(book.getTitle())));
        holder.tvBookTitle.setText(book.getTitle());
        holder.tvBookPosition.setText(book.getPosition());
        holder.tvBookDetailPosition.setText(book.getDetailPosition());
        holder.tvBookGetBookCode.setText("索书号: "+book.getGetBookCode());
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

