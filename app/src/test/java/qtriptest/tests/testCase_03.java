package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase_03 {

    static RemoteWebDriver driver;
    static String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";

    public static void logStatus(String type, String message, String status){

        String currentDateTime = String.valueOf(java.time.LocalDateTime.now());
        System.out.println(String.format(" %s  |  %s  |  %s  |  %s  ", currentDateTime, type, message, status));

    }

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException{

        logStatus("Driver Test", "Driver creation is starting", "Start");


        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();

        System.out.println("The HashCode of the driver is : " + driver.hashCode());

        driver.get(homeUrl);


        logStatus("Driver Test", "Driver creation is starting", "Success");


    }

    @Test(priority = 3,dataProvider = "userOnboardDataFlow", dataProviderClass = DP.class,groups={"Booking and Cancellation Flow"})
    public void TestCase03(String NewUserName, String Password, String  SearchCity, String AdventureName, String GuestName, String Date, String count) throws InterruptedException{
        
        logStatus("Booking and Cancellation flow", "Verify that adventure booking and cancellation works fine", "Start");
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage(driver);

        RegisterPage registerpage = new RegisterPage(driver);
        Assert.assertTrue(registerpage.registerNewUser(NewUserName, Password, Password, true),"User registration failed!!!!!");
       
        LoginPage login = new LoginPage(driver);
        Assert.assertTrue(login.performLogin(RegisterPage.lastGeneratedUsername,RegisterPage.lastGeneratedPassword),"The perform Login is NOT successful!!");
        
        Thread.sleep(2000);

        homePage.searchCity(SearchCity);

        if(homePage.assertAutoCompleteText(SearchCity)){
            homePage.selectCity(SearchCity);
        }

        String adventurePageCurrentUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city="+SearchCity;
        //Assert.assertTrue(driver.getCurrentUrl().equals(adventurePageCurrentUrl),"Navigation to adventure page NOT successful!!!");
        driver.getCurrentUrl().equals(adventurePageCurrentUrl);

        AdventurePage adventurePage = new AdventurePage(driver);
        adventurePage.selectAdventure(AdventureName);
        Thread.sleep(3000);
        Assert.assertTrue(adventurePage.selectSearchedResult(),"Selecting the searched result NOT successfull!!!");
        
        AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
        //int countOfGuests = Integer.parseInt(count);
        adventureDetailsPage.bookAdventure(GuestName, Date, count);

        adventureDetailsPage.isBookingSuccessful();
        Thread.sleep(2000);

        adventureDetailsPage.navigateToHistoryPage();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html"));
        
        Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html"));

        //Assert.assertTrue(adventureDetailsPage.isBookingSuccessful(),"Adventure booking NOT successful!!!");
        
        Thread.sleep(5000);
        HistoryPage historyPage = new HistoryPage(driver);
        String transactionId = historyPage.getTransactionId(AdventureName, GuestName, Date, count);
        Thread.sleep(5000);
        historyPage.CancelTransactionId(transactionId);
        




        logStatus("Booking and Cancellation flow", "Verify that adventure booking and cancellation works fine", "Success");
    }

    /*@AfterTest(alwaysRun = true)
    public static void quitDriver(){
        logStatus("Driver", "Quitting Driver", "Start");
        driver.close();
        logStatus("Driver", "Quitting Driver", "Success");
    }*/




}
