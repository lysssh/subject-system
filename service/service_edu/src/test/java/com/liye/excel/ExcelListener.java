package com.liye.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author liye
 * @create 2021-01-18-18:47
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {

    //一行一行读取
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("***"+demoData);
    }

//读取表头信息
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

        System.out.println("表头:"+headMap);
    }



    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
