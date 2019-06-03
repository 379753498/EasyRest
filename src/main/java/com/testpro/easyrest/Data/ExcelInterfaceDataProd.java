package com.testpro.easyrest.Data;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.testpro.easyrest.Config.EasyRestConfig;
import com.testpro.easyrest.Core.Abstract.AbstractExcelInterfaceData;
import com.testpro.easyrest.Util.ExcelUtil;
import com.testpro.easyrest.Util.TestNgUtil;
import com.testpro.easyrest.bean.ExecutionData;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class ExcelInterfaceDataProd extends AbstractExcelInterfaceData {
  private static List<Object> excelData = new ArrayList<>();
  private static Iterator<Object[]> objectList = new ArrayList<Object[]>().iterator();

  @Override
  protected Iterator<Object[]> impDataProvider() {
    String property = System.getProperty("easyrest.restassured.init");
    if (StrUtil.isEmpty(property)) {
      EasyRestConfig.initGlobalConfigSetting();
    }
    if (!StrUtil.isEmpty(EasyRestConfig.getFilepath())) {
      this.setFilepath(EasyRestConfig.getFilepath());
    }
    afterPropertiesSet();
    return objectList;
  }

  private void afterPropertiesSet() {
    File file;
    if (!StrUtil.isEmpty(this.getFilepath())) {
      file = new File(this.getFilepath());
      log.info("正在使用easyrest.exceldata.filepath 配置{}的文件路径进行加载！！", this.getFilepath());
    } else {
      file = new ClassPathResource("ExcelData.xlsx").getFile();
      log.info("正在使用ClasPath:ExcelData.xlsx数据驱动文件进行加载");
    }
    ExcelUtil excelUtil = new ExcelUtil();
    try {
      List<ExecutionData> executionDataList =
          excelUtil.readExcelReturnListBean(file, ExecutionData.class);
      if (!CollUtil.isEmpty(executionDataList)) {
        for (ExecutionData data : executionDataList) {
          excelData.add(data);
        }
        objectList = TestNgUtil.createObjectList(excelData);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
