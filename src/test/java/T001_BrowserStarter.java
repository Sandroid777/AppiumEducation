import io.appium.java_client.AndroidKeyCode;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertEquals;


public class T001_BrowserStarter {

    //URL appium сервера
    private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";

    private AppiumDriver driver;

    @Before
    public void setup() throws Exception {
        //настройка параметров
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "aPhone");
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "com.yandex.browser");
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, ".YandexBrowserActivity");

        //Иннициирую драйвер (url appium server + настройки).
        driver = new AppiumDriver(new URL(TESTOBJECT), capabilities);
    }

    @After
    public void tearDown() throws Exception{
        //какоментил так как тут driver уже null
        //driver.quit();
    }

    @Test
    public void testPlusOperation() throws Exception {

        WebElement arrow = driver.findElement(By.id("bro_sentry_bar_fake_text"));
        arrow.click();

        WebElement arrowEdit = driver.findElement(By.id("bro_sentry_bar_input_edittext"));
        arrowEdit.sendKeys("Hello world!!!");
    }
}

