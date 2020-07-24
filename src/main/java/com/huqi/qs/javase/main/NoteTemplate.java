package com.huqi.qs.javase.main;

import com.alibaba.fastjson.JSONObject;

public class NoteTemplate {
    private String prefix;
    private String suffix;
    private String numberRange;
    private String dateShort;
    private String dateShortRange;
    private String dateLong;
    private String dateLongRange;
    private String timeShort;
    private String timeLong;
    private String checkbox;
    private String userSelector;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getNumberRange() {
        return numberRange;
    }

    public void setNumberRange(String numberRange) {
        this.numberRange = numberRange;
    }

    public String getDateShort() {
        return dateShort;
    }

    public void setDateShort(String dateShort) {
        this.dateShort = dateShort;
    }

    public String getDateShortRange() {
        return dateShortRange;
    }

    public void setDateShortRange(String dateShortRange) {
        this.dateShortRange = dateShortRange;
    }

    public String getDateLong() {
        return dateLong;
    }

    public void setDateLong(String dateLong) {
        this.dateLong = dateLong;
    }

    public String getDateLongRange() {
        return dateLongRange;
    }

    public void setDateLongRange(String dateLongRange) {
        this.dateLongRange = dateLongRange;
    }

    public String getTimeShort() {
        return timeShort;
    }

    public void setTimeShort(String timeShort) {
        this.timeShort = timeShort;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    public String getUserSelector() {
        return userSelector;
    }

    public void setUserSelector(String userSelector) {
        this.userSelector = userSelector;
    }
}
