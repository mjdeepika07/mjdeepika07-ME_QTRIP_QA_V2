package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase_02 {

    static RemoteWebDriver driver;
    static String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";

    static ExtentReports report;
    static ExtentTest test;

    public static void logStatus(String type, String message, String status) {

        String currentDateTime = String.valueOf(java.time.LocalDateTime.now());
        System.out.println(String.format(" %s  |  %s  |  %s  |  %s  ", currentDateTime, type,
                message, status));

    }

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        logStatus("Driver", "Initializing Driver", "Start");
        
        DriverSingleton ds = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = ds.getDriver();
        System.out.println("Hashcode of driver is : " + driver.hashCode());

        // Wrapper method wrapper_navigate()
        // driver.get(homeUrl);
        SeleniumWrapper.wrapper_navigate(driver, homeUrl);

        logStatus("Driver", "Initializing Driver", "Success");

        ReportSingleton reportSingleton = ReportSingleton.getInstanceOfSingletonReportClass();
        report = reportSingleton.getReport();

        
        
    }

    @Test(priority = 2, dataProvider = "userOnboardDataFlow", dataProviderClass = DP.class,
            groups = {"Search and Filter flow"})
    public void TestCase02(String CityName, String Category_Filter, String DurationFilter,
            String ExpectedFilteredResults, String ExpecetedUnfilteredResults) throws IOException{
        
            try {
                test = report.startTest("Verify that Search & Filters work fine");
                logStatus("Page Test", "Search & Filters", "Started");

        

            HomePage homePage = new HomePage(driver);

            //homePage.navigateToHomePage(driver);

            //ExtentReports
            //Assert.assertTrue(homePage.navigateToHomePage(driver),"Navigation to Home Page NOT successful!!!");
            //test.log(LogStatus.PASS,"Navigation to Home Page is Successful!!");
            if(homePage.navigateToHomePage(driver))
                test.log(LogStatus.PASS,"Navigation to Home Page is Successful!!");
                    else
                        test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver))+"Navigation to Home Page is NOT Successful!!!");

            System.out.println("Entered Test case 02 method");
            System.out.println("City Name: " + CityName);

            homePage.searchCity(CityName);
            //Extent Reports
            //test.log(LogStatus.PASS,"Search for City is Successful!!");


            if (homePage.assertAutoCompleteText(CityName)) {
                homePage.selectCity(CityName);

                //Extent Reports
                //test.log(LogStatus.PASS,"Selection for City is Successful!!");

            }

            AdventurePage adventurePage = new AdventurePage(driver);
            adventurePage.setFilterValue(DurationFilter);
            //Extent Report
            //test.log(LogStatus.PASS,"Setting the Duration Filter Successful!!");


            adventurePage.setCategoryValue(Category_Filter);
            //Extent Report
            //test.log(LogStatus.PASS,"Setting the Category Filter Successful!!");

            Thread.sleep(2000);

            //Assert.assertEquals(adventurePage.getResultCount(),
            //        Integer.parseInt(ExpectedFilteredResults));

            //Extent Reports
            //test.log(LogStatus.PASS, "The actual filtered results and the expected filtered results are same!");
            if(adventurePage.getResultCount() == Integer.parseInt(ExpectedFilteredResults))
                test.log(LogStatus.PASS, "The actual filtered results and the expected filtered results are same!");
                else
                    test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver))+"The actual filtered results and the expected filtered results are NOT same!!!");


            Thread.sleep(2000);

            adventurePage.durationFilterClear(DurationFilter);
            //Extent Reports
            //test.log(LogStatus.PASS,"Duration filter cleared successfully");

            Thread.sleep(2000);

            adventurePage.categoryFilterClear(Category_Filter);
            //Extent Reports
            //test.log(LogStatus.PASS,"Category filter cleared successfully");

            Thread.sleep(2000);

            // convert the ExpecetedUnfilteredResults from String to int and then compare
            //Assert.assertEquals(adventurePage.getResultCount(),
            //        Integer.parseInt(ExpecetedUnfilteredResults));
            //Extent reports
            //test.log(LogStatus.PASS,"The Actual unfiltered results and Expected unfiltered results are same!!");
            if(adventurePage.getResultCount() == Integer.parseInt(ExpecetedUnfilteredResults))
                test.log(LogStatus.PASS,"The Actual unfiltered results and Expected unfiltered results are same!!");
                else
                test.log(LogStatus.PASS,test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver))+"The Actual unfiltered results and Expected unfiltered results are NOT same!!!");
        

        logStatus("Page Test", "Search & Filters", "Success");
        test.log(LogStatus.INFO, test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver)));
        
    } catch (Exception e) {
        test.log(LogStatus.FAIL,test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver))+"TestCase02 validation FAILED!!!");
        e.getStackTrace();
    }

    }

    /*@AfterTest(alwaysRun = true)
    public static void quitDriver(){
    logStatus("Driver", "Quitting Driver", "Start");
    driver.close();

    logStatus("Driver", "Quitting Driver", "Success");
    
    //Ending the test and refreshing the report to display new report
    report.endTest(test);
    report.flush();

    }*/


}
