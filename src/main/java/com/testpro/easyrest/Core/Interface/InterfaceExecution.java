package com.testpro.easyrest.Core.Interface;

import com.testpro.easyrest.bean.ExecutionData;

/**
 *   接口测试执行顶层接口
 * @param <E>期望执行的数据类型
 */
public interface InterfaceExecution<E extends ExecutionData> {

    void execution(E executionData);
}
