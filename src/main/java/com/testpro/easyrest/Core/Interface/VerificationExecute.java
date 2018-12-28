package com.testpro.easyrest.Core.Interface;

import com.testpro.easyrest.bean.ExecutionData;

/**
 * 根据返回数据进行验证接口
 *
 * @param <T> 需要验证的返回值类型
 * @param <E> 期望验证的数据
 */
public interface VerificationExecute<T, E extends ExecutionData> {

    void ExecutVerification(T response, E executionData);
}
