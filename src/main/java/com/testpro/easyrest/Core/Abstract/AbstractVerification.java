package com.testpro.easyrest.Core.Abstract;

import com.testpro.easyrest.Core.Interface.Verification;
import com.testpro.easyrest.bean.ExecutionData;
import io.restassured.response.Response;

public abstract class AbstractVerification implements Verification <Response, ExecutionData> {
    @Override
    public void executVerification(Response response, ExecutionData executionData) {

        if (executionData.getRetrunvauleCheck() != null) {
            ResponsevauleCheck(response, executionData);
        }
        if (executionData.getRetrunJsonPathCheck() != null && executionData.getRetruntype() != null && executionData.getRetruntype().equals("json")) {
            ResponseJsonPathCheck(response, executionData);
        }
        if (executionData.getRetrunCharacterString() != null) {
            ResponseCharacterString(response, executionData);
        }

    }

    protected abstract void ResponsevauleCheck(Response response, ExecutionData executionData);

    protected abstract void ResponseJsonPathCheck(Response response, ExecutionData executionData);

    protected abstract void ResponseCharacterString(Response response, ExecutionData executionData);
}
