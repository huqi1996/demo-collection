package com.huqi.qs.excel.bean;

/**
 * @author huqi
 */
public class Region {
    private long id;
    private String region;

    @Override
    public String toString() {
        return id + region;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
