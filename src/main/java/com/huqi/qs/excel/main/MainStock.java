package com.huqi.qs.excel.main;

import com.huqi.qs.excel.bean.Record;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author huqi 20190604
 */
public class MainStock {
    public static final int LIMIT_COST = 5;
    public static final double FIRST_CHARGE = 0.003;
    public static final double SECOND_CHARGE = 0.0003;
    public static final double GOVERNMENT_CHARGE = 0.001;
    public static final double BONUS_CHARGE = 0.1;
    public static final int BUY = 0;
    public static final int SELL = 1;
    public static final int BONUS = 2;
    public static final String START_TIME = "2014-10-30";
    public static final String FIRST_PHASE = "2015-08-10";
    public static final String SECOND_PHASE = "2017-05-16";
    public static final String THIRD_PHASE = "2018-01-15";
    public static final String LAST_TIME = "2019-04-01";

    public static Pattern firstPattern;
    public static Pattern secondPattern;
    public static Pattern thirdPattern;
    public static Pattern fourthPattern;

    static {
        firstPattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        secondPattern = Pattern.compile("\\d{4}-\\d-\\d{2}");
        thirdPattern = Pattern.compile("\\d{4}-\\d{2}-\\d");
        fourthPattern = Pattern.compile("\\d{4}-\\d-\\d");
    }

