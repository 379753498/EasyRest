package com.testpro.easyrest;

import com.testpro.easyrest.Data.InterfaceDataProvider;
import com.testpro.easyrest.baen.ExcelData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

@SpringBootTest
public class EasyrestApplicationTests extends AbstractTestNGSpringContextTests {


    @Autowired
    InterfaceDataProvider interfaceDataProvider;

    @DataProvider(name = "getdata")
    public Iterator <Object[]> getdata() {
        return interfaceDataProvider.ImplementDataProvider();
    }


    @Test(testName = "请求天气数据json", dataProvider = "getdata")
    public void contextLoads(ExcelData excelData) {
//        Iterator <Object[]> iterator = interfaceDataProvider.ImplementDataProvider();
//        DataType dataType = interfaceDataProvider.DatasourceName();
        System.out.println(excelData);


    }

}

