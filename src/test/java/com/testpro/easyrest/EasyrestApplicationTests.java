package com.testpro.easyrest;

import com.testpro.easyrest.Core.interfaceExecution;
import com.testpro.easyrest.Data.InterfaceDataProvider;
import com.testpro.easyrest.bean.ExcelData;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

@SpringBootTest
public class EasyrestApplicationTests extends AbstractTestNGSpringContextTests {

    //数据发动机
    @Autowired
    InterfaceDataProvider interfaceDataProvider;
    // 执行驱动器
    @Autowired
    interfaceExecution interfaceExecution;

    @DataProvider(name = "getdata")
    public Iterator <Object[]> getdata() {
        return interfaceDataProvider.ImplementDataProvider();
    }


    @Test(testName = "esayrestTests", dataProvider = "getdata")
    @Step("测试用例名称：{excelData.caseDescription}")
    public void contextLoads(ExcelData excelData) {
        interfaceExecution.execution(excelData);

    }

}

