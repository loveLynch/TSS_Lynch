//package com.silver.tss;
//
//import com.silver.tss.utils.ExcelUtils;
//import org.apache.poi.util.IOUtils;
//import org.junit.Test;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
///**
// * Created by lynch on 2018/10/21. <br>
// **/
//public class TestImportorExportExcel {
//    @Test
//    public void testexcel() throws IOException {
//        File file = new File("1.xlsx");
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
//        ExcelUtils excelUtils = new ExcelUtils();
//        excelUtils.importexcel(multipartFile);
//    }
//}
