package com.testpro.easyrest.Core.Interface;

import com.testpro.easyrest.Enum.DataType;

import java.util.Iterator;

/**
 * @param <T> 期望返回的数据类型
 */

public interface InterfaceDataProvider<T> {


    Iterator <T[]> ImplementDataProvider();


    DataType DatasourceName();
}
