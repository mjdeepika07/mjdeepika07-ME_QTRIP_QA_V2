package qtriptest.tests;

import qtriptest.pages.RegisterPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public class testCases {
    
    static RemoteWebDriver driver;
    static String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";
    //String lastUsername;

    public static void logStatus(String type, String message, String status){

        System.out.println(String.format("%s |  %s  |  %s | %s",
				String.valueOf(java.time.LocalDateTime.now()), type, message, status));

    }

    @BeforeSuite(enabled = false, alwaysRun = true)
    public static void createDriver(){
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        logStatus("Driver", "Initializing Driver", "Start");
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
            //driver.get(homeUrl);
            
            logStatus("Driver", "Initializing Driver", "Success");
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("MalformedURLException occured while connecting to the remote address");
            e.printStackTrace();
        }
    }

    @Test(enabled = false, dataProvider = "userOnboardDataFlow", dataProviderClass = ExcelDataProvider.class)
    public static void userOnBoardingFlow_registerUser(String UserName, String Password) throws InterruptedException{

        logStatus("User OnBoarding Flow", "User Registration", "Start");

        RegisterPage registerpage = new RegisterPage(driver);
        //Assert.assertTrue(registerpage.registerNewUser("Deepika@","Deepika@123","Deepika@123",true),"User registration failed");
        Assert.assertTrue(registerpage.registerNewUser(UserName,Password,Password,true),"User registration failed");
        logStatus("User OnBoarding Flow", "User Registration", "Success");
    }

    @Test(enabled = false, priority = 2)
    public void userOnBoardingFlow_loginUser() throws InterruptedException{

        logStatus("User OnBoarding Flow", "User Login", "Start");

        
        RegisterPage register = new RegisterPage(driver);
        String lastUsername = register.lastGeneratedUsername; 
        System.out.println("lastGeneratedUsername : "+lastUsername);
        LoginPage login = new LoginPage(driver);
        Assert.assertTrue(login.performLogin("Deepika@1706076108616","Deepika@123"),"The perform Login is NOT successful!!");

        Thread.sleep(2000);

        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isUserLoggedIn(),"User is NOT logged in correctly");

        logStatus("User OnBoarding Flow", "User Login", "Success");

    }

    @Test(enabled = false, priority = 3)
    public void userOnBoardingFlow_logoutUser() throws InterruptedException{
        
        logStatus("User OnBoarding Flow", "User Logout", "Start");
        
        RegisterPage registerpage = new RegisterPage(driver);
        Assert.assertTrue(registerpage.registerNewUser("Deepika@","Deepika@123","Deepika@123",true),"User registration failed");

        RegisterPage register = new RegisterPage(driver);
        String lastUsername = register.lastGeneratedUsername; 
        System.out.println("lastGeneratedUsername : "+lastUsername);

        LoginPage login = new LoginPage(driver);
        Assert.assertTrue(login.performLogin("Deepika@1706076108616","Deepika@123"),"The perform Login is NOT successful!!");

        Thread.sleep(2000);

        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isUserLoggedIn(),"User is NOT logged in correctly");

        home.logOutUser();

        Thread.sleep(2000);
        

        logStatus("User OnBoarding Flow", "User Logout", "Success");


    }



    @AfterSuite(enabled = false)
    public static void quitDriver(){
        logStatus("Driver", "Quitting Driver", "Start");
        driver.close();
        logStatus("Driver", "Quitting Driver", "Success");
    }

    
}
