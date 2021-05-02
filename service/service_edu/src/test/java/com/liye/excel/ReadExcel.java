package com.liye.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author liye
 * @create 2021-01-18-18:46
 */
@Data
public class ReadExcel {

    @ExcelProperty(index = 0)
    private Integer sno;


    @ExcelProperty(index = 1)
    private String sname;
}
