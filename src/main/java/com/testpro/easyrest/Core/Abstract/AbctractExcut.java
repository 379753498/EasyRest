package com.testpro.easyrest.Core.Abstract;

import com.testpro.easyrest.Core.Interface.InterfaceExecution;
import com.testpro.easyrest.Core.Interface.ResponseExecut;
import com.testpro.easyrest.Core.Interface.Verification;
import com.testpro.easyrest.bean.ExecutionData;

/**
 * 聚合接口抽象类
 *
 * @param <T> 请求返回参数类
 * @param <E> 执行参数类型
 */
public abstract class AbctractExcut<T, E extends ExecutionData> implements InterfaceExecution <E>, ResponseExecut <T, E>, Verification <T, E> {


    public abstract T UrlHeadParam(E executionData);

    public abstract T UrlParam(E executionData);

    public abstract T UrlHead(E executionData);

    public abstract T Url(E executionData);

    public abstract void ResponseVauleCheck(T response , E executionData);

    public abstract void ResponseJsonPathCheck(T response, E executionData);

    public abstract void ResponseCharacterString(T response, E executionData);
}
