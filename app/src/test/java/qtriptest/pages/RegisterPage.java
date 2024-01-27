package qtriptest.pages;

import java.sql.Timestamp;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

public class RegisterPage {

    RemoteWebDriver driver;
    public static String lastGeneratedUsername;
    public static String lastGeneratedPassword;
    String homeUrl = "https://qtripdynamic-qa-frontend.vercel.app/";
    String registerUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
    String loginUrl = "https://qtripdynamic-qa-frontend.vercel.app/pages/login";

    @FindBy(xpath="//input[@name='email']")
    WebElement userNameTextbox;
    @FindBy(xpath="//input[@name='password']")
    WebElement passwordTextbox;
    @FindBy(xpath="//input[@name='confirmpassword']")
    WebElement confirmPasswordTextbox;
    @FindBy(xpath="//button[text()='Register Now']")
    WebElement registerButton;

    public RegisterPage(RemoteWebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver,10);
        PageFactory.initElements(ajax, this);

    }

    public boolean registerNewUser(String userName, String password, String confirmPassword, Boolean makeUsernameDynamic) throws InterruptedException{
        
        Thread.sleep(2000);

        if(this.driver.getCurrentUrl() != registerUrl)
            this.driver.get(registerUrl);
        
        this.driver.manage().window().maximize();
        Thread.sleep(4000);
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String test_userName;

        if(makeUsernameDynamic)
            test_userName = userName + String.valueOf(timestamp.getTime());
            else
                test_userName = userName;

        userNameTextbox.sendKeys(test_userName);
        passwordTextbox.sendKeys(password);
        confirmPasswordTextbox.sendKeys(confirmPassword);
        registerButton.click();

        lastGeneratedUsername = test_userName;
        lastGeneratedPassword = password;

        //System.out.println("lastGeneratedUsername : "+ lastGeneratedUsername);
        //System.out.println("lastGeneratedPassword : "+ lastGeneratedPassword);

        WebDriverWait wait = new WebDriverWait(this.driver, 30);
        wait.until(ExpectedConditions.urlToBe(loginUrl));

        return this.driver.getCurrentUrl().equals(loginUrl);

    }

}
