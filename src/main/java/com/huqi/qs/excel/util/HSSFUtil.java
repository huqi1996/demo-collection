package com.huqi.qs.excel.util;

import java.io.InputStream;

import com.huqi.qs.excel.bean.Region;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HSSFUtil {
    public static List<Region> readExcel(String path, int type) {
        String postfix = getPostfix(path);
        if (Constant.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
            return readXls(path, type);
        }
        return null;
    }

    private static String getPostfix(String path) {
        if (path == null || Constant.EMPTY.equals(path.trim())) {
            return Constant.EMPTY;
        }
        if (path.contains(Constant.POINT)) {
            return path.substring(path.lastIndexOf(Constant.POINT) + 1);
        }
        return Constant.EMPTY;
    }

    private static List<Region> readXls(String path, int type) {
        System.out.println(Constant.PROCESSING + path);
        List<Region> regions = new ArrayList<>(5000);
        List<String> str = new ArrayList<>(5000);
        try {
            InputStream inputStream = new FileInputStream(path);
            HSSFWorkbook hssfWorkBook = new HSSFWorkbook(inputStream);
            for (int sheetNum = 0; sheetNum < hssfWorkBook.getNumberOfSheets(); sheetNum++) {
                HSSFSheet sheet = hssfWorkBook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    HSSFRow row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    if (type == 1) {
                        processHSSFRowV1(regions, str, row);
                    }
                    if (type == 2) {
                        processHSSFRowV2(regions, str, row);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return regions;
    }

    private static void processHSSFRowV1(List<Region> regions, List<String> str, HSSFRow row) {
        StringBuilder region = new StringBuilder();
        String province = getValue(row.getCell(6));
        String city = getValue(row.getCell(7));
        String district = getValue(row.getCell(8));
        if (StringUtils.isNotBlank(province)) {
            region.append("/");
            region.append(province.replace("省", ""));
        }
        if (StringUtils.isNotBlank(city)) {
            region.append("/");
            region.append(city);
        }
        if (StringUtils.isNotBlank(district)) {
            region.append("/");
            region.append(district);
        }
        String temp = region.toString();
        if (!str.contains(temp)) {
            str.add(temp);
            Region bean = new Region();
            bean.setRegion(temp);
            bean.setId((long) Double.parseDouble(getValue(row.getCell(0))));
            regions.add(bean);
        }
    }

    private static void processHSSFRowV2(List<Region> regions, List<String> str, HSSFRow row) {
        String region = getValue(row.getCell(5));
        if (!str.contains(region)) {
            str.add(region);
            Region bean = new Region();
            bean.setRegion(region);
            bean.setId((long) Double.parseDouble(getValue(row.getCell(0))));
            regions.add(bean);
        }
    }

    private static String getValue(HSSFCell cell) {
        if (cell == null) {
            return null;
        }
        if (CellType.NUMERIC == cell.getCellType()) {
            return String.valueOf(cell.getNumericCellValue());
        }
        if (CellType.STRING == cell.getCellType()) {
            return cell.getStringCellValue();
        }
        return null;
    }

    public static void exportExcel(String path, List<Long> ids, String fileName) {
        try {
            InputStream inputStream = new FileInputStream(path);
            HSSFWorkbook hssfWorkBook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = hssfWorkBook.getSheetAt(0);
            List<HSSFRow> rows = new ArrayList<>(5000);
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                rows.add(sheet.getRow(rowNum));
            }
            for (HSSFRow row : rows) {
                long id = (long) Double.parseDouble(getValue(row.getCell(0)));
                if (ids.contains(id)) {
                    sheet.removeRow(row);
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\admin\\Desktop\\" + fileName);
            hssfWorkBook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
