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
        System.out.println(//(this.getProfit() > 0 ? formatString("***") : formatPrint(this.getAmount())) + "\t" +
                formatPrint(this.getAmount()) + "\t" +
                        //(this.getProfit() > 0 ? formatString("***") : formatPrint(this.getProfit() - this.getCharge())) + "\t" +
                        formatPrint(this.getProfit() - this.getCharge()) + "\t" +
                        //(this.getProfit() > 0 ? formatString("***") : formatPrint(this.getProfit())) + "\t" +
                        formatPrint(this.getProfit()) + "\t" +
                        formatPrint(this.getCharge()) + "\t" +
                        formatPrint(this.getCharge() - ((this.getAmount() + this.getProfit()) / 2 * 0.001)) + "\t" +
                        formatPrint(((this.getAmount() + this.getProfit()) / 2 * 0.001)) + "\t" +
                        formatString(this.getStartTime()) + "\t" +
                        formatString(this.getEndTime()));
    }

    private void printTitle() {
        if (record == null) {
            record = new Record();
            System.out.println(formatString("cjl") + "\t"
                    + formatString("sy") + "\t"
                    + formatString("jycz") + "\t"
                    + formatString("fy") + "\t"
                    + formatString("yj") + "\t"
                    + formatString("yhs") + "\t"
                    + formatString("kssj") + "\t"
                    + formatString("jssj"));
        }
    }

    private String formatPrint(double number) {
        return formatString(String.format("%.2f", number));
    }

    private String formatString(String str) {
        StringBuilder builder = new StringBuilder();
        builder.append(str);
        for (int i = 0; i < LENGTH - str.length(); i++) {
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
