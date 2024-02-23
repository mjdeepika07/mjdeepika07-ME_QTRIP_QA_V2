package qtriptest.pages;


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

        guestNameElement.clear();
        guestNameElement.sendKeys(guestName);
        reservationDateElement.clear();
        reservationDateElement.sendKeys(date);
        guestCountElement.clear();
        guestCountElement.sendKeys(countOfGuests);

        reserveButtonElement.click();


    }

    public boolean isBookingSuccessful(){

        return reservedBannerElement.getText().contains("Greetings! Reservation for this adventure is successful.");

    }

    public void navigateToHistoryPage(){

        reservationsLinkElement.click();
        
    }

    
    //tbody/tr/th[text()='4d3199d23b76cdae'

    //tbody/tr/td/div/button[@id='4d3199d23b76cdae']
}