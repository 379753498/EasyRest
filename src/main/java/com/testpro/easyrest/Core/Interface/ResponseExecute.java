package com.testpro.easyrest.Core.Interface;


import com.testpro.easyrest.bean.ExecutionData;

/**
 * 根据 参数信息 执行并返回结果接口
 * @param <T>执行请求后的返回值类型
 * @param <E>            执行请求的参数类型
 */
public interface ResponseExecute<T, E extends ExecutionData> {
    T execut(E data);
}
