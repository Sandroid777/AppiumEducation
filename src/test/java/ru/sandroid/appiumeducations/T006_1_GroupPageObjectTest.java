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

@Title("Урок 6.1 Групперовка элементов")
public class T006_1_GroupPageObjectTest {

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

        @Title("Использование групп PageObject проверка калдунщика погоды")
        @Test
        public void groupPageObjectUsage() throws Exception {

            steps.closeTutorial();
            steps.clickToOmnibox();
            steps.sendKeys("погода");
            steps.checkSuggestSizeOver(1);
            steps.checkMeteoWizard();



        }
}
