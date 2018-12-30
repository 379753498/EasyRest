package com.example.demo;

import com.testpro.easyrest.Core.Interface.InterfaceExecution;
import com.testpro.easyrest.Core.imp.RestAssuredImp;
import com.testpro.easyrest.Data.ExcelInterfaceDataProd;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoApplication {



    @Bean
    public ExcelInterfaceDataProd getInterfaceDataProvider()
    {
        return new ExcelInterfaceDataProd();
    }

    @Bean
    public InterfaceExecution getInterfaceExecution()
    {
        return new RestAssuredImp();
    }
}

