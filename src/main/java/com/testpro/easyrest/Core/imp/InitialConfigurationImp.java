package com.testpro.easyrest.Core.imp;

import com.testpro.easyrest.Config.EasyRestConfig;
import com.testpro.easyrest.Core.Abstract.AbstractInitialConfiguration;
import com.testpro.easyrest.Core.Filter.GlobalCacheFilter;
import com.testpro.easyrest.Core.Filter.LogFilter;
import com.testpro.easyrest.Core.Filter.RestCookieFilter;
import com.testpro.easyrest.Core.Filter.RestSessionFilter;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
/** 初始化config配置接口实现 */
public class InitialConfigurationImp extends AbstractInitialConfiguration {

  protected void GlobalBaseUrlSetting(String baseUrl) {
    RestAssured.baseURI = baseUrl;
    log.info(
        "加载配置{}=+{}", EasyRestConfig.EASYREST_RESTASSURED_BASEURL, EasyRestConfig.getBaseUrl());
  }

  protected void GlobalLogSetting(String isOpenLog) {

    //    声明缓存拦截器
    ArrayList<Filter> filters = new ArrayList<>();
    filters.add(new GlobalCacheFilter());
    filters.add(new LogFilter());
    filters.add(new RestSessionFilter());
    filters.add(new RestCookieFilter());
    RestAssured.filters(filters);
    if (isOpenLog.equals("true")) {
      if (RestAssured.config != null) {
        RestAssured.config =
            RestAssuredConfig.config()
                .logConfig(
                    LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
      }
      log.info("加载日志配置配置{}=+{}", EasyRestConfig.EASYREST_RESTASSURED_LOG, isOpenLog);
    } else {
      log.info("未启用日志配置请在application.properties中配置easyrest.restassured.log=true启用失败日志");
    }
  }
}
