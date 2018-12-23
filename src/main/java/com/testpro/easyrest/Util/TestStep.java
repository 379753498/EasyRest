package com.testpro.easyrest.Util;

import com.alibaba.fastjson.JSONObject;
import io.qameta.allure.Attachment;

public class TestStep {

    public static void requestAndRespondBody(String URL, String Body, String Respond) {
        requestBody(URL, Body);
        respondBody(Respond);
    }

    @Attachment("请求报文")
    public static String requestBody(String URL, String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String str = JSONObject.toJSONString(jsonObject, true);

        //报告展现请求报文
        return URL + "\n" + str;
    }

    @Attachment("响应报文")
    public static String respondBody(String respond) {
        //报告展现响应报文
        return respond;
    }

    @Attachment("响应时间")
    public static String respondTime(String Time) {
        //报告展现响应报文
        return Time;
    }
    @Attachment("响应报文断言结果")
    public static StringBuffer assertRespond(StringBuffer assertResult) {
        //报告展现数据库断言结果
        return assertResult;
    }
    @Attachment("异常信息")
    public static StringBuffer Errorinfo(StringBuffer assertResult) {
        //报告展现数据库断言结果
        return assertResult;
    }
}

