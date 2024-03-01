package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;

public class AdventurePage {

static RemoteWebDriver driver;

@FindBy(id = "duration-select")
WebElement durationSelect;
@FindBy(xpath = "//*[@id='duration-select']/following-sibling::div")
WebElement durationClearSelect;

@FindBy(id = "category-select")
WebElement categorySelect;
@FindBy(xpath = "//*[@id='category-select']/following-sibling::div")
WebElement categoryClearSelect;

@FindBy(id = "search-adventures")
WebElement adventuresSelect;
@FindBy(xpath = "//*[@id='search-adventures']/following-sibling::div")
WebElement adventuresClearSelect;

@FindBy(xpath = "//*[@class='col-6 col-lg-3 mb-4']")
List<WebElement> resultsCount;

@FindBy(xpath = "//*[@class='activity-card']")
WebElement searchResultActivityCard;


public AdventurePage(RemoteWebDriver driver){
    AdventurePage.driver = driver;
    AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(driver, 10);
    PageFactory.initElements(ajax, this);

}

public void setFilterValue(String durationFilterName){

    Select dropdown = new Select(durationSelect); 
    dropdown.selectByVisibleText(durationFilterName);

}

public void setCategoryValue(String categoryFilterName){

    Select dropdown = new Select(categorySelect); 
    dropdown.selectByVisibleText(categoryFilterName);

}

public int getResultCount(){

    System.out.println("resultsCount.getSize()  :  " + resultsCount.size());
    return resultsCount.size();

}

public void selectAdventure(String adventureName){

    //Wrapper method wrapper_sendKeys()
    //adventuresSelect.sendKeys(adventureName);
    SeleniumWrapper.wrapper_sendKeys(adventuresSelect, adventureName);

}

public void durationFilterClear(String durationFilterName){

    //Wrapper method wrapper_click()
    //durationClearSelect.click();
    SeleniumWrapper.wrapper_click(durationClearSelect, driver);
}

public void categoryFilterClear(String categoryFilterName){

    //Wrapper method wrapper_click()
    //categoryClearSelect.click();
    SeleniumWrapper.wrapper_click(categoryClearSelect, driver);
}

public boolean selectSearchedResult(){

    //Wrapper method wrapper_click()
    //searchResultActivityCard.click();
    SeleniumWrapper.wrapper_click(searchResultActivityCard, driver);
    
    return driver.getCurrentUrl().contains("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/detail/?adventure=");
}



}