package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.UUID;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    static RemoteWebDriver driver;
    static String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";
    static String registerUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";

    String email = "";
    //public String user_email = "";
    public static String lastGeneratedUsername = "";
    public static String lastGeneratedPassword = "";
    
    @FindBy(name = "email")
    private WebElement userNameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(name = "confirmpassword")
    private WebElement confirmPasswordInput;

    @FindBy(xpath = "//button[@class='btn btn-primary btn-login']")
    private WebElement registerNowElement;

    public RegisterPage(RemoteWebDriver driver){
        RegisterPage.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);

    }

	public boolean registerNewUser(String username, String password, String confirmpassword, Boolean makeUserDynamic) throws InterruptedException {

        //Wrapper method wrapper_navigate()
        //driver.get(registerUrl);
        SeleniumWrapper.wrapper_navigate(driver, registerUrl);

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe(registerUrl));
        

        if(makeUserDynamic){
            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid. toString();
        
            //input = testuser@email.com  output = testuser88849589594@email.com
            //System.out.println(username.split("@")[0]);
            //System.out.println(username.split("@")[1]);
            email = username.split("@")[0]+uuidAsString+"@"+username.split("@")[1];
            //System.out.println(email);
            
            //Wrapper method wrapper_sendKeys()
            //userNameInput.sendKeys(email);
            SeleniumWrapper.wrapper_sendKeys(userNameInput, email);
            
        
        }
        else{
            //Wrapper method wrapper_sendKeys()
            //userNameInput.sendKeys(username);
            SeleniumWrapper.wrapper_sendKeys(userNameInput, username);
        }

    Thread.sleep(3000);

    lastGeneratedUsername = email;
    lastGeneratedPassword = password;
    
    //Wrapper method wrapper_sendKeys()
    //passwordInput.sendKeys(password);
    SeleniumWrapper.wrapper_sendKeys(passwordInput, password);
    //Thread.sleep(3000);

    //Wrapper method wrapper_sendKeys()
    //confirmPasswordInput.sendKeys(confirmpassword);
    SeleniumWrapper.wrapper_sendKeys(confirmPasswordInput, confirmpassword);
    //Thread.sleep(3000);

    //Wrapper method wrapper_click()
    //registerNowElement.click();
    SeleniumWrapper.wrapper_click(registerNowElement, driver);
    //Thread.sleep(5000);

    return driver.getCurrentUrl().endsWith("/login");
    }
}