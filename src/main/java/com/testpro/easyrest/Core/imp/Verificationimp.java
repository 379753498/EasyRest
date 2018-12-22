package com.testpro.easyrest.Core.imp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.testpro.easyrest.Core.Abstract.AbstractVerification;
import com.testpro.easyrest.Util.JsonUtil;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class Verificationimp extends AbstractVerification {
    @Override
    protected void ResponsevauleCheck(Response response, ExecutionData executionData) {
        String asString = response.asString();
        if (executionData.getRetruntype() != null && executionData.getRetruntype().equals("json")) {
            Gson gson1 = new GsonBuilder().create();//or new Gson()
            JsonElement e1 = gson1.toJsonTree(asString);//or new Gson()
            Gson gson2 = new GsonBuilder().create();
            JsonElement e2 = gson2.toJsonTree(executionData.getRetrunvauleCheck());
            Assert.assertTrue(e1.equals(e2));
        } else {
            Assert.assertEquals(asString, executionData.getRetrunvauleCheck());
        }
    }

    @Override
    protected void ResponseJsonPathCheck(Response response, ExecutionData executionData) {
        JsonPath jsonPath = response.jsonPath();
        String retrunJsonPathCheck = executionData.getRetrunJsonPathCheck();
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

    @Override
    protected void ResponseCharacterString(Response response, ExecutionData executionData) {
        String asString = response.asString();
        String RetrunCharacterString = executionData.getRetrunCharacterString();
        System.out.println(RetrunCharacterString);
        try {
//                RetrunCharacterString.replace("\""")
            String[] split = RetrunCharacterString.split(",");
            for (String str : split) {
                Assert.assertTrue(asString.contains(str));
            }

        } catch (Exception e) {
            log.error(e.toString());
        }

    }
}
