package com.testpro.easyrest.Core.Abstract;

import com.testpro.easyrest.Core.Interface.InterfaceExecution;
import com.testpro.easyrest.bean.ExecutionData;

/**
 * 聚合接口抽象类
 *
 * @param <T> 请求返回参数类
 * @param <E> 执行参数类型
 */
public abstract class AbctractExecute<T, E extends ExecutionData> implements InterfaceExecution<E> {

    //抽象执行方法
    protected abstract T executResponse(E data);

    //抽象验证方法
    protected abstract void ExecutVerification(T response, E data);

}
