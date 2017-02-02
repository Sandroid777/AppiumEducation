package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

public class T003_Junit_RulesTestOLD {

    private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";
    private static final String screenshotPath = "C:\\AppiumProject\\ErrorScreenshot\\";
    private AppiumDriver driver;
    private MainPageObject mainPageObject;

    @Before
    public void setup() throws Exception {
        //настройка параметров
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "aPhone");
        capabilities.setCapability("appPackage",  "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserActivity");
        capabilities.setCapability("unicodeKeyboard", "true");

        driver = new AndroidDriver(new URL(TESTOBJECT), capabilities);
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
    public void appiumTest() throws Exception {

        WebDriverWait waitDriver = new WebDriverWait(driver, 10);

        try {
            waitDriver.until(ExpectedConditions.elementToBeClickable(mainPageObject.sentryBar));
        }
        catch (Exception s){
            waitDriver.until(ExpectedConditions.elementToBeClickable(mainPageObject.closeTutorialBtn)).click();
        }

        mainPageObject.sentryBar.click();

        mainPageObject.omniTextEdit.sendKeys("cat");

        mainPageObject.suggestN3.click();

        Date startTime = new Date();    //фиксирую время тапа
        Date finalFindTime = new Date(startTime.getTime() + 30000); //время ожидания 30сек от тапа
        List<LogEntry> logEntryList2;    //массив для хранения логов

        //проверяю логи на наличие события "url opened"
        while(finalFindTime.getTime() > System.currentTimeMillis()){

            List<LogEntry> logEntryList = driver.manage().logs().get("logcat").filter(Level.ALL);
            LogParser lp = new LogParser(logEntryList, startTime);

            //Запускаю поиск. если находим "url opened" то выходим
            if(lp.findStringInLog("url opened")){break;}

            Thread.sleep(1000);
        };
    }
}
