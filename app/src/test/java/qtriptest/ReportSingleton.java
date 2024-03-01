package qtriptest;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.sql.Time;
import java.sql.Timestamp;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ReportSingleton {

RemoteWebDriver driver;
private static ReportSingleton instanceOfSingleton;
private ExtentReports report;


private ReportSingleton(){

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        String timeString = String.valueOf(timeStamp.getTime());
        System.out.println("Timestamp : "+timeStamp+ "  timeString : " + timeString);


        String filePath = System.getProperty("user.dir")+"/ExtentReports/report"+timeString+".html";
        report = new ExtentReports(filePath,true);
        //ExtentTest test = report.startTest(method.getName());

        //report = new ExtentReports(System.getProperty("user.dir")+ "/report"+getTimestamp()+".html", true);
        //ExtentReports reports = new ExtentReports("Path of directory to store the resultant HTML file", true/false);

        //ReportSingleton reportSingleton = ReportSingleton.getInstanceOfSingletonReportClass();
        //report =reportSingleton.getReport();

        

}

public static ReportSingleton getInstanceOfSingletonReportClass() throws MalformedURLException{
    if(instanceOfSingleton == null){

        instanceOfSingleton = new ReportSingleton();
    }

    System.out.println("instanceOfSingleton : " + instanceOfSingleton);
    return instanceOfSingleton;

}

public ExtentReports getReport(){
    return report;
}

    
    
}
