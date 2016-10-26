package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.qatools.allure.annotations.Title;

import java.net.URL;

@Title("Урок 4 - Steps и Allure репорт")
public class T004_StepsTest {

    private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";
    private AppiumDriver driver;
    private AppiumDriverSteps steps;

    @Before
    public void setUp() throws Exception {
        //настройка параметров
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "aPhone");
        capabilities.setCapability("appPackage",  "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserActivity");
        capabilities.setCapability("unicodeKeyboard", "true");

        driver = new AndroidDriver(new URL(TESTOBJECT), capabilities);
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

    @Title("Ишем котиков в саджесте №1")
    @Test
    public void appiumTestSuggest1() throws Exception {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("cat");
        steps.suggestClick(1);
        steps.waitLoadPage(30);
    }

    @Title("Отладка: Ишем котиков и падаем")
    @Test
    public void appiumTestSuggest2() throws Exception {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("cat");
        steps.suggestClick(2);
        steps.waitLoadPage(30);
        steps.failedDebugStep();
    }

    @Title("Ишем котиков в саджесте №3")
    @Test
    public void appiumTestSuggest3() throws Exception {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("cat");
        steps.suggestClick(3);
        steps.waitLoadPage(30);
    }
}