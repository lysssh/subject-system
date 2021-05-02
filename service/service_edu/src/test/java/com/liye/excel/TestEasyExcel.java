package com.liye.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liye
 * @create 2021-01-18-18:24
 */
public class TestEasyExcel {

    public static void main(String[] args) {
        //实现写操作

        //1.设置写入文件夹地址和Excel名称
        String fileName="F:\\write.xlsx";
        //2.调用easyExcel
       EasyExcel.write(fileName, DemoData.class)
                .sheet("学生列表").doWrite(getData());
    }

    @Test
    public void f1() {
        String fileName="F:\\write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    private static List<DemoData> getData() {
        List<DemoData> list=new ArrayList<>();
        for (int i=0;i<12;i++) {
            DemoData demoData=new DemoData();
            demoData.setSno(i+1);
            demoData.setSname("limin"+(i+1));
            list.add(demoData);
        }
        return list;
    }
}
