package com.testpro.easyrest.Util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class ExcelUtil {

    /**
     * @param file        　文件
     * @param SeetNo      　sheet number
     * @param headLineMun Table NAME ROW
     * @param Beanclass   Want Bean Class
     * @return
     * @throws FileNotFoundException
     */
    private ExcelTypeEnum excelTypeEnum = ExcelTypeEnum.XLSX;
    private boolean Needhead = true;

    public void setNeedhead(boolean needhead) {
        Needhead = needhead;
    }

    public void setExcelTypeEnum(ExcelTypeEnum excelTypeEnum) {
        this.excelTypeEnum = excelTypeEnum;
    }

    /**
     * @param file
     * @param SheetNO
     * @param headLineMun
     * @param Beanclass
     * @param excelListener
     * @param <T>
     * @return
     * @throws FileNotFoundException
     */
    public <T extends BaseRowModel> List <T> readExcelReturnListBean(File file, int SheetNO, int headLineMun, Class <T> Beanclass, ExcelListener <T> excelListener) throws FileNotFoundException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        readBysax(SheetNO, headLineMun, Beanclass, inputStream, excelListener);
        return excelListener.getObjectdata();
    }


    public <T extends BaseRowModel> List <T> readExcelReturnListBean(File file, int SheetNO, int headLineMun, Class <T> Beanclass) throws FileNotFoundException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        ExcelListener <T> excelListener = new ExcelListener <T>() {
            @Override
            public void invoke(T t, AnalysisContext analysisContext) {
                list.add(t);
            }
        };
        readBysax(SheetNO, headLineMun, Beanclass, inputStream, excelListener);
        return excelListener.getObjectdata();
    }

    /**
     * 默认的读取第一个Sheet 第一行是表头
     *
     * @param file
     * @param Beanclass
     * @return
     * @throws FileNotFoundException
     */
    public <T extends BaseRowModel> List <T> readExcelReturnListBean(File file, Class <T> Beanclass) throws FileNotFoundException {
        return readExcelReturnListBean(file, 1, 1, Beanclass);
    }


    private <T extends BaseRowModel> void readBysax(int SeetNo, int headLineMun, Class <T> Beanclass, InputStream inputStream, ExcelListener <T> excelListener) {
        EasyExcelFactory.readBySax(inputStream, new Sheet(SeetNo, headLineMun, Beanclass), excelListener);
    }


    public <T extends BaseRowModel> void WriterExcelWithListBean(File file, List <T> list, Class <T> tClass) throws FileNotFoundException {
        Sheet sheet = new Sheet(1, 1, tClass);
        WriterExcelWithListBean(file, list, sheet);
    }

    public <T extends BaseRowModel> void WriterExcelWithListBean(File file, List <T> list, Class <T> tClass, int sheetNo, int StartRow, int HeadLinMun) throws FileNotFoundException {
        Sheet sheet = new Sheet(sheetNo, HeadLinMun, tClass);
        sheet.setStartRow(StartRow);
        WriterExcelWithListBean(file, list, sheet);
    }

    public <T extends BaseRowModel> void WriterExcelWithListBean(File file, List <T> list, Sheet sheet) throws FileNotFoundException {
        OutputStream out = new FileOutputStream(file);
        ExcelWriter writer = EasyExcelFactory.getWriter(out, this.excelTypeEnum, this.Needhead);
        writer.write(list, sheet);
        writer.finish();
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
