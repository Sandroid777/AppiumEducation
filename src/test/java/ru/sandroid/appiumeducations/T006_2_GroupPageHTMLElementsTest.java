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

@Title("Урок 6.2 Групперовка HTML Elements")
public class T006_2_GroupPageHTMLElementsTest {

    private AppiumDriver driver;
    private AppiumDriverSteps steps;

    @Before
    public void setUp() throws Exception {
        //настройка параметров
        TestPreparation testPreparation= new TestPreparation();
        driver = testPreparation.getDriver();
        steps = testPreparation.getSteps();
    }

    @Rule
    public TestRule watchman = new CustomRule(driver, steps);

    @Title("Использование групп HTML Elements. Проверка калдунщика погоды")
    @Test
    public void groupPageObjectUsage() throws Exception {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("погода");
        steps.checkSuggestSizeOver(1);
        steps.checkMeteoWizardHTML();
    }
}
