package com.testpro.easyrest.Core.Interface;

import com.testpro.easyrest.Enum.DataType;

import java.util.Iterator;


public interface InterfaceDataProvider {


    Iterator <Object[]> ImplementDataProvider() ;


    DataType DatasourceName();
}
