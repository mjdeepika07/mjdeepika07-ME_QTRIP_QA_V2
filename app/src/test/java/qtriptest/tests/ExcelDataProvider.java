package qtriptest.tests;

import java.io.IOException;
import java.util.Arrays;
import org.testng.annotations.DataProvider;

public class ExcelDataProvider{

@DataProvider(name="userOnboardDataFlow")
public String[][] userOnboardFlowData() throws IOException{
    String[][] excelDataArray = ExcelUtility.readExcelFile("TestCase01");

    // Print the data for debugging purposes
    System.out.println("Data from ExcelDataProvider:");
    System.out.println(Arrays.deepToString(excelDataArray));
    // return ExcelUtility.readExcelFile("TestCase01");
    return excelDataArray;

}

}