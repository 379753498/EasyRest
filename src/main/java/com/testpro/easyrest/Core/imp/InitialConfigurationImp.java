package com.testpro.easyrest.Core.imp;

import com.testpro.easyrest.Config.EasyRestConfig;
import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import com.testpro.easyrest.Util.RestAssuredUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitialConfigurationImp implements InitialConfiguration {

    @Autowired
    EasyRestConfig easyrestConfig;

    @Autowired
    RestAssuredUtil assuredUtil;

    @Override
    public void InitialConfiguration() {
        if (easyrestConfig.getBaseurl().equals("")) {
            log.warn("默认url没有填写请务必在URL中填写全部URL 否则会出现错误");
        } else {
            assuredUtil.setBaseurl(easyrestConfig.getBaseurl());
        }

    }
}
