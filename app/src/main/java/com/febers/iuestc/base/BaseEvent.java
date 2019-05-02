package com.febers.iuestc.base;

public class BaseEvent<T> {

    private int code;

    private T date;

    public BaseEvent(int code, T date) {
        this.code = code;
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }
}
