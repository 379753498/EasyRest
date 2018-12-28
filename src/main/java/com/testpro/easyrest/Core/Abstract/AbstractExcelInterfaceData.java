package com.testpro.easyrest.Core.Abstract;

import com.testpro.easyrest.Core.Interface.InterfaceDataProvider;
import com.testpro.easyrest.Enum.DataType;

import java.util.Iterator;

/**
 * 抽象实现数据提供接口
 */
public abstract class AbstractExcelInterfaceData implements InterfaceDataProvider <Object> {
    /**
     * 文件路径
     */
    private String filepath;
    @Override
    public DataType DatasourceName() {
        return DataType.Excel;
    }

    @Override
    public Iterator <Object[]> ImplementDataProvider() {
        return impDataProvider();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /**
     * @return 回调函数由子类实现
     */
    protected abstract Iterator <Object[]> impDataProvider();
}
