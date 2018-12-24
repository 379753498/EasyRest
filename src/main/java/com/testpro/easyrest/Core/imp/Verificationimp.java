package com.testpro.easyrest.Core.imp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.testpro.easyrest.Core.Abstract.AbstractVerification;
import com.testpro.easyrest.Util.JsonUtil;
import com.testpro.easyrest.Util.ReportDetil;
import com.testpro.easyrest.Util.Verify;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;


@Component
@Slf4j
public class Verificationimp extends AbstractVerification {
    @Override
    protected void ResponsevauleCheck(Response response, ExecutionData executionData) {
        HashMap<String, Object> map = new HashMap<>();
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
    protected void ResponseJsonPathCheck(Response response, ExecutionData executionData) {

        JsonPath jsonPath = response.jsonPath();
        List<HashMap<String, Object>> list = new ArrayList<>();
        String retrunJsonPathCheck = executionData.getRetrunJsonPathCheck();
        Map map = JsonUtil.FastStringtoMap(retrunJsonPathCheck);
        if (!map.isEmpty()) {
            Set set = map.keySet();
            for (Object obj : set) {
                final String key = (String) obj;
                final String vaule = (String) map.get(obj);
                // 此处不存在有解析错误的可能性 所有的解析错误都判断接口返回值错误 默认以JSonPath 解析结果为准
                String jsonpathvalue = jsonPath.get(key).toString();
                HashMap<String, Object> objectHashMap = new HashMap<>();
                boolean equals = vaule.equals(jsonpathvalue);
                objectHashMap.put("JsonPath", key);
                objectHashMap.put("预期值", vaule);
                objectHashMap.put("实际值", jsonpathvalue);
                objectHashMap.put("校验结果", equals == true ? "true" : "false");
                list.add(objectHashMap);
                try {
                    Verify.assertEquals(vaule, jsonpathvalue);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ReportDetil.ResponseJsonPathCheck(JsonUtil.toJsonArrayWithExpose(list));


        }

    }

    @Override
    protected void ResponseCharacterString(Response response, ExecutionData executionData) {
        String asString = response.asString();
        String RetrunCharacterString = executionData.getRetrunCharacterString();
        List<HashMap<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();

        try {
            String[] split = RetrunCharacterString.split(",");
            for (String str : split) {
                boolean contains = asString.contains(str);
                map.put("期望包含字符串", str);
                map.put("是否包含", contains == true ? "true" : "false");
                list.add(map);
                map = new HashMap<>();
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
