package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.qatools.allure.annotations.Title;

import java.net.URL;

public class T005_HamcrestTest {

    private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";
    private AppiumDriver driver;
    private AppiumDriverSteps steps;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "aPhone");
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "com.yandex.browser");
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, ".YandexBrowserActivity");

        driver = new AppiumDriver(new URL(TESTOBJECT), capabilities);
        steps = new AppiumDriverSteps(driver);
    }

    @Rule
    public TestRule watchman = new TestWatcher() {

        @Override
        protected void finished(Description description) {
            driver.quit();
        }

        @Override
        protected void failed(Throwable e, Description description) {
            steps.makeScreenshot();
            driver.quit();
        }
    };

    @Title("Проверка саджеста на > 1")
    @Test
    public void chackSuggestSize() throws Exception {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("cat.com");
        steps.checkSuggestSizeOver(1);
        steps.suggestClick(1);

        steps.clickOmniboxOnWebPage();
        steps.clickOmniboxButton();
        steps.sendKeys("cat.com");
        steps.checkSuggestSizeOver(1);
        steps.checkHistorySuggestTextColor();
        //steps.clickHistorySuggest();

        //steps.findBlueTextInSuggest(4);
    }


}