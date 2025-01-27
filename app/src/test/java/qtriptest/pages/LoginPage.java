package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage {

    RemoteWebDriver driver;
    //String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";
    //String registerUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
    String loginUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/login";

    @FindBy(xpath="//input[@name='email']")
    WebElement emailAddressTextbox;
    @FindBy(xpath="//input[@name='password']")
    WebElement passwordTextbox;
    @FindBy(xpath="//button[text()='Login to QTrip']")
    WebElement loginToQtripButton;

    public LoginPage(RemoteWebDriver driver){

        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);
    }


    public boolean performLogin(String emailAddress, String password){

        //Wrapper method wrapper_navigate()
        // if(!this.driver.getCurrentUrl().equals(loginUrl))
        //     this.driver.get(loginUrl);
        SeleniumWrapper.wrapper_navigate(driver, loginUrl);


        //Wrapper method wrapper_sendKeys()
        //emailAddressTextbox.sendKeys(emailAddress);
        //passwordTextbox.sendKeys(password);
        SeleniumWrapper.wrapper_sendKeys(emailAddressTextbox, emailAddress);
        SeleniumWrapper.wrapper_sendKeys(passwordTextbox, password);
        
        //Wrapper method wrapper_click()
        //loginToQtripButton.click();
        SeleniumWrapper.wrapper_click(loginToQtripButton, driver);

        WebDriverWait wait = new WebDriverWait(this.driver, 30);
        wait.until(ExpectedConditions.urlToBe(RegisterPage.homeUrl));
        
        return this.driver.getCurrentUrl().equals(RegisterPage.homeUrl); 

    } 

}
