package qtriptest.pages;

import static org.testng.Assert.assertTrue;
import qtriptest.SeleniumWrapper;
import static org.testng.Assert.assertFalse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.Assertion;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage {

    RemoteWebDriver driver;
    public String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";

    // @FindBy(id="autocomplete")
    @FindBy(xpath = "//input[@id='autocomplete']")
    WebElement searchTextbox;
    @FindBy(xpath = "//a[@href='./pages/register/']")
    WebElement registerButton;
    @FindBy(xpath = "//div[@class='nav-link login register']")
    WebElement logoutButton;
    @FindBy(xpath = "//div[@class='container']/ul")
    WebElement autoComplElement;
    @FindBy(xpath = "//a[@href='./pages/login/']")
    WebElement loginHereLink;

    public HomePage(RemoteWebDriver driver) {
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 30);
        PageFactory.initElements(ajax, this);

    }

    public void clickRegister() {
        registerButton.click();

    }

    public boolean navigateToHomePage(RemoteWebDriver driver) {
        // try{
        // Thread.sleep(7000);
        // }
        // catch(Exception e){
        // e.getStackTrace();
        // }
        // if(!(driver.getCurrentUrl().equals(homeUrl))){
        // driver.get(homeUrl);
        // }

        // wrapper method wrapper_navigate()
        return SeleniumWrapper.wrapper_navigate(driver, homeUrl);
    }

    public boolean isUserLoggedIn() {

        if (!this.driver.getCurrentUrl().equals(homeUrl))
            this.driver.get(homeUrl);

        WebDriverWait wait = new WebDriverWait(this.driver, 50);
        wait.until(ExpectedConditions.urlToBe(homeUrl));

        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));

        return logoutButton.isDisplayed();

    }

    public boolean logOutUser() {
        logoutButton.click();
        WebDriverWait wait = new WebDriverWait(this.driver, 40);
        wait.until(ExpectedConditions.visibilityOf(loginHereLink));
        return loginHereLink.isDisplayed();

    }

    public void searchCity(String cityName) {
        try {

            Thread.sleep(3000);
            searchTextbox.clear();
            searchTextbox.sendKeys(cityName);
            
            //Wrapper Method wrapper_sendKeys()
            //SeleniumWrapper.wrapper_sendKeys(searchTextbox, cityName);
            
            WebDriverWait wait = new WebDriverWait(this.driver, 10);
            wait.until(ExpectedConditions.elementToBeClickable(autoComplElement));

            System.out.println("autoComplElement : " + autoComplElement.getText());
            // System.out.println("autoComplElement : " +
            // autoComplElement.getText().equalsIgnoreCase("No City found"));

        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    public boolean assertAutoCompleteText(String cityName) throws InterruptedException {
        // *********below thread is added for Testcase04 testing purpose***********************
        Thread.sleep(5000);
        
        return autoComplElement.getText().equals(cityName);
    }

    public void selectCity(String cityName) {
        autoComplElement.click();
        driver.getCurrentUrl().contains("city");
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

}
