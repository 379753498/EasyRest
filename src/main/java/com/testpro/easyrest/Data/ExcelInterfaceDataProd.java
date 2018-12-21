package com.testpro.easyrest.Data;

import com.testpro.easyrest.Util.ExcelUtil;
import com.testpro.easyrest.Util.TestNgUtil;
import com.testpro.easyrest.bean.ExcelData;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelInterfaceDataProd extends AbstractExcelInterfaceData implements InitializingBean {


    private static List <Object> excelData = new ArrayList <>();
    private static Iterator <Object[]> objectList = new ArrayList <Object[]>().iterator();


    @Override
    Iterator <Object[]> impDataProvider() {
        return objectList;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        File file = new ClassPathResource("ExcelData.xlsx").getFile();
        ExcelUtil excelUtil = new ExcelUtil();
        List <ExcelData> excelDataList = excelUtil.readExcelReturnListBean(file, ExcelData.class);

        for (ExcelData data : excelDataList)
            excelData.add((Object) data);
        objectList = TestNgUtil.createObjectList(excelData);
    }


}
