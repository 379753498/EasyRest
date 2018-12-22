package com.testpro.easyrest.Core.Abstract;

import com.testpro.easyrest.Core.Interface.ResponseExecut;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbctractResponseExecut implements ResponseExecut {
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

    protected abstract Response UrlHeadParam(ExecutionData executionData);
    protected abstract Response UrlParam(ExecutionData executionData);
    protected abstract Response UrlHead(ExecutionData executionData);
    protected abstract Response Url(ExecutionData executionData);

}
