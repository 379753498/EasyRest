package com.testpro.easyrest.Core.Abstract;

import cn.hutool.core.util.StrUtil;
import com.testpro.easyrest.Config.EasyRestConfig;
import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractInitialConfiguration implements InitialConfiguration {

    /**
     * property 三种状态 null initFile  init
     * 第一种由数据驱动场景读取配置文件后 property initFile状态 此时不需要再次读取文件 但是需要初始化全局设置一次
     * 此时代码会执行一次initGlobal 操作 此时property 从initFile 变成initGlobal
     * <p>
     * 第二种 剥离数据驱动场景
     * 由java 构造对象时 property 参数为空
     * 那么此时会初始化所有参数并进行initGlobal 操作 此时property从空变成initGlobal
     */
    @Override
    public void InitialConfiguration() {
        String property = System.getProperty("easyrest.restassured.init");
        //场景一 剥离数据驱动场景 用户
        if (StrUtil.isEmpty(property)) {
            EasyRestConfig.initglobalConfigSetting();
            this.GlobalSetting();
            log.info("场景一 剥离数据驱动场景 用户");
        }
        //场景二 数据驱动场景用户
        else if (property.equals(EasyRestConfig.initFile)) {
            log.info("场景二 数据驱动场景用户");
            this.GlobalSetting();
        }
    }

    private void GlobalSetting() {
        if (!StrUtil.isEmpty(EasyRestConfig.getBaseurl())) {
            GlobalBaseUrlSetting(EasyRestConfig.getBaseurl());
        } else if (!StrUtil.isEmpty(EasyRestConfig.getLog())) {
            GlobalLogSetting(EasyRestConfig.getLog());
        }
        System.setProperty("easyrest.restassured.init" , EasyRestConfig.initGlobal);
    }

    protected abstract void GlobalLogSetting(String log);

    protected abstract void GlobalBaseUrlSetting(String baseurl);
}
