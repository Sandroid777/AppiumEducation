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

import java.awt.Color;
import java.net.URL;

@Title("Урок 5 Использование Hamcrest")
public class T005_HamcrestTest {

    private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";
    private AppiumDriver driver;
    private AppiumDriverSteps steps;
    private MainPageObject mainPageObject;

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
        mainPageObject = new MainPageObject(driver);
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
    }

    @Title("Проверка цвета в историческом саджесте")
    @Test
    public void chackHisorySuggestTextColor() throws Exception {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("cat.com");
        steps.checkSuggestSizeOver(1);
        steps.suggestClick(1);

        steps.clickOmniboxOnWebPage();
        steps.clickOmniboxButton();
        steps.sendKeys("cat.com");
        steps.checkSuggestSizeOver(1);

        steps.checkColorInHistorySuggest(mainPageObject.historySuggest, new Color(6, 112, 193), new Color(147, 147, 147));
    }


}