package com.huqi.qs.excel.main;

import com.huqi.qs.excel.bean.Record;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public static final String SECOND_PHASE = "2018-01-01";
    public static final String END_TIME = "2019-04-01";

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
            calculateSecond(data);
            calculateFirst(data);
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
            long firstDate = new SimpleDateFormat("yyyy-mm-dd").parse(FIRST_PHASE).getTime();
            long secondDate = new SimpleDateFormat("yyyy-mm-dd").parse(SECOND_PHASE).getTime();
            long dataRowDate = new SimpleDateFormat("yyyy-mm-dd").parse(dataRow.get(4)).getTime();
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

    private static void calculateFirst(List<Map<Integer, String>> data) {
        Record record = new Record(0, 0, 0, START_TIME, END_TIME);
        for (Map<Integer, String> dataRow : data) {
            calculateDataRow(dataRow, record);
        }
        record.printRecord();
    }

    private static void calculateDataRow(Map<Integer, String> dataRow, Record record) {
        int type = Integer.parseInt(dataRow.get(3));
        if (type == BUY) {
            record.setAmount(record.getAmount() + Double.parseDouble(dataRow.get(2)));
            record.setProfit(record.getProfit() - Double.parseDouble(dataRow.get(2)));
            record.setCharge(record.getCharge() + Double.parseDouble(dataRow.get(5)));
        } else if (type == SELL || type == BONUS) {
            record.setAmount(record.getAmount() + Double.parseDouble(dataRow.get(2)));
            record.setProfit(record.getProfit() + Double.parseDouble(dataRow.get(2)));
            record.setCharge(record.getCharge() + Double.parseDouble(dataRow.get(5)));
            if (type == SELL) {
                record.setCharge(record.getCharge() + Double.parseDouble(dataRow.get(2)) * GOVERNMENT_CHARGE);
            } else {
                record.setCharge(record.getCharge() + (Double.parseDouble(dataRow.get(2)) * BONUS_CHARGE));
            }
        }
    }

    private static void calculateSecond(List<Map<Integer, String>> data) {
        try {
            long firstDate = new SimpleDateFormat("yyyy-mm-dd").parse(FIRST_PHASE).getTime();
            long secondDate = new SimpleDateFormat("yyyy-mm-dd").parse(SECOND_PHASE).getTime();
            Record record1 = new Record(0, 0, 0, START_TIME, FIRST_PHASE);
            Record record2 = new Record(0, 0, 0, FIRST_PHASE, SECOND_PHASE);
            Record record3 = new Record(0, 0, 0, SECOND_PHASE, END_TIME);
            for (Map<Integer, String> dataRow : data) {
                long dataRowDate = new SimpleDateFormat("yyyy-mm-dd").parse(dataRow.get(4)).getTime();
                if (dataRowDate < firstDate) {
                    calculateDataRow(dataRow, record1);
                } else if (dataRowDate < secondDate) {
                    calculateDataRow(dataRow, record2);
                } else {
                    calculateDataRow(dataRow, record3);
                }
            }
            record1.printRecord();
            record2.printRecord();
            record3.printRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}