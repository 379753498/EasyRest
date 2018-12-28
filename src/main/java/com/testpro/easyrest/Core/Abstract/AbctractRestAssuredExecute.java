package com.testpro.easyrest.Core.Abstract;


import cn.hutool.core.util.StrUtil;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象执行集成接口聚合抽象类
 */

@Slf4j
public abstract class AbctractRestAssuredExecute extends AbctractExecute<Response, ExecutionData> {

    public Response executResponse(ExecutionData data) {
        String url = data.getUrl();
        if (StrUtil.isEmpty(url)) {
            throw new RuntimeException("Url不能为空");
        } else if (!StrUtil.isEmpty(data.getMethod())) {
          return   execut(data);
        } else {
            throw new RuntimeException("Method参数不能为空");
        }
    }

    public abstract Response execut(ExecutionData data);
}



