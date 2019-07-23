package com.huqi.qs.java8.bean;

/**
 * @author huqi
 */
public enum Country {
    /**
     * 枚举值
     */
    CHINA((byte) 0, "CHINA"), AMERICA((byte) 1, "AMERICA"), JAPAN((byte) 2, "JAPAN");

    private Byte code;
    private String name;

    public Byte getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    private Country(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Country fromCode(Byte code) {
        if (code != null) {
            for (Country country : Country.values()) {
                if (country.getCode().equals(code)) {
                    return country;
                }
            }
        }
        return null;
    }
}
