package com.testpro.easyrest.Core.Abstract;


import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象执行集成接口聚合抽象类
 */

@Slf4j
public abstract class AbctractRestAssuredExecute extends AbctractExecute <Response, ExecutionData> {
    @Override
    public Response execut(ExecutionData executionData) {
        if (executionData.getUrl() == null) {
            log.error("Url 为空 请检查数据参");
            throw new RuntimeException("Url 为空 请检查数据参数");
        }
//        有参数信息 有头的情况
        if (executionData.getParameters() != null && executionData.getHeaders() != null) {

            if (executionData.getMethod() != null) {
                return UrlHeadParam(executionData);
            } else {
                log.error("url is  null Exception please check try agian");
                throw new RuntimeException("url is null");
            }
        }

//        有参数信息 没有头的情况
        if (executionData.getParameters() != null && executionData.getHeaders() == null) {
            if (executionData.getMethod() != null) {
                return UrlParam(executionData);
            } else {
                log.error("url is  null Exception please check try agian");
                throw new RuntimeException("url is null");
            }
        }

        //        没有参数信息 有头的情况
        if (executionData.getParameters() == null && executionData.getHeaders() != null) {
            if (executionData.getMethod() != null) {
                return UrlHead(executionData);
            } else {
                log.error("url is  null Exception please check try agian");
                throw new RuntimeException("url is null");
            }
        } else {
            return Url(executionData);
        }


    }

    @Override
    public void ExecutVerification(Response response, ExecutionData executionData) {
        if (executionData.getRetrunvauleCheck() != null) {
            ResponseVauleCheck(response, executionData);
        }
        if (executionData.getRetrunJsonPathCheck() != null && executionData.getRetruntype() != null && executionData.getRetruntype().equals("json")) {
            ResponseJsonPathCheck(response, executionData);
        }
        if (executionData.getRetrunCharacterString() != null) {
            ResponseCharacterString(response, executionData);
        }

    }

    public abstract void execution(ExecutionData executionData);

    public abstract Response UrlHeadParam(ExecutionData executionData);

    public abstract Response UrlParam(ExecutionData executionData);

    public abstract Response UrlHead(ExecutionData executionData);

    public abstract Response Url(ExecutionData executionData);

    public abstract void ResponseVauleCheck(Response response, ExecutionData executionData);

    public abstract void ResponseJsonPathCheck(Response response, ExecutionData executionData);

    public abstract void ResponseCharacterString(Response response, ExecutionData executionData);
}
