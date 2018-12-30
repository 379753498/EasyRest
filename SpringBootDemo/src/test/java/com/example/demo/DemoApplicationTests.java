package com.example.demo;

import com.testpro.easyrest.Core.Interface.InterfaceDataProvider;
import com.testpro.easyrest.Core.Interface.InterfaceExecution;
import com.testpro.easyrest.bean.ExecutionData;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

@SpringBootTest
public class DemoApplicationTests extends AbstractTestNGSpringContextTests {

    //数据发动机
    @Autowired
    InterfaceDataProvider interfaceDataProvider;
    // 执行驱动器
    @Autowired
    InterfaceExecution interfaceExecution;

    @DataProvider(name = "getdata")
    public Iterator<Object[]> getdata() {
        return interfaceDataProvider.ImplementDataProvider();
    }

    @Test(testName = "esayrestTests", dataProvider = "getdata")
    @Step("测试用例名称：{executionData.caseDescription}")
    public void EasyRestApplicationContext(ExecutionData executionData) {
        System.out.println(executionData);
        interfaceExecution.execution(executionData);
    }

}

