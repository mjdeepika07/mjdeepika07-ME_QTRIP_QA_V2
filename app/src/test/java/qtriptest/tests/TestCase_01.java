package qtriptest.tests;
//package qtriptest;

//import qtriptest.tests.ExternalDataProvider;
import qtriptest.tests.ExcelDataProvider;
import qtriptest.DriverSingleton;

import qtriptest.pages.RegisterPage;
import qtriptest.DP;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCase_01 {

    static RemoteWebDriver driver;
    static String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";

    public static void logStatus(String type, String message, String status){

        System.out.println(String.format("%s |  %s  |  %s | %s",
				String.valueOf(java.time.LocalDateTime.now()), type, message, status));

    }

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException{
        //final DesiredCapabilities capabilities = new DesiredCapabilities();
        //capabilities.setBrowserName(BrowserType.CHROME);
        logStatus("Driver", "Initializing Driver", "Start");
        DriverSingleton ds = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = ds.getDriver();
        System.out.println("Hashcode of driver is : " + driver.hashCode());

        //try {
            //driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        driver.get(homeUrl);
            //driver.manage().window().maximize();
        logStatus("Driver", "Initializing Driver", "Success");
        /* } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("MalformedURLException occured while connecting to the remote address");
            e.printStackTrace();
        }*/
    }

    @Test(priority = 1,dataProvider = "userOnboardDataFlow", dataProviderClass= DP.class,groups={"Login Flow"})
    
        public void TestCase01(String userName, String password) throws InterruptedException{
       
        logStatus("User OnBoarding Flow", "User Registration - Login - Logout", "Start");
        
        RegisterPage registerpage = new RegisterPage(driver);
        
        Assert.assertTrue(registerpage.registerNewUser(userName, password, password, true),"User registration failed!!!!!");
        //System.out.println("lastGeneratedUsername : " + RegisterPage.lastGeneratedUsername);
        //System.out.println("lastGeneratedPassword : "+ RegisterPage.lastGeneratedPassword);


        LoginPage login = new LoginPage(driver);
        Assert.assertTrue(login.performLogin(RegisterPage.lastGeneratedUsername,RegisterPage.lastGeneratedPassword),"The perform Login is NOT successful!!");
        
        Thread.sleep(2000);

        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isUserLoggedIn(),"User is NOT logged in correctly");

        home.logOutUser();

        Thread.sleep(2000);
        

        logStatus("User OnBoarding Flow", "User Registration - Login - Logout", "Success");
    }
    
    

    @AfterSuite(alwaysRun = true)
    public static void quitDriver(){
        logStatus("Driver", "Quitting Driver", "Start");
        driver.close();
        logStatus("Driver", "Quitting Driver", "Success");
    }

}
