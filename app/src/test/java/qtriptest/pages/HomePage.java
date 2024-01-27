package qtriptest.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage {

    RemoteWebDriver driver;
    String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";

    @FindBy(id="autocomplete")
    WebElement searchTextbox;
    @FindBy(xpath="//a[@href='./pages/register/']")
    WebElement registerButton;
    @FindBy(xpath="//div[@class='nav-link login register']")
    WebElement logoutButton;
    @FindBy(xpath="//div[@class='container']/ul/a")
    WebElement autoComplElement;
    @FindBy(xpath="//a[@href='./pages/login/']")
    WebElement loginHereLink;
    
    public HomePage(RemoteWebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);
        
    }

    public void clickRegister(){
        registerButton.click();
        
    }

    public boolean isUserLoggedIn(){

        if(!this.driver.getCurrentUrl().equals(homeUrl))
            this.driver.get(homeUrl);
        
        WebDriverWait wait = new WebDriverWait(this.driver, 40);
        wait.until(ExpectedConditions.urlToBe(homeUrl));

        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        
        return logoutButton.isDisplayed();    

    }

    public void logOutUser(){
        logoutButton.click();
        WebDriverWait wait = new WebDriverWait(this.driver, 40);
        wait.until(ExpectedConditions.visibilityOf(loginHereLink));
   
    }

    public void searchCity(String cityName){   
        searchTextbox.sendKeys(cityName);
    }

    public boolean assertAutoCompleteText(String cityName){
        return autoComplElement.getText().equals(cityName);
    }

    public void selectCity(String cityName){
        autoComplElement.click();
    }

}
