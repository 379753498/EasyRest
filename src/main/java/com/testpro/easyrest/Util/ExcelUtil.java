package com.testpro.easyrest.Util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.*;
import java.util.List;

public class ExcelUtil {

  private ExcelTypeEnum excelTypeEnum = ExcelTypeEnum.XLSX;

  private boolean needHead = true;
  private int sheetNo = 1;
  private int headLineMun = 1;

  public void setSheetNo(int sheetNo) {
    this.sheetNo = sheetNo;
  }

  public void setHeadLineMun(int headLineMun) {
    this.headLineMun = headLineMun;
  }

  public void setNeedHead(boolean needHead) {
    this.needHead = needHead;
  }

  public void setExcelTypeEnum(ExcelTypeEnum excelTypeEnum) {
    this.excelTypeEnum = excelTypeEnum;
  }

  /**
   * @param file 文件
   * @param sheetNO sheet number
   * @param headLineMun 头行数
   * @param BeanClass 实体对象
   * @param excelListener 监听器
   * @param <T> 泛型必须继承BaseRowModel
   * @return List<T> 对象数组</>
   * @throws FileNotFoundException 文件找不到的异常
   */
  public <T extends BaseRowModel> List<T> readExcelReturnListBean(
      File file, int sheetNO, int headLineMun, Class<T> BeanClass, ExcelListener<T> excelListener)
      throws FileNotFoundException {
    InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
    readBySax(sheetNO, headLineMun, BeanClass, inputStream, excelListener);
    return excelListener.getObjectData();
  }

  /**
   * @param file 文件
   * @param sheetNo sheet number
   * @param headLineMun 头行数
   * @param BeanClass 实体对象
   * @param <T> 泛型必须继承BaseRowModel
   * @return List<T> 对象数组</>
   * @throws FileNotFoundException 文件找不到的异常
   */
  private <T extends BaseRowModel> List<T> readExcelReturnListBean(
      File file, int sheetNo, int headLineMun, Class<T> BeanClass) throws FileNotFoundException {
    InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
    ExcelListener<T> excelListener =
        new ExcelListener<T>() {
          @Override
          public void invoke(T t, AnalysisContext analysisContext) {
            list.add(t);
          }
        };
    readBySax(sheetNo, headLineMun, BeanClass, inputStream, excelListener);
    return excelListener.getObjectData();
  }

  /**
   * 默认的读取第一个Sheet 第一行是表头 开放set方法设置 sheetNo 和headLineMun
   *
   * @param file file 文件对象
   * @param BeanClass BeanClass 实体对象需要继承BaseRowModel
   * @param <T> T 实体对象需要继承BaseRowModel
   * @return List<BeanClass>实体对象
   * @throws FileNotFoundException 文件找不到异常
   */
  public <T extends BaseRowModel> List<T> readExcelReturnListBean(File file, Class<T> BeanClass)
      throws FileNotFoundException {
    return readExcelReturnListBean(file, this.sheetNo, this.headLineMun, BeanClass);
  }

  /**
   * @param sheetNo sheetNumber
   * @param headLineMun 表头行数
   * @param BeanClass 实体对象 需要继承 @see BaseRowModel
   * @param inputStream 目标文件流
   * @param excelListener 监听器对象
   * @param <T> T extends BaseRowModel
   */
  private <T extends BaseRowModel> void readBySax(
      int sheetNo,
      int headLineMun,
      Class<T> BeanClass,
      InputStream inputStream,
      ExcelListener<T> excelListener) {
    EasyExcelFactory.readBySax(
        inputStream, new Sheet(sheetNo, headLineMun, BeanClass), excelListener);
  }

  public <T extends BaseRowModel> void WriterExcelWithListBean(
      File file, List<T> list, Class<T> tClass) throws FileNotFoundException {
    Sheet sheet = new Sheet(this.sheetNo, this.headLineMun, tClass);
    WriterExcelWithListBean(file, list, sheet);
  }

  public <T extends BaseRowModel> void WriterExcelWithListBean(
      File file, List<T> list, Class<T> tClass, int sheetNo, int StartRow, int HeadLinMun)
      throws FileNotFoundException {
    Sheet sheet = new Sheet(sheetNo, HeadLinMun, tClass);
    sheet.setStartRow(StartRow);
    WriterExcelWithListBean(file, list, sheet);
  }

  private <T extends BaseRowModel> void WriterExcelWithListBean(
      File file, List<T> list, Sheet sheet) throws FileNotFoundException {
    OutputStream out = new FileOutputStream(file);
    ExcelWriter writer = EasyExcelFactory.getWriter(out, this.excelTypeEnum, this.needHead);
    writer.write(list, sheet);
    writer.finish();
    try {
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
