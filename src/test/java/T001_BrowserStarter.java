
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.junit.Assert.assertEquals;


public class T001_BrowserStarter {

    //URL appium сервера
    private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";

    private AppiumDriver driver;

    @Before
    public void setup() throws Exception {
        //настройка параметров
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "aPhone");
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "com.yandex.browser");
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, ".YandexBrowserActivity");

        //Инициирую драйвер (url appium server + настройки).
        driver = new AppiumDriver(new URL(TESTOBJECT), capabilities);
    }

    @After
    public void tearDown() throws Exception{
        //убиваю драйвер
        driver.quit();
    }
    @Test
    public void abroMainTest() throws Exception {

        //пауза чтобы успело отобразится экран приветствия
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //закрываю экран приветствия
        WebElement close_tutorial = driver.findElement(By.id("activity_tutorial_close_button"));
        close_tutorial.click();

        //тап в омнибокс
        WebElement arrow = driver.findElement(By.id("bro_sentry_bar_fake_text"));
        arrow.click();

        //ввожу cat в строку поиска
        WebElement arrowEdit = driver.findElementById("bro_sentry_bar_input_edittext");
        arrowEdit.sendKeys("cat");

        //нахожу 3 строку в саджесте
        WebElement suggestN3 = driver.findElement(By.xpath("//*[@class='android.widget.RelativeLayout' and @index = '2']"));
        suggestN3.click();

        //С ожиданием трудности!!!! не нашел пока как реализовать.

    }
}

