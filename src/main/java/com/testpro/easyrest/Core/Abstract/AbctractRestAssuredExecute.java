package com.testpro.easyrest.Core.Abstract;

import cn.hutool.core.util.StrUtil;
import com.testpro.easyrest.Enum.ContentType;
import com.testpro.easyrest.Util.ReportDetil;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象执行集成接口聚合抽象类
 */

@Slf4j
public abstract class AbctractRestAssuredExecute extends AbctractExecute<Response, ExecutionData> {


    public void execution(ExecutionData executionData) {
        //初始化配置
        InitConfiguration();
        //打印请求报文
        ReportDetil.requestBody(rquestbodyData(executionData));
        //执行返回请求
        Response response = executResponse(executionData);
        //记录到测试报告
        String responseBody = response.asString();
        log.info("请求执行完成返回值{}，总耗时{}", responseBody, response.getTime());

        ReportDetil.respondBody(responseBody);
        //执行验证
        String retrunvauleCheck = executionData.getRetrunvauleCheck();
        String retrunJsonPathCheck = executionData.getRetrunJsonPathCheck();
        String retrunCharacterString = executionData.getRetrunCharacterString();
        String contentType = response.getContentType();

        if (ContentType.JSON.getValue().equals(contentType)) {
            if (!StrUtil.isEmpty(retrunvauleCheck) || !StrUtil.isEmpty(retrunJsonPathCheck) || !StrUtil.isEmpty(retrunCharacterString)) {
                ExecutVerification(response, executionData);
            }
        } else {
            log.warn("暂不支持返回值非{}的数据格式校验功能", ContentType.JSON.getValue());
        }
    }

    protected abstract void InitConfiguration();

    protected abstract String rquestbodyData(ExecutionData executionData);

    public abstract void ExecutVerification(Response response, ExecutionData executionData);

    public Response executResponse(ExecutionData data) {
        String url = data.getUrl();
        if (StrUtil.isEmpty(url)) {
            throw new RuntimeException("Url不能为空");
        } else if (!StrUtil.isEmpty(data.getMethod())) {
            return execut(data);
        } else {
            throw new RuntimeException("Method参数不能为空");
        }
    }

    protected abstract Response execut(ExecutionData data);
}



