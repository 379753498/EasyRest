package com.testpro.easyrest.Core.Interface;

import com.testpro.easyrest.bean.ExecutionData;

/**
 * @param <E>期望执行的数据类型
 */
public interface interfaceExecution<E extends ExecutionData> {

    void execution(E executionData);
}
