import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.qatools.allure.annotations.Title;

import java.net.URL;

@Title("���� � 4 - Steps � Allure ������")
public class T004_StepsTest {


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

    @Title("���� ������� � ������ ��������")
    @Test
    public void appiumTestSuggest1() throws Exception {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("cat");
        steps.suggestClick(1);
        steps.waitLoadPage(30);
    }

    @Title("�������: ���� ������� � � ����� ������")
    @Test
    public void appiumTestSuggest2() throws Exception {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("cat");
        steps.suggestClick(2);
        steps.waitLoadPage(30);
        steps.failedDebugStep();
    }

    @Title("���� ������� � ������� ��������")
    @Test
    public void appiumTestSuggest3() throws Exception {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("cat");
        steps.suggestClick(3);
        steps.waitLoadPage(30);
    }
}
