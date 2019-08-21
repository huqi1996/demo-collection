package com.huqi.qs.json.bean;

import com.alibaba.fastjson.JSON;

public class Test001 {
    private String key001;
    private String key002;
    private String key003;
    private String text;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getKey001() {
        return key001;
    }

    public void setKey001(String key001) {
        this.key001 = key001;
    }

    public String getKey002() {
        return key002;
    }

    public void setKey002(String key002) {
        this.key002 = key002;
    }

    public String getKey003() {
        return key003;
    }

    public void setKey003(String key003) {
        this.key003 = key003;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
