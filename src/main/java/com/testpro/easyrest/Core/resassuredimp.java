package com.testpro.easyrest.Core;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.testpro.easyrest.Config.EasyRestConfig;
import com.testpro.easyrest.Util.JsonUtil;
import com.testpro.easyrest.Util.RestAssuredUtil;
import com.testpro.easyrest.bean.ExcelData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class resassuredimp implements interfaceExecution {

    @Autowired
    EasyRestConfig easyrestConfig;

    @Autowired
    RestAssuredUtil assuredUtil;

    @Override
    public void execution(ExcelData excelData) {
        initConfig(easyrestConfig);
        Response response = execut(excelData);
        Checkmatch(response, excelData);
    }

    private void Checkmatch(Response response, ExcelData excelData) {
        //  检测返回值是否等于预期

        String asString = response.asString();
        if (excelData.getRetrunvauleCheck() != null) {
            if (excelData.getRetruntype() != null && excelData.getRetruntype().equals("json"))
            {
                Gson gson1 = new GsonBuilder().create();//or new Gson()
                JsonElement e1 = gson1.toJsonTree(asString);//or new Gson()
                Gson gson2 = new GsonBuilder().create();
                JsonElement e2 = gson2.toJsonTree(excelData.getRetrunvauleCheck());
                Assert.assertEquals(e1.equals(e2),true);
            }
            else {
                Assert.assertEquals(asString, excelData.getRetrunvauleCheck());
            }
        }
        //josnPath校验
        if (excelData.getRetrunJsonPathCheck() != null && excelData.getRetruntype() != null && excelData.getRetruntype().equals("json")) {
            JsonPath jsonPath = response.jsonPath();
            String retrunJsonPathCheck = excelData.getRetrunJsonPathCheck();
            Map map = JsonUtil.FastStringtoMap(retrunJsonPathCheck);
            if (!map.isEmpty()) {
                Set set = map.keySet();
                for (Object obj : set) {
                    final String key = (String) obj;
                    final String vaule = (String) map.get(obj);
                    // 此处不存在有解析错误的可能性 所有的解析错误都判断接口返回值错误 默认以JSonPath 解析结果为准
                    String jsonpathvalue = jsonPath.get(key).toString();
                    Assert.assertEquals(vaule, jsonpathvalue);
                }
            }
        }
        //字符包含校验
        if (excelData.getRetrunCharacterString() != null) {
            String RetrunCharacterString = excelData.getRetrunCharacterString();
            System.out.println(RetrunCharacterString);
            try {
//                RetrunCharacterString.replace("\""")
                String[] split = RetrunCharacterString.split(",");
                for (String str : split) {
                    Assert.assertEquals(asString.contains(str), true);
                }

            } catch (Exception e) {
                log.error(e.toString());
            }

        }


    }

    private Response execut(ExcelData excelData) {
        if (excelData.getUrl() == null) {
            log.error("Url 为空 请检查数据参");
            throw new RuntimeException("Url 为空 请检查数据参数");
        }

//        有参数信息 有头的情况
        if (excelData.getParameters() != null && excelData.getHeaders() != null && excelData.getUrl() != null) {
            if (excelData.getMethod() != null) {
                Map headers = null;
                Map Parameters = null;
                try {
                    headers = JsonUtil.FastStringtoMap(excelData.getHeaders());
                    Parameters = JsonUtil.FastStringtoMap(excelData.getParameters());
                } catch (Exception e) {
                    log.error(e.toString());
                    throw new RuntimeException("参数解析异常请核对后在播");
                }
                if (excelData.getMethod().equals("get")) {
                    Response response = assuredUtil.GetResponse(excelData.getUrl(), Parameters, headers);
                    log.info(response.asString());
                    return response;
                } else if (excelData.getMethod().equals("post")) {
                    return assuredUtil.PostResponse(excelData.getUrl(), Parameters, headers);
                } else {
                    log.error("框架暂时不支持 post get 之外其他请求");
                    throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
                }
            } else {
                log.error("url is  null Exception please check try agian");
                throw new RuntimeException("url is null");
            }

        }
        //只有参数没有头信息的情况
        if (excelData.getParameters() != null && excelData.getUrl() != null && excelData.getHeaders() == null) {
            if (excelData.getMethod() != null) {
                Map Parameters = null;
                try {
                    Parameters = JsonUtil.FastStringtoMap(excelData.getParameters());
                } catch (Exception e) {
                    log.error(e.toString());
                    throw new RuntimeException("参数解析异常请核对后在播");
                }

                if (excelData.getMethod().equals("get")) {
                    Response response = assuredUtil.GetResponseparameters(excelData.getUrl(), Parameters);
                    log.info(response.asString());
                    return response;
                } else if (excelData.getMethod().equals("post")) {
                    return assuredUtil.PostResponseparameters(excelData.getUrl(), Parameters);
                } else {
                    log.error("框架暂时不支持 post get 之外其他请求");
                    throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
                }

            } else {
                log.error("url is  null Exception please check try agian");
                throw new RuntimeException("url is null");
            }
        }
        // 有头 没有参数的情况
        if (excelData.getHeaders() != null && excelData.getUrl() != null && excelData.getParameters() == null) {
            if (excelData.getMethod() != null) {
                Map headers = null;
                try {
                    headers = JsonUtil.FastStringtoMap(excelData.getHeaders());
                } catch (Exception e) {
                    log.error(e.toString());
                    log.error("参数解析异常请核对后在播");
                    throw new RuntimeException("参数解析异常请核对后在播");
                }
                if (excelData.getMethod().equals("get")) {
                    Response response = assuredUtil.GetResponseheards(excelData.getUrl(), headers);
                    log.info(response.asString());
                    return response;
                } else if (excelData.getMethod().equals("post")) {
                    return assuredUtil.PostResponseheards(excelData.getUrl(), headers);

                } else {
                    log.error("框架暂时不支持 post get 之外其他请求");
                    throw new RuntimeException("框架暂时不支持 post get 之外其他请求");
                }
            } else {
                log.error("url is  null Exception please check try agian");
                throw new RuntimeException("url is null");
            }
        }
        // 无头 无参数 纯url
        else {
            if (excelData.getMethod() != null) {
                {
                    if (excelData.getMethod().equals("get")) {
                        Response response = assuredUtil.GetResponse(excelData.getUrl());
                        log.info(response.asString());
                        return response;
                    } else if (excelData.getMethod().equals("post")) {
                        return assuredUtil.PostResponse(excelData.getUrl());
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

    private void initConfig(EasyRestConfig easyrestConfig) {
        if (easyrestConfig.getBaseurl().equals("")) {
            log.warn("默认url没有填写请务必在URL中填写全部URL 否则会出现错误");
        } else {
            assuredUtil.setBaseurl(easyrestConfig.getBaseurl());
        }


    }
}
