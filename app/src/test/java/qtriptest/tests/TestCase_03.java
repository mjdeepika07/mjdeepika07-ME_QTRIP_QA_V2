package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestCase_03 {

        static RemoteWebDriver driver;
        static String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";
        static String historyUrl =
                        "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html";

        static ExtentReports report;
        static ExtentTest test;

        public static void logStatus(String type, String message, String status) {

                String currentDateTime = String.valueOf(java.time.LocalDateTime.now());
                System.out.println(String.format(" %s  |  %s  |  %s  |  %s  ", currentDateTime,
                                type, message, status));

        }

        @BeforeSuite(alwaysRun = true)
        public static void createDriver() throws MalformedURLException {

                logStatus("Driver Test", "Driver creation is starting", "Start");


                DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
                driver = singleton.getDriver();

                System.out.println("The HashCode of the driver is : " + driver.hashCode());

                // Wrapper method wrapper_navigate()
                // driver.get(homeUrl);
                SeleniumWrapper.wrapper_navigate(driver, homeUrl);


                logStatus("Driver Test", "Driver creation is starting", "Success");

                ReportSingleton reportSingleton =
                                ReportSingleton.getInstanceOfSingletonReportClass();
                report = reportSingleton.getReport();



        }

        @Test(priority = 3, dataProvider = "userOnboardDataFlow", dataProviderClass = DP.class,
                        groups = {"Booking and Cancellation Flow"})
        public void TestCase03(String NewUserName, String Password, String SearchCity,
                        String AdventureName, String GuestName, String Date, String count)
                        throws InterruptedException, IOException {

                // logStatus("Booking and Cancellation flow",
                // "Verify that adventure booking and cancellation works fine", "Start");
                try {
                        test = report.startTest(
                                        "Verify that adventure booking and cancellation works fine");

                        HomePage homePage = new HomePage(driver);
                        if (homePage.navigateToHomePage(driver))
                                test.log(LogStatus.PASS, "Navigation to Home Page is successful!!");
                        else
                                test.log(LogStatus.FAIL, test.addScreenCapture(
                                                SeleniumWrapper.wrapper_captureScreenshot(driver))
                                                + "Navigation to Home Page is UNSUCCESSFULL!!");



                        RegisterPage registerpage = new RegisterPage(driver);
                        // Assert.assertTrue(registerpage.registerNewUser(NewUserName, Password,
                        // Password,
                        // true),
                        // "User registration failed!!!!!");

                        if (registerpage.registerNewUser(NewUserName, Password, Password, true))
                                test.log(LogStatus.PASS, "User Registration successful!!");
                        else
                                test.log(LogStatus.FAIL, test.addScreenCapture(
                                                SeleniumWrapper.wrapper_captureScreenshot(driver))
                                                + "User Registration UNSUCCESSFULL!!");

                        //Assert.assertTrue(registerpage.registerNewUser(NewUserName, Password,
                        //                Password, true));



                        LoginPage login = new LoginPage(driver);
                        /*
                         * Assert.assertTrue( login.performLogin(RegisterPage.lastGeneratedUsername,
                         * RegisterPage.lastGeneratedPassword),
                         * "The perform Login is NOT successful!!");
                         */
                        if (login.performLogin(RegisterPage.lastGeneratedUsername,
                                        RegisterPage.lastGeneratedPassword))
                                test.log(LogStatus.PASS, "Perform Login Successfull!!");
                        else
                                test.log(LogStatus.FAIL,
                                                test.addScreenCapture(SeleniumWrapper
                                                                .wrapper_captureScreenshot(driver))
                                                                + "Perform Login UNSUCCESSFULL!!");
                        //Assert.assertTrue(login.performLogin(RegisterPage.lastGeneratedUsername,
                        //                RegisterPage.lastGeneratedPassword));


                        Thread.sleep(2000);

                        homePage.searchCity(SearchCity);


                        if (homePage.assertAutoCompleteText(SearchCity)) {
                                homePage.selectCity(SearchCity);
                        }

                        String SearchCityNew = SearchCity.toLowerCase();     //"bengaluru" should be in the Url and not "Bengaluru";
                        String adventurePageCurrentUrl =
                                        "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city="
                                                        + SearchCityNew ;
                        
                        WebDriverWait wait = new WebDriverWait(driver, 10);
                        wait.until(ExpectedConditions.urlToBe(adventurePageCurrentUrl));
                                                        // Assert.assertTrue(driver.getCurrentUrl().equals(adventurePageCurrentUrl),"Navigation
                        // to
                        // adventure page NOT successful!!!");

                        /*if (driver.getCurrentUrl().equals(adventurePageCurrentUrl))
                                test.log(LogStatus.PASS,
                                                "Navigation to Adventure Page Successfull!!");
                        else
                                test.log(LogStatus.FAIL, test.addScreenCapture(
                                                SeleniumWrapper.wrapper_captureScreenshot(driver))
                                                + "Navigation to Adventure Page NOT SUCCESSFULL!!!");*/
                        //Assert.assertTrue(driver.getCurrentUrl().equals(adventurePageCurrentUrl));


                        AdventurePage adventurePage = new AdventurePage(driver);
                        adventurePage.selectAdventure(AdventureName);
                        Thread.sleep(3000);

                        // Assert.assertTrue(adventurePage.selectSearchedResult(),
                        // "Selecting the searched result NOT successfull!!!");

                        if (adventurePage.selectSearchedResult())
                                test.log(LogStatus.PASS,
                                                "Selecting the searched result successfull!!");
                        else
                                test.log(LogStatus.FAIL, test.addScreenCapture(
                                                SeleniumWrapper.wrapper_captureScreenshot(driver))
                                                + "Selecting the searched result NOT SUCCESSFULL!!!");

                        //Assert.assertTrue(adventurePage.selectSearchedResult());


                        AdventureDetailsPage adventureDetailsPage =
                                        new AdventureDetailsPage(driver);
                        // int countOfGuests = Integer.parseInt(count);
                        adventureDetailsPage.bookAdventure(GuestName, Date, count);

                        if (adventureDetailsPage.isBookingSuccessful())
                                test.log(LogStatus.PASS, "Booking the Adventure is successfull!!");
                        else
                                test.log(LogStatus.FAIL, test.addScreenCapture(
                                                SeleniumWrapper.wrapper_captureScreenshot(driver))
                                                + "Booking the Adventure is NOT SUCCESSFULL!!!");
                        //Assert.assertTrue(adventureDetailsPage.isBookingSuccessful());



                        Thread.sleep(2000);


                        adventureDetailsPage.navigateToHistoryPage();


                        // historyUrl =
                        // "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html"
                        
                        
                        //WebDriverWait wait = new WebDriverWait(driver, 10);
                        wait.until(ExpectedConditions.urlToBe(historyUrl));


                        if (driver.getCurrentUrl().equals(historyUrl))
                                test.log(LogStatus.PASS,
                                                "Navigation to History Page is successfull!!");
                        else
                                test.log(LogStatus.FAIL, test.addScreenCapture(
                                                SeleniumWrapper.wrapper_captureScreenshot(driver))
                                                + "Navigation to History Page is NOT SUCCESSFULL!!!");
                        //Assert.assertTrue(driver.getCurrentUrl().equals(historyUrl));

                        Thread.sleep(5000);

                        HistoryPage historyPage = new HistoryPage(driver);
                        String transactionId = historyPage.getTransactionId(AdventureName,
                                        GuestName, Date, count);
                        Thread.sleep(5000);

                        if (historyPage.CancelTransactionId(transactionId))
                                test.log(LogStatus.PASS,
                                                "Cancellation of Transaction Id is successfull!!");
                        else
                                test.log(LogStatus.FAIL, test.addScreenCapture(
                                                SeleniumWrapper.wrapper_captureScreenshot(driver))
                                                + "Cancellation of Transaction Id is NOT SUCCESSFULL!!!");
                        //Assert.assertTrue(historyPage.CancelTransactionId(transactionId));


                        logStatus("Booking and Cancellation flow",
                                        "Verify that adventure booking and cancellation works fine",
                                        "Success");
                        test.log(LogStatus.INFO, test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver)));

                } catch (Exception e) {
                        test.log(LogStatus.FAIL, "Test Case 03 validation failed");
                        e.getStackTrace();
                }
        }


        /*@AfterTest(alwaysRun = true)
        public static void quitDriver() {

                logStatus("Driver", "Quitting Driver", "Start");
                driver.close();

                logStatus("Driver", "Quitting Driver", "Success");

                report.endTest(test);
                report.flush();

        }*/



}
