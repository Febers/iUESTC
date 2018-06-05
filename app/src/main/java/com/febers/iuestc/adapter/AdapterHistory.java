package com.febers.iuestc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.febers.iuestc.R;
import com.febers.iuestc.module.library.model.BeanBook;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    private Context mContext;
    private List<BeanBook> bookList;

    public AdapterHistory(List<BeanBook> bookList) {
        this.bookList = bookList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookTitle;
        TextView tvBookDate;

        public ViewHolder(View itemView) {
            super(itemView);

            tvBookTitle = itemView.findViewById(R.id.tv_lib_history_title);
            tvBookDate = itemView.findViewById(R.id.tv_lib_history_date);
        }
    }
    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lib_history, parent, false);
        final AdapterHistory.ViewHolder holder = new AdapterHistory.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterHistory.ViewHolder holder, int position) {
        BeanBook book = bookList.get(position);
        holder.tvBookTitle.setText(book.getTitle());
        holder.tvBookDate.setText("借阅日期: " + book.getDate());
    }

    public void removeAll() {
        bookList.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
