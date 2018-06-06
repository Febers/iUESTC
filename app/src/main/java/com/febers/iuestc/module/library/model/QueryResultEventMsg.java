package com.febers.iuestc.module.library.model;

import java.util.List;

public class QueryResultEventMsg {

    private List<BeanBook> bookList;

    public QueryResultEventMsg(List<BeanBook> bookList) {
        this.bookList = bookList;
    }
    public List<BeanBook> getBookList() {
        return bookList;
    }

    public void setBookList(List<BeanBook> bookList) {
        this.bookList = bookList;
    }
}
