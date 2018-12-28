package com.testpro.easyrest.Core.imp;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.testpro.easyrest.Core.Abstract.AbctractRestAssuredExecute;
import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import com.testpro.easyrest.Util.JsonUtil;
import com.testpro.easyrest.Util.ReportDetil;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Slf4j
public class RestAssuredImp extends AbctractRestAssuredExecute {

    @Override
    public void execution(ExecutionData executionData) {
        //初始化配置
        InitialConfiguration configuration = new InitialConfigurationImp();
        configuration.InitialConfiguration();
        //打印请求报文
        ReportDetil.requestBody(rquestbodyData(executionData));
        //执行返回请求
        Response response = executResponse(executionData);
        //记录到测试报告
        String responseBody = response.asString();
        ReportDetil.respondBody(responseBody);
        ExecutVerification(response,executionData);


    }


    @Override
    public Response executResponse(ExecutionData data) {
        String url = data.getUrl();
        if (StrUtil.isEmpty(url)) {
            throw new RuntimeException("Url不能为空");
        }
        RequestSpecification requestSpecification = getRequestSpecification(data);
        if (!StrUtil.isEmpty(data.getMethod())) {
            switch (data.getMethod()) {
                case "get":
                    return RestAssured.given().log().all().spec(requestSpecification).get(url);
                case "post":
                    return RestAssured.given().log().all().spec(requestSpecification).post(url);
                default:
                    throw new RuntimeException("暂时不支持其他方式");
            }
        } else {
            throw new RuntimeException("方法参数不能为空");
        }
    }


    @Override
    public void ExecutVerification(Response response, ExecutionData executionData) {
        ResponseSpecification specification = getResponseSpecification(executionData);
        response.then().assertThat().spec(specification).log().all();
    }

    private ResponseSpecification getResponseSpecification(ExecutionData executionData) {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        if (!StrUtil.isEmpty(executionData.getRetrunvauleCheck())) {
            builder.expectBody(Matchers.equalTo(executionData.getRetrunvauleCheck()));
        }
        if (!StrUtil.isEmpty(executionData.getRetrunJsonPathCheck())) {
            Map map = JSON.parseObject(executionData.getRetrunJsonPathCheck(), Map.class);
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = (String) map.get(key);
                try {
                    int i = Integer.parseInt(value);
                    builder.expectBody(key, Matchers.is(i));
                }
                catch (Exception e)
                {
                    builder.expectBody(key, Matchers.equalTo(value));
                }




            }
        }
        if (!StrUtil.isEmpty(executionData.getRetrunCharacterString())) {
            String retrunCharacterString = executionData.getRetrunCharacterString();
            String[] split = StrUtil.split(retrunCharacterString, ",");
            for (String s : split) {
                builder.expectBody(Matchers.containsString(s));
            }

        }

        return builder.build();
    }

    private String rquestbodyData(ExecutionData executionData) {
        LinkedHashMap<String, Object> stringMap = new LinkedHashMap<>();
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
        return JSONUtil.parseFromMap(stringMap).toStringPretty();
    }

    private RequestSpecification getRequestSpecification(ExecutionData data) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        if (!StrUtil.isEmpty(data.getHeaders())) {
            Map map = JSON.parseObject(data.getHeaders(), Map.class);
            builder.addHeaders(map);
        }
        if (!StrUtil.isEmpty(data.getParameters())) {
            Map map = JSON.parseObject(data.getParameters(), Map.class);
            builder.addParams(map);
        }
        return builder.build();
    }
}
