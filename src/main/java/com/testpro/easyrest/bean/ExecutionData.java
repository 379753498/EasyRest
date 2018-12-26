package com.testpro.easyrest.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 数据参数模型对象
 */
@Getter
@Setter
@ToString
public class ExecutionData extends BaseRowModel {

    @ExcelProperty(index = 0, value = "用例名称")
    private String caseName;
    @ExcelProperty(index = 1, value = "用例描述")
    private String caseDescription;
    @ExcelProperty(index = 2, value = "地址")
    private String url;
    @ExcelProperty(index = 3, value = "头信息")
    private String headers;
    @ExcelProperty(index = 4, value = "参数信息")
    private String parameters;
    @ExcelProperty(index = 5, value = "方法")
    private String method;
    @ExcelProperty(index = 6, value = "返回值类型")
    private String retruntype;
    @ExcelProperty(index = 7, value = "返回值校验")
    private String retrunvauleCheck;
    @ExcelProperty(index = 8, value = "jsonPath校验")
    private String retrunJsonPathCheck;
    @ExcelProperty(index = 9, value = "字符包含校验")
    private String retrunCharacterString;


}
