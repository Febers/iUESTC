/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-4 下午10:03
 *
 */

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
