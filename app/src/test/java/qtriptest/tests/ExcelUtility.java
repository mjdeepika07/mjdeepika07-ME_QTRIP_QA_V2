package qtriptest.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelUtility {
    
    @DataProvider(name="userOnboardDataFlow")
    public static String[][] readExcelFile(String sheetName) throws IOException, FileNotFoundException{
        int rowIndex = 0;
        int cellIndex = 0;
    
        List<List> outputList = new ArrayList<List>();

        FileInputStream excelFile = new FileInputStream(new File(System.getProperty("user.dir")+"/app/src/test/resources/DatasetsforQTrip.xlsx"));
        //FileInputStream excelFile = new FileInputStream(new File("/home/crio-user/workspace/mjdeepika07-ME_QTRIP_QA_V2/app/src/test/resources/DatasetsforQTrip.xlsx"));
        ///home/crio-user/workspace/mjdeepika07-ME_QTRIP_QA_V2/app/src/test/resources/DatasetsforQTrip.xlsx
        //app/src/test/resources/DatasetsforQTrip.xlsx
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet(sheetName);
        
        Iterator<Row> iteratorRow = sheet.rowIterator();
        while(iteratorRow.hasNext()){
            Row nextRow = iteratorRow.next();
            List<String> innerList = new ArrayList<>();
            Iterator<Cell> iteratorCell = nextRow.cellIterator();
            while(iteratorCell.hasNext()){
                Cell nextCell = iteratorCell.next();
                if(rowIndex > 0 && cellIndex > 0){
                    if(nextCell.getCellType() == CellType.STRING)
                        innerList.add(nextCell.getStringCellValue());
                    if(nextCell.getCellType() == CellType.NUMERIC)
                        innerList.add(String.valueOf(nextCell.getNumericCellValue()));
                }
            cellIndex = cellIndex + 1;    
            }
            rowIndex = rowIndex + 1;
            cellIndex = 0;
            if(innerList.size() > 0){
                outputList.add(innerList);
                System.out.println(innerList);
            }

        }

        excelFile.close();
        workbook.close();
        String[][] excelDataArray = outputList.stream().map(output->output.toArray(new String[0])).toArray(String[][]::new);
        return excelDataArray;
        
    
    
       /*  catch(Exception e){
            System.out.println("Exception in try block - IO exception");
            e.getStackTrace();
        }*/


        
    }
    
}
