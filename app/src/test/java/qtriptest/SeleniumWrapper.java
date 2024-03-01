package qtriptest;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumWrapper {
    
    
    public static boolean wrapper_sendKeys(WebElement inputBox, String keysToSend){

        try{
            inputBox.clear();
            inputBox.sendKeys(keysToSend);
            return true;

        }
        catch(Exception e){

            return false;
        }
        

    }

    public static boolean wrapper_click(WebElement element, RemoteWebDriver driver){
        if(element.isDisplayed()){

            try{
                JavascriptExecutor js = ((JavascriptExecutor)driver);
                js.executeScript("arguments[0].scrollIntoView(true)",element);
                element.click();
                Thread.sleep(2000);
                return true;
            }
            catch(Exception e){
                return false;
            }
        }
        return false;

    }

    public static boolean wrapper_navigate(RemoteWebDriver driver, String url){

        try{
            if(driver.getCurrentUrl().equals(url)){
                return true; 
            }
            else{
                driver.get(url);
                return driver.getCurrentUrl().equals(url);
            }
        
        }
        catch(Exception e){

            return false;
        }
    }

    public static String wrapper_captureScreenshot(RemoteWebDriver driver) throws IOException{

        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        File destFile = new File(System.getProperty("user.dir")+"/Screenshots/screenshot"+String.valueOf(ts.getTime())+".png");
        
        FileUtils.copyFile(srcFile, destFile);

        String destFilePath = destFile.getAbsolutePath();

        return destFilePath;
    } 



}