    public static void main(String[] args) {
        try {
            InputStream inputStream = new FileInputStream("D:\\file\\data.xlsx");
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            List<Map<Integer, String>> data = new ArrayList<>();
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                Map<Integer, String> dataRow = new HashMap<>();
                XSSFRow row = xssfSheet.getRow(rowNum);
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    dataRow.put(i, getValue(row.getCell(i), i));
                }
                System.out.println(dataRow.get(4));
                calculateCost(dataRow);
                data.add(dataRow);
            }
            calculate(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getValue(XSSFCell cell, int cellRow) {
        if (cell == null) {
            return null;
        }
        if (cellRow == 4) {
            return DateFormat.getDateInstance().format(cell.getDateCellValue());
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    private static void calculateCost(Map<Integer, String> dataRow) {
        try {
            long firstDate = getTime(FIRST_PHASE);
            long secondDate = getTime(THIRD_PHASE);
            long dataRowDate = getTime(dataRow.get(4));
            int type = Integer.parseInt(dataRow.get(3));
            double a;
            if (type == BUY || type == SELL) {
                if (dataRowDate < firstDate) {
                    a = Integer.parseInt(dataRow.get(0)) * Double.parseDouble(dataRow.get(1));
                    dataRow.put(2, a + "");
                    if ((a * FIRST_CHARGE) < LIMIT_COST) {
                        dataRow.put(5, "5");
                    } else {
                        dataRow.put(5, (a * FIRST_CHARGE) + "");
                    }
                } else if (dataRowDate < secondDate) {
                    a = Integer.parseInt(dataRow.get(0)) * Double.parseDouble(dataRow.get(1));
                    dataRow.put(2, a + "");
                    if ((a * SECOND_CHARGE) < LIMIT_COST) {
                        dataRow.put(5, "5");
                    } else {
                        dataRow.put(5, (a * SECOND_CHARGE) + "");
                    }
                } else {
                    a = Integer.parseInt(dataRow.get(0)) * Double.parseDouble(dataRow.get(1));
                    dataRow.put(2, a + "");
                    dataRow.put(5, (a * SECOND_CHARGE) + "");
                }
            } else if (type == BONUS) {
                a = Integer.parseInt(dataRow.get(0)) * Double.parseDouble(dataRow.get(1));
                dataRow.put(2, a + "");
                dataRow.put(5, (a * BONUS_CHARGE) + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void calculate(List<Map<Integer, String>> data) {
        try {
            long firstDate = getTime(FIRST_PHASE);
            long secondDate = getTime(THIRD_PHASE);
            Record record1 = new Record(0, 0, 0, 0, 0, 0, 0, START_TIME, FIRST_PHASE);
            Record record2 = new Record(0, 0, 0, 0, 0, 0, 0, FIRST_PHASE, SECOND_PHASE);
            Record record3 = new Record(0, 0, 0, 0, 0, 0, 0, THIRD_PHASE, LAST_TIME);
            for (Map<Integer, String> dataRow : data) {
                long dataRowDate = getTime(dataRow.get(4));
                if (dataRowDate < firstDate) {
                    calculateDataRow(dataRow, record1);
                    System.out.println(dataRow.get(4) + "         " + dataRowDate + "    " + firstDate + "-------------------");
                } else if (dataRowDate < secondDate) {
                    calculateDataRow(dataRow, record2);
                    System.out.println(dataRow.get(4) + "         " + dataRowDate + "    " + firstDate + "+++++++++++++++++++");
                } else {
                    calculateDataRow(dataRow, record3);
                    System.out.println(dataRow.get(4) + "         " + dataRowDate + "    " + secondDate + "@@@@@@@@@@@@@@@@@@");
                }
            }
            List<Record> records = Arrays.asList(record1, record2, record3);
            printSummary(records);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long getTime(String text) {
        try {
            if (firstPattern.matcher(text).matches()) {
                return new SimpleDateFormat("yyyy-MM-dd").parse(text).getTime();
            } else if (secondPattern.matcher(text).matches()) {
                return new SimpleDateFormat("yyyy-M-dd").parse(text).getTime();
            } else if (thirdPattern.matcher(text).matches()) {
                return new SimpleDateFormat("yyyy-MM-d").parse(text).getTime();
            } else {
                return new SimpleDateFormat("yyyy-M-d").parse(text).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private static void calculateDataRow(Map<Integer, String> dataRow, Record record) {
        long secondDate = getTime(THIRD_PHASE);
        long dataRowDate = getTime(dataRow.get(4));
        int type = Integer.parseInt(dataRow.get(3));
        if (type == BUY) {
            record.setAmount(record.getAmount() + Double.parseDouble(dataRow.get(2)));
            record.setBuyAmount(record.getBuyAmount() + Double.parseDouble(dataRow.get(2)));
            record.setProfit(record.getProfit() - Double.parseDouble(dataRow.get(2)));
            record.setCharge(record.getCharge() + Double.parseDouble(dataRow.get(5)));
        } else if (type == SELL || type == BONUS) {
            record.setAmount(record.getAmount() + Double.parseDouble(dataRow.get(2)));
            record.setSellAmount(record.getSellAmount() + Double.parseDouble(dataRow.get(2)));
            record.setProfit(record.getProfit() + Double.parseDouble(dataRow.get(2)));
            record.setCharge(record.getCharge() + Double.parseDouble(dataRow.get(5)));
            if (type == SELL && dataRowDate < secondDate) {
                record.setCharge(record.getCharge() + Double.parseDouble(dataRow.get(2)) * GOVERNMENT_CHARGE);
                record.setPoundage(record.getCharge() - ((record.getAmount() + record.getProfit()) / 2 * 0.001));
                record.setStampTax((record.getAmount() + record.getProfit()) / 2 * 0.001);
            } else if (type == BONUS) {
                record.setCharge(record.getCharge() + (Double.parseDouble(dataRow.get(2)) * BONUS_CHARGE));
                record.setPoundage(record.getCharge() - ((record.getAmount() + record.getProfit()) / 2 * 0.001));
                record.setStampTax((record.getAmount() + record.getProfit()) / 2 * 0.001);
            } else {
                record.setPoundage(record.getCharge());
            }
        }
    }

    private static void printSummary(List<Record> records) {
        if (CollectionUtils.isNotEmpty(records)) {
            Record summary = new Record(0, 0, 0, 0, 0, 0, 0, START_TIME, LAST_TIME);
            for (Record record : records) {
                summary.setAmount(summary.getAmount() + record.getAmount());
                summary.setBuyAmount(summary.getBuyAmount() + record.getBuyAmount());
                summary.setSellAmount(summary.getSellAmount() + record.getSellAmount());
                summary.setProfit(summary.getProfit() + record.getProfit());
                summary.setCharge(summary.getCharge() + record.getCharge());
                summary.setPoundage(summary.getPoundage() + record.getPoundage());
                summary.setStampTax(summary.getStampTax() + record.getStampTax());
                record.printRecord();
            }
            summary.printRecord();
        }
    }
}