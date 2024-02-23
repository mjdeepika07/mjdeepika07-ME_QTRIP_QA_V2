package qtriptest.tests;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase_02{

    static RemoteWebDriver driver;
    static String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";

    public static void logStatus(String type, String message, String status){

        String currentDateTime = String.valueOf(java.time.LocalDateTime.now());
        System.out.println(String.format(" %s  |  %s  |  %s  |  %s  ", currentDateTime, type, message, status));

    }

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException{
        logStatus("Driver", "Initializing Driver", "Start");
        DriverSingleton ds = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = ds.getDriver();
        System.out.println("Hashcode of driver is : " + driver.hashCode());
        driver.get(homeUrl);
        logStatus("Driver", "Initializing Driver", "Success");
    }  

    @Test(priority = 2, dataProvider = "userOnboardDataFlow", dataProviderClass=DP.class,groups={"Search and Filter flow"})
    public void TestCase02(String CityName, String Category_Filter, String DurationFilter, String ExpectedFilteredResults, String ExpecetedUnfilteredResults){
        
        logStatus("Page Test", "Search & Filters", "Started");
        
        try{

        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage(driver);
        System.out.println("Entered Test case 02 method");
        System.out.println("City Name: " + CityName);

        homePage.searchCity(CityName);

        if(homePage.assertAutoCompleteText(CityName)){
            homePage.selectCity(CityName);
        }

        AdventurePage adventurePage = new AdventurePage(driver);
        adventurePage.setFilterValue(DurationFilter);
        
        adventurePage.setCategoryValue(Category_Filter);
    
        Thread.sleep(2000);

        Assert.assertEquals(adventurePage.getResultCount(),Integer.parseInt(ExpectedFilteredResults));

        Thread.sleep(2000);
           
        adventurePage.durationFilterClear(DurationFilter);
        
        Thread.sleep(2000);

        adventurePage.categoryFilterClear(Category_Filter);
        Thread.sleep(2000);
            

        //convert the ExpecetedUnfilteredResults from String to int and then compare
        Assert.assertEquals(adventurePage.getResultCount(),Integer.parseInt(ExpecetedUnfilteredResults));
        }
        catch(Exception e){
            e.getStackTrace();
        }

        logStatus("Page Test", "Search & Filters", "Success");


    }

    // @AfterTest(alwaysRun = true)
    // public static void quitDriver(){
    //     logStatus("Driver", "Quitting Driver", "Start");
    //     driver.close();
    //     logStatus("Driver", "Quitting Driver", "Success");
    // }


}
