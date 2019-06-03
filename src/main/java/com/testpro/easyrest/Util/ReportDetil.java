package com.testpro.easyrest.Util;

import io.qameta.allure.Attachment;

import java.nio.charset.StandardCharsets;

public class ReportDetil {

  @Attachment(value = "请求报文", type = "text/plain")
  public static byte[] RequestBodyReport(String requestInfo) {
    return requestInfo.getBytes(StandardCharsets.UTF_8);
  }

  @Attachment(value = "响应报文", type = "text/plain")
  public static byte[] RespondBodyReport(String respondBody) {
    // 报告展现响应报文
    return respondBody.getBytes(StandardCharsets.UTF_8);
  }
}
