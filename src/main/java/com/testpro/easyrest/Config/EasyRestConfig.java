package com.testpro.easyrest.Config;

import cn.hutool.core.io.resource.ClassPathResource;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class EasyRestConfig {
  // 已读文件未初始化全局设置
  public static final String initFile = "initFile";
  // 已读文件已初始化全局设置
  public static final String initGlobal = "initGlobal";
  public static final String GLOBAL_SYSTEM_CLEAN_SETTING = "clean";
  public static final String GLOBAL_SYSTEM_CACHE_SETTING = "cache";
  public static final String GLOBAL_SYSTEM_COOKIE_SETTING = "easyrest.restassured.cookies";
  public static final String GLOBAL_SYSTEM_SESSION_SETTING = "easyrest.restassured.session";
  public static final String EASYREST_RESTASSURED_BASEURL = "easyrest.restassured.baseurl";
  public static final String EASYREST_RESTASSURED_LOG = "easyrest.restassured.log";
  private static String baseUrl;
  private static String Log;
  private static String filepath;

  public static String getLog() {
    return Log;
  }

  private static void setLog(String log) {
    Log = log;
  }

  public static String getBaseUrl() {
    return baseUrl;
  }

  private static void setBaseUrl(String baseUrl) {
    EasyRestConfig.baseUrl = baseUrl;
  }

  public static String getFilepath() {
    return filepath;
  }

  private static void setFilepath(String filepath) {
    EasyRestConfig.filepath = filepath;
  }

  public static void initGlobalConfigSetting() {
    log.info("读取配置文件application.properties写入EasyRestConfig");
    ClassPathResource resource = new ClassPathResource("application.properties");
    Properties properties = new Properties();
    try {

      properties.load(resource.getStream());
      if (!properties.isEmpty()) {
        for (String key : properties.stringPropertyNames()) {
          if (key.equals("easyrest.restassured.baseurl")) {
            EasyRestConfig.setBaseUrl(properties.getProperty(key));
            System.setProperty(key, properties.getProperty(key));
          }
          if (key.equals("easyrest.exceldata.filepath")) {
            EasyRestConfig.setFilepath(properties.getProperty(key));
            System.setProperty(key, properties.getProperty(key));
          }
          if (key.equals("easyrest.restassured.log")) {
            EasyRestConfig.setLog(properties.getProperty(key));
            System.setProperty(key, properties.getProperty(key));
          }
        }
      }
      System.setProperty("easyrest.restassured.init", initFile);
      log.info("完成读取配置文件application.properties写入EasyRestConfig");

    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }
}
