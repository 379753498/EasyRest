package com.testpro.easyrest.Core.imp;

import com.testpro.easyrest.Config.EasyRestConfig;
import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
/**
 *初始化config配置接口实现
 */
public class InitialConfigurationImp implements InitialConfiguration {

    EasyRestConfig easyrestConfig;



    @Override
    public void InitialConfiguration() {
//        easyrestConfig= new EasyRestConfig();
//        if (easyrestConfig.getBaseurl().equals("")) {
//        } else {
//        }
        RestAssured.given().config(RestAssured.config().logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()));
    }
}
