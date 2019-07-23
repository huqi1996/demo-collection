package com.huqi.qs.excel.main;


import com.huqi.qs.excel.bean.Region;
import com.huqi.qs.excel.util.HSSFUtil;
import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huqi 20190327
 */
public class MainRegion {
    public static void main(String[] args) {

        String path = "C:\\Users\\admin\\Desktop\\2019年最新行政区划数据库-旗舰版.xls";
        List<Region> regions = HSSFUtil.readExcel(path, 1);

        String path2 = "C:\\Users\\admin\\Desktop\\eh_regions.xls";
        List<Region> regions2 = HSSFUtil.readExcel(path2, 2);
        List<String> str1 = new ArrayList<>(5000);
        List<String> str2 = new ArrayList<>(5000);
        for (Region region : regions) {
            str1.add(region.getRegion());
        }
        for (Region region : regions2) {
            str2.add(region.getRegion());
        }

        // 文件有，数据库没有
        List<String> list1 = ListUtils.removeAll(str1, str2);
        // 数据库有，文件没有
        List<String> list2 = ListUtils.removeAll(str2, str1);

        System.out.println(regions.size() + "   " + str1.size());
        System.out.println(regions2.size() + "   " + str2.size());
        System.out.println(list1.size());
        System.out.println(list2.size());
        System.out.println(regions.size() - list1.size());
        System.out.println(regions2.size() - list2.size());
        System.out.println("****************************************");
        List<Long> ids = new ArrayList<>(5000);
        List<Long> ids2 = new ArrayList<>(5000);
        List<Long> ids3 = new ArrayList<>(5000);
        List<Long> ids4 = new ArrayList<>(5000);
        for (String str : list1) {
            for (Region region : regions) {
                if (region.getRegion().equals(str)) {
                    ids.add(region.getId());
                    break;
                }
            }
        }
        for (Region region : regions) {
            if (!ids.contains(region.getId())) {
                ids2.add(region.getId());
            }
        }
        for (String str : list2) {
            for (Region region : regions2) {
                if (region.getRegion().equals(str)) {
                    ids3.add(region.getId());
                    break;
                }
            }
        }
        for (Region region : regions2) {
            if (!ids3.contains(region.getId())) {
                ids4.add(region.getId());
            }
        }
        System.out.println(ids.size() + " " + ids2.size() + " " + ids3.size() + " " + ids4.size());
        HSSFUtil.exportExcel(path2, ids3, "左邻数据库省市区-共有.xls");
    }
}
