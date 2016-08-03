
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import java.net.URL;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

        //создаю объект WebDriverWait с его помощью сделаю ожидание событий.
        WebDriverWait waitDriver = new WebDriverWait(driver, 10);

        try {
            //Если не нахожу омнибокс значит появилось окно приветствия.
            waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("bro_sentry_bar_fake")));
        }
        catch (Exception s){
            //закрываю экран приветствия если появился
            waitDriver.until(ExpectedConditions.elementToBeClickable(By.id("activity_tutorial_close_button"))).click();
        }

        //тап в омнибокс
        WebElement arrow = driver.findElement(By.id("bro_sentry_bar_fake_text"));
        arrow.click();

        //ввожу cat в строку поиска
        WebElement arrowEdit = driver.findElementById("bro_sentry_bar_input_edittext");
        arrowEdit.sendKeys("cat");

        //нахожу 3 строку в саджесте
        WebElement suggestN3 = driver.findElement(By.xpath("//*[@class='android.widget.RelativeLayout' and @index = '2']"));
        suggestN3.click();

        //Статус загрузки страницы буду проверять по кнопке "обновить страницу" в омнибоксе
        //жду Stop. загрузка началась
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageButton' and @content-desc='Stop']")));
        //жду Reload загрузка закончена
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageButton' and @content-desc='Reload']")));


    }
}

