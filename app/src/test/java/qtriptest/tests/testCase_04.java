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


public class testCase_04 {

    static RemoteWebDriver driver;
    static String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";

    public static void logStatus(String type, String message, String status) {

        String currentDateTime = String.valueOf(java.time.LocalDateTime.now());
        System.out.println(String.format(" %s  |  %s  |  %s  |  %s  ", currentDateTime, type,
                message, status));

    }

    @BeforeSuite(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {

        logStatus("Driver Test", "Driver creation is starting", "Start");


        DriverSingleton singleton = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = singleton.getDriver();

        System.out.println("The HashCode of the driver is : " + driver.hashCode());

        driver.get(homeUrl);


        logStatus("Driver Test", "Driver creation is starting", "Success");


    }

    @Test(priority = 4,dataProvider = "userOnboardDataFlow", dataProviderClass = DP.class,groups={"Reliability Flow"})
    public void TestCase04(String NewUserName, String Password, String dataset1, String dataset2,
            String dataset3) throws InterruptedException {

        logStatus("Booking history flow", "Verify that booking history can be viewed", "Start");
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage(driver);

        RegisterPage registerpage = new RegisterPage(driver);
        Assert.assertTrue(registerpage.registerNewUser(NewUserName, Password, Password, true),
                "User registration failed!!!!!");

        LoginPage login = new LoginPage(driver);
        Assert.assertTrue(
                login.performLogin(RegisterPage.lastGeneratedUsername,
                        RegisterPage.lastGeneratedPassword),
                "The perform Login is NOT successful!!");

        Thread.sleep(2000);



        String[] datasets = {dataset1, dataset2, dataset3};
        String[] transactionIds = {"","",""};
        System.out.println("datasets.length. :  " + datasets.length);
        for(int i = 0; i < datasets.length; i++){

                homePage.navigateToHomePage(driver);
        //for (String eachDataset : datasets) {
            // String[] newDataset1 = dataset1.split(";");
            // System.out.println("newDataset["+i+"] : ");
            // System.out.println("SearchCity: " + newDataset1[0]);
            // System.out.println("AdventureName: " + newDataset1[1]);
            // System.out.println("GuestName: " + newDataset1[2]);
            // System.out.println("Date: " + newDataset1[3]);
            // System.out.println("count: " + newDataset1[4]);

            // String SearchCity = newDataset1[0];
            // String AdventureName = newDataset1[0];
            // String GuestName = newDataset1[0];
            // String Date = newDataset1[0];
            // String count = newDataset1[0];

            //String[] valuesOfDataset = eachDataset.split(";");

            String[] valuesOfDataset = datasets[i].split(";");
            System.out.println("newDataset["+i+"] : ");
            System.out.println("SearchCity: " + valuesOfDataset[0]);
            System.out.println("AdventureName: " + valuesOfDataset[1]);
            System.out.println("GuestName: " + valuesOfDataset[2]);
            System.out.println("Date: " + valuesOfDataset[3]);
            System.out.println("count: " + valuesOfDataset[4]);

            String SearchCity = valuesOfDataset[0];
            String AdventureName = valuesOfDataset[1];
            String GuestName = valuesOfDataset[2];
            String Date = valuesOfDataset[3];
            String count = valuesOfDataset[4];



            homePage.searchCity(SearchCity);

            // WebDriverWait wait = new WebDriverWait(driver, 10);
            // wait.until(ExpectedConditions.)
            //Thread.sleep(7000);

            if (homePage.assertAutoCompleteText(SearchCity)) {
                homePage.selectCity(SearchCity);
            }

            String adventurePageCurrentUrl =
                    "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city="
                            + SearchCity;
            // Assert.assertTrue(driver.getCurrentUrl().equals(adventurePageCurrentUrl),"Navigation
            // to adventure page NOT successful!!!");
            driver.getCurrentUrl().equals(adventurePageCurrentUrl);

            AdventurePage adventurePage = new AdventurePage(driver);
            adventurePage.selectAdventure(AdventureName);
            Thread.sleep(3000);
            Assert.assertTrue(adventurePage.selectSearchedResult(),
                    "Selecting the searched result NOT successfull!!!");

            AdventureDetailsPage adventureDetailsPage = new AdventureDetailsPage(driver);
            // int countOfGuests = Integer.parseInt(count);
            adventureDetailsPage.bookAdventure(GuestName, Date, count);

            adventureDetailsPage.isBookingSuccessful();
            Thread.sleep(2000);

            adventureDetailsPage.navigateToHistoryPage();

            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.urlToBe(
                    "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html"));

            Assert.assertTrue(driver.getCurrentUrl().equals(
                    "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html"));

            // Assert.assertTrue(adventureDetailsPage.isBookingSuccessful(),"Adventure booking NOT
            // successful!!!");

            Thread.sleep(5000);
            HistoryPage historyPage = new HistoryPage(driver);
            transactionIds[i] = historyPage.getTransactionId(AdventureName, GuestName, Date, count);
            System.out.println("transactionIds["+i+"] : " + transactionIds[i]);
            Thread.sleep(5000);


        }

        HistoryPage historyPage = new HistoryPage(driver);
        Assert.assertTrue(historyPage.checkAllBookingsDisplayed(transactionIds),"TransactionIds  in the History page does NOT match with the bookings done!!!");
        
        
        logStatus("Booking history flow", "Verify that booking history can be viewed", "Success");
    }

    /*@AfterTest(alwaysRun = true)
    public static void quitDriver() {
        logStatus("Driver", "Quitting Driver", "Start");
        driver.close();
        logStatus("Driver", "Quitting Driver", "Success");
    }*/

}
