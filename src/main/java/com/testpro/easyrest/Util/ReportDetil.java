package com.testpro.easyrest.Util;


import io.qameta.allure.Attachment;


public class ReportDetil {



    @Attachment("请求报文")
    public static String requestBody(String requestInfo) {
        return JsonUtil.JsonPretty(requestInfo);
    }


    @Attachment("响应报文")
    public static String respondBody(String respondBody) {
        //报告展现响应报文
        return JsonUtil.JsonPretty(respondBody);
    }


    @Attachment("响应报文包含断言结果")
    public static String ResponseCharacterString(String assertResult) {
        return JsonUtil.JsonArraygood(assertResult);
    }

    @Attachment("返回值校验断言结果")
    public static String ResponsevauleCheck(String assertResult) {
        return JsonUtil.JsonPretty(assertResult);

    }
    @Attachment("JsonPath校验断言结果")
    public static String ResponseJsonPathCheck(String assertResult) {
        return JsonUtil.JsonArraygood(assertResult);
    }


}

