package com.testpro.easyrest;

import com.testpro.easyrest.Core.imp.RestAssuredImp;
import com.testpro.easyrest.bean.ExecutionData;

public class Test {

  public static void main(String[] args) {

    RestAssuredImp restAssuredImp = new RestAssuredImp();
    ExecutionData executionData = new ExecutionData();
    executionData.setCaseName("hello world");
    executionData.setUrl("https://www.apiopen.top/login");
    executionData.setMethod("GET");
    executionData.setHeaders(
        "{\n" + " \"Content-Type\": \"application/json\",\n" + " \"charset\": \"UTF-8\"\n" + "}");
    executionData.setCaseDescription("case ");
    executionData.setParameters(
        "{\"key\":\"00d91e8e0cca2b76f515926a36db68f5\",\"phone\":\"13594347817\",\"passwd\":\"123456\"}");
    executionData.setResultJsonPathCheck(
        "{\"code\":\"200\",\"data.key\":\"00d91e8e0cca2b76f515926a36db68f5\"}");
    restAssuredImp.execution(executionData);

    ExecutionData data = new ExecutionData();
    data.setCaseName("${url}");
    data.setUrl("https://www.apiopen.top/login");
    data.setMethod("${method}");
    data.setHeaders(
        "{\n" + " \"Content-Type\": \"application/json\",\n" + " \"charset\": \"UTF-8\"\n" + "}");
    data.setCaseDescription("case ");
    data.setParameters("{\"key\":\"${key}\",\"phone\":\"${phone}\",\"passwd\":\"${passwd}\"}");
    data.setResultJsonPathCheck("{\"code\":\"200\",\"data.key\":\"${key}+\"}");
    restAssuredImp.execution(data);

    //        ClientAndServer mockServer =  startClientAndServer(8080);
    //        new MockServerClient("127.0.0.1", 8081)
    //                .when(
    //                        request()
    //                                .withMethod("GET")
    //                                .withPath("/login1")
    //                )
    //                .respond(
    //                        response()
    //                                .withStatusCode(200)
    //                                .withCookie(
    //                                        "sessionId", "2By8LOhBmaW5nZXJwcmludCIlMDAzMW"
    //                                )
    //                                .withHeader(
    //                                        "Location", "https://www.mock-server.com"
    //                                ).withBody("hello bo1dy")
    //                );

    //        MockServerClient mockServerClient = new MockServerClient("127.0.0.1", 8081);
    //        mockServerClient.when(new
    // HttpRequest().withMethod("GET").withPath("/login2")).respond(HttpResponse.response().withBody("hello world"));

  }
}
