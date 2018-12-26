package com.testpro.easyrest.Core.Abstract;

import com.testpro.easyrest.Core.Interface.InterfaceDataProvider;
import com.testpro.easyrest.Enum.DataType;

import java.util.Iterator;

public abstract class AbstractExcelInterfaceData implements InterfaceDataProvider <Object> {

    private String filepath;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public DataType DatasourceName() {
        return DataType.Excel;
    }

    @Override
    public Iterator <Object[]> ImplementDataProvider() {
        return impDataProvider();
    }

    /**
     * @return 回调函数由子类实现
     */
    protected abstract Iterator <Object[]> impDataProvider();
}
