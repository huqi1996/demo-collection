package com.huqi.qs.javase.notes;

/**
 * <ul>
 * <li>INVALID：无效</li>
 * <li>PROCESSING：处理中</li>
 * <li>FINISH：已结束</li>
 * </ul>
 *
 * @author huqi
 */
public enum StatusType implements InterfaceFirst, InterfaceSecond {
    INVALID((byte) 0, "invalid") {
        @Override
        public String second() {
            return "this is second invalid";
        }

        @Override
        public String third() {
            return "this is third invalid";
        }
    }, PROCESSING((byte) 1, "processing") {
        @Override
        public String second() {
            return "this is second processing";
        }

        @Override
        public String third() {
            return "this is third processing";
        }
    }, FINISH((byte) 2, "finish") {
        @Override
        public String second() {
            return "this is second finish";
        }

        @Override
        public String third() {
            return "this is third finish";
        }
    };

    private byte code;
    private String type;

    StatusType(byte code, String type) {
        this.code = code;
        this.type = type;
    }

    public byte getCode() {
        return this.code;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String first() {
        return "this is first " + this.type;
    }

    public abstract String third();
}
