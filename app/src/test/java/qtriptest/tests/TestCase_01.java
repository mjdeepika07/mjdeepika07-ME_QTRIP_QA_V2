package qtriptest.tests;
// package qtriptest;

// import qtriptest.tests.ExternalDataProvider;
import qtriptest.tests.ExcelDataProvider;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.RegisterPage;
import qtriptest.DP;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import java.io.File;

public class TestCase_01 {

    static RemoteWebDriver driver;
    static String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";

    static ExtentReports report;
    static ExtentTest test;


    public static void logStatus(String type, String message, String status) {

        System.out.println(String.format("%s |  %s  |  %s | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));

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


        logStatus("Report", "Creating a Report", "Start");
        ReportSingleton reportSingleton = ReportSingleton.getInstanceOfSingletonReportClass();
        report = reportSingleton.getReport();

        File configExtentReportsPath =
                new File(System.getProperty("user.dir") + "/src/configExtentReports.xml");
        /// home/crio-user/workspace/mjdeepika07-ME_QTRIP_QA_V2/app/src/configExtentReports.xml
        // . /home/crio-user/workspace/mjdeepika07-ME_QTRIP_QA_V2/app/src/configExtentReports.xml
        report.loadConfig(configExtentReportsPath);



    }

    @Test(priority = 1, dataProvider = "userOnboardDataFlow", dataProviderClass = DP.class,
            groups = {"Login Flow"})

    public void TestCase01(String userName, String password)
            throws InterruptedException, IOException {

        try {
            test = report.startTest("Verify User Registration - Login - Logout");

            logStatus("User OnBoarding Flow", "User Registration - Login - Logout", "Start");


            RegisterPage registerpage = new RegisterPage(driver);

            // Assert.assertTrue(registerpage.registerNewUser(userName, password, password, true),
            // "User registration failed!!!!!");

            if (registerpage.registerNewUser(userName, password, password, true))
                test.log(LogStatus.PASS, "User Registration successful!!");
            else
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver))
                                + "User Registration UNSUCCESSFULL!!");

            // Extent Report - log status
            // test.log(LogStatus.PASS, "User Registration Successfull");

            LoginPage login = new LoginPage(driver);
            /*
             * Assert.assertTrue( login.performLogin(RegisterPage.lastGeneratedUsername,
             * RegisterPage.lastGeneratedPassword), "The perform Login is NOT successful!!");
             */

            // Extent Report - log status
            // test.log(LogStatus.PASS, "User Login functionality Successfull");

            if (login.performLogin(RegisterPage.lastGeneratedUsername,
                    RegisterPage.lastGeneratedPassword))
                test.log(LogStatus.PASS, "Perform Login Successfull!!");
            else
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver))
                                + "Perform Login UNSUCCESSFULL!!");


            Thread.sleep(2000);

            HomePage home = new HomePage(driver);

            // Assert.assertTrue(home.isUserLoggedIn(), "User is NOT logged in correctly");
            // Extent Report
            // test.log(LogStatus.PASS, "User is logged in successfully");

            if (home.isUserLoggedIn())
                test.log(LogStatus.PASS, "User LogIn Successfull!!");
            else
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver))
                                + "User LogIn UNSUCCESSFULL!!!");



            // Thread.sleep(3000);
            //home.logOutUser();
            // Extent Report
            // test.log(LogStatus.PASS, "User is logged out successfully");
            if (home.logOutUser())
                test.log(LogStatus.PASS, "User LogOut Successfull!!");
            else
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver))
                                + "User LogOut UNSUCCESSFULL!!!");


            Thread.sleep(2000);

            logStatus("User OnBoarding Flow", "User Registration - Login - Logout", "Success");
            test.log(LogStatus.INFO, test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver)));

        } catch (Exception e) {

            try {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver))
                                + "TestCase01 validation FAILED!!!-1");
                e.getMessage();
                e.getStackTrace();
                
            }

            catch (IOException ioe) {
                test.log(LogStatus.FAIL,
                        test.addScreenCapture(SeleniumWrapper.wrapper_captureScreenshot(driver))
                                + "TestCase01 validation FAILED!!!");
                
            }
        }
    }



    @AfterSuite(alwaysRun = true)
    public static void quitDriver() {
        logStatus("Driver", "Quitting Driver", "Start");
        driver.close();
        logStatus("Driver", "Quitting Driver", "Success");

        report.endTest(test);
        report.flush();
    }

}
