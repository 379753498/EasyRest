package com.testpro.easyrest.Core.Abstract;

import cn.hutool.core.util.StrUtil;
import com.testpro.easyrest.Config.EasyRestConfig;
import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractInitialConfiguration implements InitialConfiguration {

  /**
   * property 2种状态 null-initFile--->initGlobal 第一种由数据驱动场景AbstractInitialConfiguration读取配置文件后
   * property initFile状态 此时不需要再次读取文件 但是需要初始化全局设置一次 此时代码会执行一次initGlobal 操作 此时property 从initFile
   * 变成initGlobal
   *
   * <p>第二种 剥离数据驱动场景 由java 构造对象时 property 参数为空 对象从null-->nitGlobal状态 这样就只会执行一次
   * 那么此时会初始化所有参数并进行initGlobal 操作 此时property从空变成initGlobal
   */
  @Override
  public void initConfiguration() {
    String property = System.getProperty("easyrest.restassured.init");
    // 场景一 剥离数据驱动场景 用户
    if (StrUtil.isEmpty(property)) {
      log.info("场景一 剥离数据驱动场景 用户");
      EasyRestConfig.initGlobalConfigSetting();
      this.GlobalSetting();
      log.info("初始化{剥离数据驱动场景}配置完成");
    }
    // 场景二 数据驱动场景用户
    else if (property.equals(EasyRestConfig.initFile)) {
      log.info("场景二 数据驱动场景用户");
      this.GlobalSetting();
      log.info("初始化{数据驱动场景}配置完成");
    }
  }

  /** 执行全集设置 */
  private void GlobalSetting() {
    if (!StrUtil.isEmpty(EasyRestConfig.getBaseUrl())) {
      GlobalBaseUrlSetting(EasyRestConfig.getBaseUrl());
    }
    if (StrUtil.isEmpty(EasyRestConfig.getLog())) {
      // 即便没有填写我要进行初始化操作
      GlobalLogSetting("default");
    } else {
      // 如果填写了true那么会启用全局失败print功能
      GlobalLogSetting(EasyRestConfig.getLog());
    }
    System.setProperty("easyrest.restassured.init", EasyRestConfig.initGlobal);
  }

  /**
   * 是否启用全局失败打印日志功能
   *
   * @param log true 启用
   */
  protected abstract void GlobalLogSetting(String log);

  /**
   * 全局BaseUrl设置
   *
   * @param baseUrl 全局url
   */
  protected abstract void GlobalBaseUrlSetting(String baseUrl);
}
