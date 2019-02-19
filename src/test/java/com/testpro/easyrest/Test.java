package com.testpro.easyrest;

import com.testpro.easyrest.Core.imp.RestAssuredImp;
import com.testpro.easyrest.bean.ExecutionData;

public class Test {

    public static void main(String[] args) {

        ExecutionData executionData = new ExecutionData();
        executionData.setCaseName("hello world");
        executionData.setUrl("https://www.apiopen.top/login");
        executionData.setMethod("get");
        executionData.setHeaders("{\n" +
                " \"Content-Type\": \"application/json\",\n" +
                " \"charset\": \"UTF-8\"\n" +
                "}");
        executionData.setCaseDescription("case ");
        executionData.setParameters("{\"key\":\"00d91e8e0cca2b76f515926a36db68f5\",\"phone\":\"13594347817\",\"passwd\":\"123456\"}");
        executionData.setRetrunJsonPathCheck("{\"code\":\"200\",\"data.key\":\"00d91e8e0cca2b76f515926a36db68f5\"}");
        new RestAssuredImp().execution(executionData);

    }
}
