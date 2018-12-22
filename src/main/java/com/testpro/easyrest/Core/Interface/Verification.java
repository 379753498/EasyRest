package com.testpro.easyrest.Core.Interface;

import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.response.Response;

public interface Verification {

     void executVerification(Response response, ExecutionData executionData) ;
}
