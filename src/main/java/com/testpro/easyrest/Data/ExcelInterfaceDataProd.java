package com.testpro.easyrest.Data;

import com.testpro.easyrest.Core.Abstract.AbstractExcelInterfaceData;
import com.testpro.easyrest.Util.ExcelUtil;
import com.testpro.easyrest.Util.TestNgUtil;
import com.testpro.easyrest.bean.ExecutionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
@ConfigurationProperties(prefix = "easyrest.exceldata")
public class ExcelInterfaceDataProd extends AbstractExcelInterfaceData implements InitializingBean {

    private static List<Object> excelData = new ArrayList<>();
    private static Iterator<Object[]> objectList = new ArrayList<Object[]>().iterator();


    @Override
    protected Iterator<Object[]> impDataProvider() {
        return objectList;
    }

    public void afterPropertiesSet() throws Exception {

        File file;
        if (this.getFilepath() != null && !this.getFilepath().equals("")) {
            file = new File(this.getFilepath());
            log.info(this.getFilepath()+"正在使用此路径进行测试");
        } else {
            file = new ClassPathResource("ExcelData.xlsx").getFile();
        }
        ExcelUtil excelUtil = new ExcelUtil();
        List<ExecutionData> executionDataList = excelUtil.readExcelReturnListBean(file, ExecutionData.class);
        for (ExecutionData data : executionDataList)
            excelData.add((Object) data);
        objectList = TestNgUtil.createObjectList(excelData);
    }



}
