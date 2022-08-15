import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Utils {
    public static void readFromExcl(String filepath,String fileName, String sheetName) throws IOException {
        File file=new File(filepath+ "//"+fileName);
        FileInputStream inputStream=new FileInputStream(file);
        Workbook workbook=null;
        String fileExtensionName=fileName.substring(fileName.indexOf("."));
        if(fileExtensionName.equals(".xls")){
            workbook=new HSSFWorkbook(inputStream);
        }
        Sheet sheet=workbook.getSheet(sheetName);
        int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
        for (int i=0; i<rowCount; i++){
            Row row=sheet.getRow(i);
            for (int j=0; j< row.getLastCellNum();j++){
                System.out.print((row.getCell(i).getStringCellValue())+"||");
            }
            System.out.println();
        }


    }
}
