package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import ru.yandex.qatools.allure.annotations.Title;

@Title("Урок 6.1 Групперовка элементов")
public class T006_1_GroupPageObjectTest {

    private AndroidDriver driver;
    private AppiumDriverSteps steps;

    @Before
    public void setUp() throws Exception {
        driver = androidCustomRule.getDriver();
        steps = new AppiumDriverSteps(driver);
    }

    @Rule
    public AndroidCustomRule androidCustomRule = new AndroidCustomRule();

    @Title("Использование групп PageObject проверка калдунщика погоды")
    @Test
    public void groupPageObjectUsage() throws Exception {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("погода");
        steps.checkSuggestSizeOver(1);
        steps.checkingMeteoWizard();
        }
}
