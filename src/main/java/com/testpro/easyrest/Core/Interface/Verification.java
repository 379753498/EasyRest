package com.testpro.easyrest.Core.Interface;

import com.testpro.easyrest.bean.ExecutionData;

/**
 * @param <T> 需要验证的返回值类型
 * @param <E> 期望验证的数据
 */
public interface Verification<T, E extends ExecutionData> {

    void executVerification(T response, E executionData);
}
