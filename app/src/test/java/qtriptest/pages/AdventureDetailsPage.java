package qtriptest.pages;


import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventureDetailsPage {

    static RemoteWebDriver driver;


    @FindBy(xpath="//input[@name='name']")
    WebElement guestNameElement;

    @FindBy(xpath="//input[@name='date']")
    WebElement reservationDateElement;

    @FindBy(xpath="//input[@name='person']")
    WebElement guestCountElement;

    @FindBy(className="reserve-button")
    WebElement reserveButtonElement;

    @FindBy(id="reserved-banner")
    WebElement reservedBannerElement;

    @FindBy(linkText = "Reservations")
    WebElement reservationsLinkElement;
    
    

    public AdventureDetailsPage(RemoteWebDriver driver){

        this.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);

    }
    public void bookAdventure(String guestName, String date,String countOfGuests){

        //Wrapper method wrapper_sendKeys()
        //guestNameElement.clear();
        //guestNameElement.sendKeys(guestName);
        SeleniumWrapper.wrapper_sendKeys(guestNameElement, guestName);

         //Wrapper method wrapper_sendKeys()
        //reservationDateElement.clear();
        //reservationDateElement.sendKeys(date);
        SeleniumWrapper.wrapper_sendKeys(reservationDateElement, date);

        //Wrapper method wrapper_sendKeys()
        //guestCountElement.clear();
        //guestCountElement.sendKeys(countOfGuests);
        SeleniumWrapper.wrapper_sendKeys(guestCountElement, countOfGuests);


        //wrapper method wrapper_click()
        //reserveButtonElement.click();
        SeleniumWrapper.wrapper_click(reserveButtonElement, driver);


    }

    public boolean isBookingSuccessful(){

        return reservedBannerElement.getText().contains("Greetings! Reservation for this adventure is successful.");

    }

    public void navigateToHistoryPage(){

        //Wrapper method wrapper_click()
        //reservationsLinkElement.click();
        SeleniumWrapper.wrapper_click(reservationsLinkElement, driver);
        
    }

    
}