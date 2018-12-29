package com.testpro.easyrest.Util;


import io.qameta.allure.Attachment;


public class ReportDetil {



    @Attachment("请求报文")
    public static String requestBody(String requestInfo) {
        return requestInfo;
    }

    @Attachment("响应报文")
    public static String respondBody(String respondBody) {
        //报告展现响应报文
        return JsonUtil.JsonPretty(respondBody);
    }

}

