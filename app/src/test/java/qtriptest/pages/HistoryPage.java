
package qtriptest.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HistoryPage {

    static RemoteWebDriver driver;

    @FindBy(xpath="//table/tbody/tr/th")
    List<WebElement> transactionIdsOnHistoryPage;


    //table/tbody/tr/td[text()='Samuel']/parent::tr/th[@scope='row']


    public HistoryPage(RemoteWebDriver driver){

        HistoryPage.driver = driver;
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajax, this);


    }

    public void CancelTransactionId(String transactionId){

        WebElement cancelButton = driver.findElement(By.xpath("//tbody/tr/td/div/button[@id='"+transactionId+"']"));
        System.out.println("transactionId : " + transactionId);
        cancelButton.click();

    }

    public String getTransactionId(String AdventureName, String GuestName, String Date, String count) throws InterruptedException{

        Thread.sleep(7000);

        WebElement transactionIdWebElement;

        if(AdventureName.equals("Fort Sionnnn")){

            AdventureName = AdventureName.substring(0, 9);
            System.out.println("************AdventureName : " + AdventureName);
            //transactionIdWebElement = driver.findElement(By.xpath("//table/tbody/tr/td[text()='"+AdventureName+"']/parent::tr/th[@scope='row']"));
        }
        /*else {
            Thread.sleep(4000);
            //transactionIdWebElement = driver.findElement(By.xpath("//table/tbody/tr/td[1][text()='"+GuestName+"']/following-sibling::td[1][text()='"+AdventureName+"']/parent::tr/th"));
        
        }*/
        transactionIdWebElement = driver.findElement(By.xpath("//table/tbody/tr/td[text()='"+AdventureName+"']/parent::tr/th[@scope='row']"));

        System.out.println("transactionId : " + transactionIdWebElement.getText());
        return transactionIdWebElement.getText();

    }

    public boolean checkAllBookingsDisplayed(String[] transactionIds){

        int lengthOfInputTransactionIds = transactionIds.length;
        int matchFound=0;
        System.out.println("From checkAllBookingsDisplayed.. input TransactionIds : " + transactionIds.length);
        for(String eachTransactionId : transactionIds){
            
            
            for(WebElement transactionIdOnHistoryPage : transactionIdsOnHistoryPage){
                if(!eachTransactionId.equals(transactionIdOnHistoryPage.getText())){
                    continue;   
                }
                else{
                    
                    matchFound++;
                    System.out.println("Number of Transactions found till now : "+matchFound);
                    
                    break;
                }

            }
        }
        System.out.println("Total Matches of Transactions Found : "+matchFound);
        if(matchFound == lengthOfInputTransactionIds)
            return true;
            else
                return false;

    }

    /*public String getTransactionIdUsingAdventureGuestName(String transactionId, String AdventureName, String GuestName, String Date, String count) throws InterruptedException{

        //WebElement transactionIdUsingAdventureGuestNameWebElement = driver.findElement(By.xpath("//table/tbody/tr/td[text()='"+GuestName+"']/parent::tr/th[text()='"+transactionId+"']/parent::tr/td(2)[text()='"+AdventureName+"']"));
        List<WebElement> transactionIdWebElement = driver.findElements(By.xpath("//table/tbody/tr/td[text()='"+AdventureName+"']/parent::tr/th[@scope='row']"));
        


        System.out.println("transactionIdUsingAdventureGuestNameWebElement : " + transactionIdUsingAdventureGuestNameWebElement.getText());
      
        return transactionIdUsingAdventureGuestNameWebElement.getText();
    }*/


}