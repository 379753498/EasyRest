package com.testpro.easyrest.Core.imp;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.testpro.easyrest.Core.Abstract.AbctractRestAssuredExecute;
import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import com.testpro.easyrest.Util.JsonUtil;
import com.testpro.easyrest.Util.ReportDetil;
import com.testpro.easyrest.Util.RestAssuredUtil;
import com.testpro.easyrest.Util.Verify;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
@Slf4j
public class RestAssuredImp extends AbctractRestAssuredExecute {

    /**
     * 请求工具类
     */
    @Autowired
    RestAssuredUtil assuredUtil;
    /**
     * 初始化配置接口
     */
    @Autowired
    InitialConfiguration initialConfiguration;

    @Override
    public void execution(ExecutionData executionData) {
        ReportDetil.requestBody(rquestbodyData(executionData));//设置测试报告的参数列表
        initialConfiguration.InitialConfiguration();//初始化配置接口并进行设置
        Response response = super.execut(executionData);//执行请求并拿到返回值
        log.info(response.asString());
        if (executionData.getRetruntype().equals("json")) {//如果预期结果是json则展示在报告中
            ReportDetil.respondBody(response.asString());
        }
        super.ExecutVerification(response, executionData);//执行验证参数功能
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

    @Override
    public Response UrlHeadParam(ExecutionData executionData) {


        Map headers;
        Map Parameters;
        try {
            headers = JsonUtil.FastStringtoMap(executionData.getHeaders());
            Parameters = JsonUtil.FastStringtoMap(executionData.getParameters());
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("参数解析异常请核对后在播");
        }
        switch (executionData.getMethod()) {
            case "get":
                return assuredUtil.GetResponse(executionData.getUrl(), Parameters, headers);
            case "post":
                return assuredUtil.PostResponse(executionData.getUrl(), Parameters, headers);
            default:
                log.error("框架暂时不支持 post get 之外其他请求");
                throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
        }

    }

    @Override
    public Response UrlParam(ExecutionData executionData) {

        Map Parameters;
        try {
            Parameters = JsonUtil.FastStringtoMap(executionData.getParameters());
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("参数解析异常请核对后在播");
        }
        switch (executionData.getMethod()) {
            case "get":
                return assuredUtil.GetResponseparameters(executionData.getUrl(), Parameters);
            case "post":
                return assuredUtil.PostResponseparameters(executionData.getUrl(), Parameters);
            default:
                log.error("框架暂时不支持 post get 之外其他请求");
                throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
        }
    }

    @Override
    public Response UrlHead(ExecutionData executionData) {

        Map headers;
        try {
            headers = JsonUtil.FastStringtoMap(executionData.getHeaders());
        } catch (Exception e) {
            log.error(e.toString());
            log.error("参数解析异常请核对后在播");
            throw new RuntimeException("参数解析异常请核对后在播");
        }
        switch (executionData.getMethod()) {
            case "get":
                return assuredUtil.GetResponseheards(executionData.getUrl(), headers);
            case "post":
                return assuredUtil.PostResponseheards(executionData.getUrl(), headers);

            default:
                log.error("框架暂时不支持 post get 之外其他请求");
                throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
        }
    }

    @Override
    public Response Url(ExecutionData executionData) {
        if (executionData.getMethod() != null) {
            {
                switch (executionData.getMethod()) {
                    case "get":
                        return assuredUtil.GetResponse(executionData.getUrl());
                    case "post":
                        return assuredUtil.PostResponse(executionData.getUrl());
                    default:
                        log.error("框架暂时不支持 post get 之外其他请求");
                        throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
                }
            }
        } else {
            log.error("url is  null Exception please check try agian");
            throw new RuntimeException("url is null");
        }
    }

    @Override
    public void ResponseVauleCheck(Response response, ExecutionData executionData) {
        HashMap <String, Object> map = new HashMap <>();
        String asString = response.asString();
        if (executionData.getRetruntype() != null && executionData.getRetruntype().equals("json")) {
            Gson gson1 = new GsonBuilder().create();//or new Gson()
            JsonElement e1 = gson1.toJsonTree(asString);//or new Gson()
            Gson gson2 = new GsonBuilder().create();
            JsonElement e2 = gson2.toJsonTree(executionData.getRetrunvauleCheck());
            map.put("验证类型", "返回值验证");
            map.put("预期值", JsonUtil.FastStringtoJSONObject(e2.getAsString()));
            map.put("实际值", JsonUtil.FastStringtoJSONObject(asString));
            map.put("验证结果", e1.equals(e2));
            ReportDetil.ResponsevauleCheck(JsonUtil.FastObjectToJsonString(map));
            Verify.assertTrue(e1.equals(e2));
        } else {
            map.put("验证类型", "返回值验证");
            map.put("预期值", executionData.getRetrunvauleCheck());
            map.put("实际值", asString);
            map.put("验证结果", asString.equals(executionData.getRetrunvauleCheck()));
            ReportDetil.ResponsevauleCheck(JsonUtil.FastObjectToJsonString(map));
            try {
                Verify.assertEquals(asString, executionData.getRetrunvauleCheck());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void ResponseJsonPathCheck(Response response, ExecutionData executionData) {

        JsonPath jsonPath = response.jsonPath();
        List <HashMap <String, Object>> list = new ArrayList <>();
        String retrunJsonPathCheck = executionData.getRetrunJsonPathCheck();
        Map map = JsonUtil.FastStringtoMap(retrunJsonPathCheck);
        if (!map.isEmpty()) {
            Set set = map.keySet();
            for (Object obj : set) {
                final String key = (String) obj;
                final String vaule = (String) map.get(obj);
                // 此处不存在有解析错误的可能性 所有的解析错误都判断接口返回值错误 默认以JSonPath 解析结果为准
                String JsonPathValue = jsonPath.get(key).toString();
                HashMap <String, Object> objectHashMap = new HashMap <>();
                boolean equals = vaule.equals(JsonPathValue);
                objectHashMap.put("JsonPath", key);
                objectHashMap.put("预期值", vaule);
                objectHashMap.put("实际值", JsonPathValue);
                objectHashMap.put("校验结果", equals ? "true" : "false");
                list.add(objectHashMap);
                try {
                    Verify.assertEquals(vaule, JsonPathValue);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ReportDetil.ResponseJsonPathCheck(JsonUtil.toJsonArrayWithExpose(list));


        }

    }

    @Override
    public void ResponseCharacterString(Response response, ExecutionData executionData) {
        String asString = response.asString();
        String RetrunCharacterString = executionData.getRetrunCharacterString();
        List <HashMap <String, Object>> list = new ArrayList <>();
        HashMap <String, Object> map = new HashMap <>();

        try {
            String[] split = RetrunCharacterString.split(",");
            for (String str : split) {
                boolean contains = asString.contains(str);
                map.put("期望包含字符串", str);
                map.put("是否包含", contains ? "true" : "false");
                list.add(map);
                map = new HashMap <>();
                try {
                    Verify.assertTrue(asString.contains(str));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ReportDetil.ResponseCharacterString(JsonUtil.toJsonArrayWithExpose(list));
        } catch (Exception e) {
            log.error(e.toString());
        }
    }


}
