package com.testpro.easyrest.Core.imp;


import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import com.testpro.easyrest.Core.Interface.ResponseExecut;
import com.testpro.easyrest.Core.Interface.Verification;
import com.testpro.easyrest.Core.Interface.interfaceExecution;
import com.testpro.easyrest.Util.JsonUtil;
import com.testpro.easyrest.Util.ReportDetil;
import com.testpro.easyrest.Util.Verify;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class RestAssuredImp implements interfaceExecution {

    @Resource
    private InitialConfiguration initialConfiguration;

    @Resource
    private ResponseExecut <Response, ExecutionData> responseExecut;
    @Resource
    private Verification <Response, ExecutionData> verification;

    @Override
    public void execution(ExecutionData executionData) {

        ReportDetil.requestBody(rquestbodyData(executionData));//设置测试报告的参数列表
        initialConfiguration.InitialConfiguration();//初始化配置并进行设置
        Response response = responseExecut.execut(executionData);//执行请求并拿到返回值
        log.info(response.asString());
        if (executionData.getRetruntype().equals("json")) {//      如果预期结果是json则展示在报告中
            ReportDetil.respondBody(response.asString());
        }
        verification.executVerification(response, executionData);//执行验证参数功能
        Verify.tearDown();//统一异常处理收集

    }

    private String rquestbodyData(ExecutionData executionData) {
        LinkedHashMap <String, Object> stringMap = new LinkedHashMap <>();
        String returntype = executionData.getRetruntype();
        String method = executionData.getMethod();
        String headers = executionData.getHeaders();
        String parameters = executionData.getParameters();
        String url = executionData.getUrl();
        stringMap.put("URL", url);
        stringMap.put("Method", method);
        if (!StringUtils.isEmpty(parameters)) {
            stringMap.put("参数列表", JsonUtil.FastStringtoMap(parameters));
        } else {
            Map map = new HashMap();
            map.put("参数信息", "无");
            stringMap.put("参数列表", map);
        }
        if (headers != null) {
            stringMap.put("头信息", JsonUtil.FastStringtoMap(headers));
        } else {
            Map map = new HashMap();
            map.put("头信息", "无");
            stringMap.put("头信息列表", map);
        }
        stringMap.put("返回类型", returntype);
        return JsonUtil.FastObjectToJsonString(stringMap);
    }
}
