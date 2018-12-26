package com.testpro.easyrest.Core.Interface;


import com.testpro.easyrest.bean.ExecutionData;

/**
 * @param <T>执行请求后的返回值类型
 * @param <E>            执行请求的参数类型
 */
public interface ResponseExecut<T, E extends ExecutionData> {
    T execut(E data);
}
