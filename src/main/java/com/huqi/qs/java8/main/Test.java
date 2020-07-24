package com.huqi.qs.java8.main;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class Test {

    public static void main(String[] args) {

        //GBK编码格式源码路径
        String srcDirPath = "D:\\IntelliJ IDEA 2018.3.1Files\\demo-collection\\webservice";
        //转为UTF-8编码格式源码路径
        String utf8DirPath = "D:\\IntelliJ IDEA 2018.3.1Files\\demo-collection\\src\\main\\java\\webservice";
        //获取所有java文件
        Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(srcDirPath), new String[]{"java"}, true);

        for (File javaGbkFile : javaGbkFileCol) {
            //UTF8格式文件路径
            String utf8FilePath = utf8DirPath + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());
            //使用GBK读取数据，然后用UTF-8写入数据
            try {
                FileUtils.writeLines(new File(utf8FilePath), "UTF-8", FileUtils.readLines(javaGbkFile, "GBK"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
