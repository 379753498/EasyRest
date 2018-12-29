package com.testpro.easyrest.Core.imp;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.testpro.easyrest.Core.Abstract.AbctractRestAssuredExecute;
import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import com.testpro.easyrest.Util.JsonUtil;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class RestAssuredImp extends AbctractRestAssuredExecute {


    @Override
    public void execution(ExecutionData executionData) {
        super.execution(executionData);
    }

    @Override
    protected void InitConfiguration() {
        InitialConfiguration configuration = new InitialConfigurationImp();
        configuration.InitialConfiguration();
        log.info("初始化配置完成");
    }


    @Override
    public Response executResponse(ExecutionData data) {
        return super.executResponse(data);
    }

    @Override
    protected Response execut(ExecutionData data) {
        RequestSpecification requestSpecification = getRequestSpecification(data);
        switch (data.getMethod()) {
            case "get":
                return RestAssured.given(requestSpecification).get(data.getUrl());
            case "post":
                return RestAssured.given(requestSpecification).post(data.getUrl());
            default:
                throw new RuntimeException("暂时不支持其他方式");
        }
    }


    @Override
    public void ExecutVerification(Response response , ExecutionData executionData) {
        ResponseSpecification specification = getResponseSpecification(executionData);
        response.then().assertThat().spec(specification);
        log.info("验证工作执行完成");
    }

    private ResponseSpecification getResponseSpecification(ExecutionData executionData) {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        if (!StrUtil.isEmpty(executionData.getRetrunvauleCheck())) {
            builder.expectBody(Matchers.equalTo(executionData.getRetrunvauleCheck()));
        }
        if (!StrUtil.isEmpty(executionData.getRetrunJsonPathCheck())) {
            Map map = JSON.parseObject(executionData.getRetrunJsonPathCheck() , Map.class);
            for (Object o : map.keySet()) {
                String key = (String) o;
                String value = (String) map.get(key);
                try {
                    //识别是有小数点还是没有小数点的算法
                    String[] split = StrUtil.split(value , ".");
                    if (split.length > 1) {
                        double val = Double.parseDouble(value);
                        builder.expectBody(key , Matchers.is(val));
                    } else {
                        int val = Integer.parseInt(value);
                        builder.expectBody(key , Matchers.is(val));
                    }

                } catch (Exception e) {
                    builder.expectBody(key , Matchers.equalTo(value));
                }
            }
        }
        if (!StrUtil.isEmpty(executionData.getRetrunCharacterString())) {
            String retrunCharacterString = executionData.getRetrunCharacterString();
            String[] split = StrUtil.split(retrunCharacterString , ",");
            for (String s : split) {
                builder.expectBody(Matchers.containsString(s));
            }
        }
        return builder.build();
    }

    protected String rquestbodyData(ExecutionData executionData) {
        LinkedHashMap <String, Object> stringMap = new LinkedHashMap <>();
        String returntype = executionData.getRetruntype();
        String method = executionData.getMethod();
        String headers = executionData.getHeaders();
        String parameters = executionData.getParameters();
        String url = executionData.getUrl();
        stringMap.put("URL" , url);
        stringMap.put("Method" , method);
        if (!StrUtil.isEmpty(parameters)) {
            stringMap.put("参数列表" , JsonUtil.FastStringtoMap(parameters));
        } else {
            Map <String, String> map = new HashMap <>();
            map.put("参数信息" , "无");
            stringMap.put("参数列表" , map);
        }
        if (headers != null) {
            stringMap.put("头信息" , JsonUtil.FastStringtoMap(headers));
        } else {
            Map <String, String> map = new HashMap <>();
            map.put("头信息" , "无");
            stringMap.put("头信息列表" , map);
        }
        stringMap.put("返回类型" , returntype);
        return JSONUtil.parseFromMap(stringMap).toStringPretty();
    }

    private RequestSpecification getRequestSpecification(ExecutionData data) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        if (!StrUtil.isEmpty(data.getHeaders())) {
            Map <String, String> map = JSON.parseObject(data.getHeaders() , Map.class);
            builder.addHeaders(map);
        }
        if (!StrUtil.isEmpty(data.getParameters())) {
            Map <String, String> map = JSON.parseObject(data.getParameters() , Map.class);
            builder.addParams(map);
        }
        return builder.build();
    }
}
