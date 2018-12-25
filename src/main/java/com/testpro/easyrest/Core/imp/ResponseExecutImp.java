package com.testpro.easyrest.Core.imp;

import com.testpro.easyrest.Core.Abstract.AbctractResponseExecut;
import com.testpro.easyrest.Util.JsonUtil;
import com.testpro.easyrest.Util.RestAssuredUtil;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Slf4j
@Component
public class ResponseExecutImp extends AbctractResponseExecut {
    @Autowired
    RestAssuredUtil assuredUtil;

    @Override
    protected Response UrlHeadParam(ExecutionData executionData) {




        Map headers ;
        Map Parameters ;
        try {
            headers = JsonUtil.FastStringtoMap(executionData.getHeaders());
            Parameters = JsonUtil.FastStringtoMap(executionData.getParameters());
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("参数解析异常请核对后在播");
        }
        if (executionData.getMethod().equals("get")) {
            Response response = assuredUtil.GetResponse(executionData.getUrl(), Parameters, headers);

            return response;
        } else if (executionData.getMethod().equals("post")) {
            return assuredUtil.PostResponse(executionData.getUrl(), Parameters, headers);
        } else {
            log.error("框架暂时不支持 post get 之外其他请求");
            throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
        }

    }

    @Override
    protected Response UrlParam(ExecutionData executionData) {

        Map Parameters ;
        try {
            Parameters = JsonUtil.FastStringtoMap(executionData.getParameters());
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("参数解析异常请核对后在播");
        }
        if (executionData.getMethod().equals("get")) {
            Response response = assuredUtil.GetResponseparameters(executionData.getUrl(), Parameters);
            return response;
        } else if (executionData.getMethod().equals("post")) {
            return assuredUtil.PostResponseparameters(executionData.getUrl(), Parameters);
        } else {
            log.error("框架暂时不支持 post get 之外其他请求");
            throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
        }
    }

    @Override
    protected Response UrlHead(ExecutionData executionData) {

        Map headers;
        try {
            headers = JsonUtil.FastStringtoMap(executionData.getHeaders());
        } catch (Exception e) {
            log.error(e.toString());
            log.error("参数解析异常请核对后在播");
            throw new RuntimeException("参数解析异常请核对后在播");
        }
        if (executionData.getMethod().equals("get")) {
            Response response = assuredUtil.GetResponseheards(executionData.getUrl(), headers);
            return response;
        } else if (executionData.getMethod().equals("post")) {
            return assuredUtil.PostResponseheards(executionData.getUrl(), headers);

        } else {
            log.error("框架暂时不支持 post get 之外其他请求");
            throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
        }
    }

    @Override
    protected Response Url(ExecutionData executionData) {
        if (executionData.getMethod() != null) {
            {
                if (executionData.getMethod().equals("get")) {
                    Response response = assuredUtil.GetResponse(executionData.getUrl());
                    return response;
                } else if (executionData.getMethod().equals("post")) {
                    return assuredUtil.PostResponse(executionData.getUrl());
                } else {
                    log.error("框架暂时不支持 post get 之外其他请求");
                    throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
                }
            }
        } else {
            log.error("url is  null Exception please check try agian");
            throw new RuntimeException("url is null");
        }
    }
}
