package com.testpro.easyrest.Util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.ArrayList;
import java.util.List;

public  abstract class ExcelListener<T extends BaseRowModel> extends AnalysisEventListener <T>{


     List<T> list = new ArrayList<T>();

    public List <T> getObjectdata() {
        return list;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
