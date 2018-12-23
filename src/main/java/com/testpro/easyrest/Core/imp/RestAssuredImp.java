package com.testpro.easyrest.Core.imp;


import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import com.testpro.easyrest.Core.Interface.ResponseExecut;
import com.testpro.easyrest.Core.Interface.Verification;
import com.testpro.easyrest.Core.Interface.interfaceExecution;
import com.testpro.easyrest.Util.JsonUtil;
import com.testpro.easyrest.Util.ReportDetil;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

@Component
@Slf4j
public class RestAssuredImp implements interfaceExecution {

    @Resource
    private InitialConfiguration initialConfiguration;

    @Resource
    private  ResponseExecut responseExecut;
    @Resource
    private Verification verification;

    @Override
    public void execution(ExecutionData executionData) {
        // 面向过程语言 增加测试Detil
        // 请求URL：xxx
        //  请求头参数：
        //

        ReportDetil.requestBody(rquestbodyData(executionData));

        initialConfiguration.InitialConfiguration();

        Response response = responseExecut.execut(executionData);
//      如果预期结果是json则展示在报告中
       if (executionData.getRetruntype().equals("json")){
           ReportDetil.respondBody(response.asString());
       }

        verification.executVerification(response, executionData);
    }

       private   String rquestbodyData(ExecutionData executionData){
           LinkedHashMap<String,Object> stringMap= new LinkedHashMap<>();
             String returntype = executionData.getRetruntype();
             String method = executionData.getMethod();
             String headers = executionData.getHeaders();
             String parameters = executionData.getParameters();
             String url = executionData.getUrl();
             stringMap.put("URL",url);
             stringMap.put("Method",method);
             stringMap.put("参数列表", JsonUtil.FastStringtoMap(parameters));
             stringMap.put("头信息",JsonUtil.FastStringtoMap(headers));
             stringMap.put("返回类型",returntype);
             return JsonUtil.FastObjectToJsonString(stringMap);
         }
}
