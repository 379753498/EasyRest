package com.testpro.easyrest.Data;

import com.testpro.easyrest.Enum.DataType;

import java.util.Iterator;

public  abstract  class AbstractExcelInterfaceData implements InterfaceDataProvider {

    private String FilePath;

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }


    @Override
    public DataType DatasourceName() {
        return DataType.Excel;
    }
    @Override
    public Iterator<Object[]> ImplementDataProvider(){
        return impDataProvider();
     }

    /**
     *
     * @return 回调函数由子类实现
     */
    abstract  Iterator<Object[]> impDataProvider();
}
