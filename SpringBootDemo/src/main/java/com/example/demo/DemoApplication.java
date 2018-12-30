package com.example.demo;

import com.testpro.easyrest.Core.Interface.InterfaceExecution;
import com.testpro.easyrest.Core.imp.RestAssuredImp;
import com.testpro.easyrest.Data.ExcelInterfaceDataProd;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


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

