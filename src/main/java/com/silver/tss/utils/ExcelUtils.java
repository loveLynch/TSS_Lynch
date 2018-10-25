package com.silver.tss.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import jxl.Sheet;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by lynch on 2018/10/21. <br>
 **/
public class ExcelUtils {
    JSONArray jsons = new JSONArray();
    public final static String XLS = "xls";
    public static final String XLSX = "xlsx";

    public void importexcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            if (fileName.endsWith(XLS)) {
                //获取一个Excel文件  只支持.xls格式
                Workbook workbook = Workbook.getWorkbook(file.getInputStream());
                //获取文件里的某个表  从0开始
                Sheet sheet = workbook.getSheet(0);

                for (int i = 1; i < sheet.getRows(); i++) {
                    //每一行创建一个JSONObject对象

                    JSONObject object = new JSONObject();
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        //循环读出每一数据格的数据
                        //sheet.getCell(列，行);
                        object.put(sheet.getCell(j, 0).getContents(), sheet.getCell(j, i).getContents());
                    }
                    jsons.add(object);


                }

                workbook.close();
            } else if (fileName.endsWith(XLSX)) {
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet = workbook.getSheetAt(0);
                XSSFRow row;
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);
                    if (row != null) {
                        JSONObject json = new JSONObject();
                        for (int j = 0; j < sheet.getRow(0).getPhysicalNumberOfCells(); j++) {
                            row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                            json.put(sheet.getRow(0).getCell(j).getStringCellValue(), row.getCell(j).getStringCellValue());
                        }
                        jsons.add(json);

                    }
                }

                workbook.close();

            }
            //对队列进行输出或者其他操作
            for (int i = 0; i < jsons.size(); i++) {
                System.out.println(jsons.get(i).toString());
            }

        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void exportexcel(MultipartFile file) {

    }
}
