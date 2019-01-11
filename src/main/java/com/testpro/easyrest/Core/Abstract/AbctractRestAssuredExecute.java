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

    //总执行接口聚合其他接口功能
    public void execution(ExecutionData executionData) {
        //初始化配置
        this.InitConfiguration();
        //打印请求报文
        ReportDetil.requestBody(this.rquestbodyData(executionData));
        //执行返回请求
        Response response = this.executResponse(executionData);
        //记录到测试报告
        String responseBody = response.asString();
        log.info("请求执行完成返回值{}，总耗时{}", responseBody, response.getTime());
        ReportDetil.respondBody(responseBody);
        String retrunvauleCheck = executionData.getRetrunvauleCheck();
        String retrunJsonPathCheck = executionData.getRetrunJsonPathCheck();
        String retrunCharacterString = executionData.getRetrunCharacterString();
        String contentType = response.getContentType();

        if (contentType.contains(ContentType.JSON.getValue())) {
            if (!StrUtil.isEmpty(retrunvauleCheck) || !StrUtil.isEmpty(retrunJsonPathCheck) || !StrUtil.isEmpty(retrunCharacterString)) {
                //执行验证
                this.ExecutVerification(response, executionData);
            }
        } else {
            log.warn("暂不支持返回值非{}的数据格式校验功能", ContentType.JSON.getValue());
        }
    }

    //执行请求并返回Response对象
    protected Response executResponse(ExecutionData data) {
        String url = data.getUrl();
        if (StrUtil.isEmpty(url)) {
            throw new RuntimeException("Url不能为空");
        } else if (!StrUtil.isEmpty(data.getMethod())) {
            return this.execut(data);
        } else {
            throw new RuntimeException("Method参数不能为空");
        }
    }

    protected void ExecutVerification(Response response, ExecutionData executionData) {
        RestAssuredExecutVerification(response, executionData);
    }


    //初始化配置
    protected abstract void InitConfiguration();

    //拼凑报告的请求参数相关信息
    protected abstract String rquestbodyData(ExecutionData executionData);

    //执行请求并返回Response对象
    protected abstract Response execut(ExecutionData data);

    //执行验证过程
    protected abstract void RestAssuredExecutVerification(Response response, ExecutionData executionData);


}



