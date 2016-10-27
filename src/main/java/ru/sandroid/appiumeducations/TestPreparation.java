package ru.sandroid.appiumeducations;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class TestPreparation {

    private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";
    private AppiumDriver driver;
    private AppiumDriverSteps steps;


    TestPreparation() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "aPhone");
        capabilities.setCapability("appPackage",  "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserActivity");
        capabilities.setCapability("unicodeKeyboard", "true");

        driver = new AndroidDriver(new URL(TESTOBJECT), capabilities);
        steps = new AppiumDriverSteps(driver);
    }

    public AppiumDriver getDriver(){
        return driver;
    }
    public AppiumDriverSteps getSteps(){
        return  steps;
    }
}
