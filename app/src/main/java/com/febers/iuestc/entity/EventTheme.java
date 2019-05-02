package com.febers.iuestc.entity;

public class EventTheme {

    private Boolean themeChanged = false;

    private int themeCode = 0;

    public EventTheme(Boolean themeChanged, int themeCode) {
        this.themeChanged = themeChanged;
        this.themeCode = themeCode;
    }

    public Boolean getThemeChanged() {
        return themeChanged;
    }

    public void setThemeChanged(Boolean themeChanged) {
        this.themeChanged = themeChanged;
    }

    public int getThemeCode() {
        return themeCode;
    }

    public void setThemeCode(int themeCode) {
        this.themeCode = themeCode;
    }
}
