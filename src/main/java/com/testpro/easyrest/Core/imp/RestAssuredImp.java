package com.testpro.easyrest.Core.imp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.testpro.easyrest.Core.Interface.InitialConfiguration;
import com.testpro.easyrest.Core.Interface.ResponseExecut;
import com.testpro.easyrest.Core.Interface.Verification;
import com.testpro.easyrest.Core.Interface.interfaceExecution;
import com.testpro.easyrest.Util.JsonUtil;
import com.testpro.easyrest.bean.ExecutionData;
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
public class RestAssuredImp implements interfaceExecution {

    @Autowired
    InitialConfiguration initialConfiguration;

    @Autowired
    ResponseExecut responseExecut;
    @Autowired
    Verification verification;

    @Override
    public void execution(ExecutionData executionData) {
        initialConfiguration.InitialConfiguration();
        Response response = responseExecut.execut(executionData);
        verification.executVerification(response, executionData);
    }
}
