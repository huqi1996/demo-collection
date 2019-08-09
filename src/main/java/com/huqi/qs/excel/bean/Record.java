package com.huqi.qs.excel.bean;

/**
 * @author huqi
 */
public class Record {
    private double amount;
    private double profit;
    private double charge;
    private String startTime;
    private String endTime;

    private static Record record;

    private static final int LENGTH = 15;

    public Record() {
    }

    public Record(double amount, double profit, double charge, String startTime, String endTime) {
        this.amount = amount;
        this.profit = profit;
        this.charge = charge;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return amount + " " + profit + " " + charge;
    }

    public void printRecord() {
        printTitle();
        System.out.println(formatString(this.getStartTime(), null) +
                formatString(this.getEndTime(), null) +
                //(this.getProfit() > 0 ? formatString("***", null) : formatPrint(this.getAmount())) +
                formatPrint(this.getAmount()) +
                //(this.getProfit() > 0 ? formatString("***", null) : formatPrint(this.getProfit())) +
                formatPrint(this.getProfit()) +
                //(this.getProfit() > 0 ? formatString("***", null) : formatPrint(this.getProfit() - this.getCharge())) +
                formatPrint(this.getProfit() - this.getCharge()) +
                formatPrint(this.getCharge()) +
                formatPrint(this.getCharge() - ((this.getAmount() + this.getProfit()) / 2 * 0.001)) +
                formatPrint(((this.getAmount() + this.getProfit()) / 2 * 0.001)));
    }

    private void printTitle() {
        if (record == null) {
            record = new Record();
            System.out.println(formatString("开始时间", 8)
                    + formatString("结束时间", 8)
                    + formatString("交易额", 6)
                    + formatString("交易差值", 8)
                    + formatString("净收益", 6)
                    + formatString("费用", 4)
                    + formatString("佣金", 4)
                    + formatString("印花税", 6));
        }
    }

    private String formatPrint(double number) {
        return formatString(String.format("%.2f", number), null);
    }

    private String formatString(String str, Integer length) {
        StringBuilder builder = new StringBuilder();
        builder.append(str);
        if (length == null) {
            length = str.length();
        }
        for (int i = 0; i < LENGTH - length; i++) {
            builder.append(" ");
        }
        return builder.toString();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
