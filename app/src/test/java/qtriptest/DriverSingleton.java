package qtriptest;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {

    RemoteWebDriver driver;
    private static DriverSingleton instanceOfSingleton;
    private DriverSingleton() throws MalformedURLException{

        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(BrowserType.CHROME);
        driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);

        System.out.println("Create Driver");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    public static DriverSingleton getInstanceOfSingletonBrowserClass() throws MalformedURLException{
        if(instanceOfSingleton == null){

            instanceOfSingleton = new DriverSingleton();
        }

        return instanceOfSingleton;

    }

    //public method 
    public RemoteWebDriver getDriver(){
        return driver;
    }



}