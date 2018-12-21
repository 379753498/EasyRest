package com.testpro.easyrest.Data;

import com.testpro.easyrest.Enum.DataType;

import java.util.Iterator;


public interface InterfaceDataProvider {


    Iterator <Object[]> ImplementDataProvider() ;


    DataType DatasourceName();
}
