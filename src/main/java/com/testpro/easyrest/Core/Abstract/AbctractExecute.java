package com.testpro.easyrest.Core.Abstract;

import com.testpro.easyrest.Core.Interface.InterfaceExecution;
import com.testpro.easyrest.Core.Interface.ResponseExecute;
import com.testpro.easyrest.Core.Interface.VerificationExecute;
import com.testpro.easyrest.bean.ExecutionData;

/**
 * 聚合接口抽象类
 *
 * @param <T> 请求返回参数类
 * @param <E> 执行参数类型
 */
public abstract class AbctractExecute<T, E extends ExecutionData> implements InterfaceExecution <E>, ResponseExecute<T, E>, VerificationExecute <T, E> {







}
