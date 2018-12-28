package com.testpro.easyrest.Data;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.testpro.easyrest.Core.Abstract.AbstractExcelInterfaceData;
import com.testpro.easyrest.Util.ExcelUtil;
import com.testpro.easyrest.Util.TestNgUtil;
import com.testpro.easyrest.bean.ExecutionData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

@Component
@Slf4j
public class ExcelInterfaceDataProd extends AbstractExcelInterfaceData {
    private static List<Object> excelData = new ArrayList<>();
    private static Iterator<Object[]> objectList = new ArrayList<Object[]>().iterator();
    @Override
    protected Iterator<Object[]> impDataProvider() {
        ClassPathResource resource = new ClassPathResource("application.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getStream());
            String filepath = properties.getProperty("easyrest.exceldata.filepath");
            if (!StrUtil.isEmpty(filepath)) {
                this.setFilepath(filepath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        afterPropertiesSet();
        return objectList;
    }

    private void afterPropertiesSet() {
        File file;
        if (!StrUtil.isEmpty(this.getFilepath())) {
            file = new File(this.getFilepath());
            log.info(this.getFilepath() + "正在使用此路径进行测试");
        } else {
            file = new ClassPathResource("ExcelData.xlsx").getFile();
        }
        ExcelUtil excelUtil = new ExcelUtil();
        try {
            List<ExecutionData> executionDataList = excelUtil.readExcelReturnListBean(file, ExecutionData.class);
            if (executionDataList != null) {
                for (ExecutionData data : executionDataList) {
                    excelData.add(data);
                }
                objectList = TestNgUtil.createObjectList(excelData);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
