import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.TestCase.assertTrue;

public class T003_Junit_Rules {

    private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";
    private static final String screenshotPath = "C:\\AppiumProject\\ErrorScreenshot\\";
    private AppiumDriver driver;
    private MainPageObject mainPageObject;


    @Before
    public void setup() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "aPhone");
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "com.yandex.browser");
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, ".YandexBrowserActivity");
        capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");

        driver = new AppiumDriver(new URL(TESTOBJECT), capabilities);
        mainPageObject = new MainPageObject(driver);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Rule
    public TestRule watchman = new TestWatcher() {

        @Override
        protected void finished(Description description) {
            driver.quit();
        }

        @Override
        protected void failed(Throwable e, Description description) {

            //Формирую имя файла yyMMddHHmmss + testname + .png
            Date errorDate = new Date();
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyMMddHHmmss");
            String screenShotName = dataFormat.format(errorDate) +"_"+ description.getMethodName() + ".png";

            try {
                File scFile = driver.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scFile, new File(screenshotPath + screenShotName));
            }
            catch (Exception ioE){
                ioE.printStackTrace();
            }

            //убиваю драйвер
            driver.quit();
        }
    };


    @Test
    public void closeHelloTapOmni() throws Exception {

        WebDriverWait waitDriver = new WebDriverWait(driver, 10);

        try {
            waitDriver.until(ExpectedConditions.elementToBeClickable(mainPageObject.sentry_bar));
        }
        catch (Exception s){
            waitDriver.until(ExpectedConditions.elementToBeClickable(mainPageObject.closeTutorialBtn)).click();
        }

        mainPageObject.sentry_bar.click();
    }

    @Test
    public void sendTextSuggestClick() throws Exception {

        closeHelloTapOmni();

        mainPageObject.omni_edittext.sendKeys("cat");
        mainPageObject.suggestN3.click();
    }
}
