package com.testpro.easyrest.Core.imp;

import com.testpro.easyrest.Config.EasyRestConfig;
import com.testpro.easyrest.Core.Abstract.AbstractInitialConfiguration;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
/**
 *初始化config配置接口实现
 */
public class InitialConfigurationImp extends AbstractInitialConfiguration {


    @Override
    public void InitialConfiguration() {
        super.InitialConfiguration();//执行全局设置
        //执行通用设置
    }

    protected void GlobalBaseUrlSetting(String baseURI) {
        RestAssured.baseURI = baseURI;
        log.info("加载配置easyrest.restassured.baseurl=+{}" , EasyRestConfig.getBaseurl());
    }

    protected void GlobalLogSetting(String Strlog) {
        if (Strlog.equals("true")) {
            RestAssured.config = RestAssuredConfig.config().logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
            log.info("加载日志配置配置easyrest.restassured.log=+{}" , Strlog);
        } else {
            log.info("未启用日志配置请在application.properties中配置easyrest.restassured.log=true启用失败日志");
        }
    }


}