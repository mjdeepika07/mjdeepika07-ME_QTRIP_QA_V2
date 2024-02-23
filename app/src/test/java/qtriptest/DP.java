package qtriptest;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DP {
    
    @DataProvider(name = "userOnboardDataFlow")
    public Object[][] dpmethod(Method m){

        List<List> listOfRows = new ArrayList<List>();
        
        

        int rowIndex = 0;
        int cellIndex = 0;

        String filepath = "/home/crio-user/workspace/mjdeepika07-ME_QTRIP_QA_V2/app/src/test/resources/DatasetsforQTrip.xlsx";
        File file = new File(filepath);
        try{
        FileInputStream fileInputStream = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet(m.getName());
        System.out.println("m.getName() : " + m.getName());
        Iterator<Row> rowiterator = sheet.rowIterator();
        

        while(rowiterator.hasNext()){
            Row row = rowiterator.next();
            Iterator<Cell> celliterator = row.cellIterator();
            List<String> listOfCells = new ArrayList<String>();

            while(celliterator.hasNext()){
                
                Cell cell = celliterator.next();
                
                if(rowIndex > 0 && cellIndex > 0){
                    if (cell.getCellType() == CellType.STRING) {
                        listOfCells.add(cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        listOfCells.add(String.valueOf(cell.getNumericCellValue()));
                    } else if (cell.getCellType() == CellType.BOOLEAN) {
                        listOfCells.add(String.valueOf(cell.getBooleanCellValue()));
                    }
                } 
                cellIndex = cellIndex + 1;   
            }
            rowIndex = rowIndex + 1;
            cellIndex = 0;

            if(listOfCells.size() > 0){
                //System.out.println("listOfCells : " + listOfCells);
                listOfRows.add(listOfCells);
                //System.out.println("listOfRows : " + listOfRows);
               

            }
        }
        
        fileInputStream.close();
        workbook.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.getStackTrace();
        }    
        String[][] stringArray = listOfRows.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        return stringArray;
    }


    /*public static void main(String[] args){
        DP dp = new DP();
        dp.dpmethod();
    }*/

}
