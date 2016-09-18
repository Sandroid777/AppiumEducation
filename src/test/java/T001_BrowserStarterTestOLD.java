        import io.appium.java_client.AppiumDriver;
        import io.appium.java_client.remote.MobileCapabilityType;
        import java.net.URL;
        import java.security.Timestamp;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.concurrent.TimeUnit;
        import java.util.logging.Level;

        import org.junit.After;
        import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.logging.LogEntry;
        import org.openqa.selenium.remote.DesiredCapabilities;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import sun.security.jca.GetInstance;

        import static java.text.DateFormat.getInstance;
        import static junit.framework.TestCase.assertTrue;

public class T001_BrowserStarterTestOLD {

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

        //Опредиление загрузки страницы по логам

        Date starttime = new Date();    //фиксирую время тапа
        List<LogEntry> logEntryList;    //массив для хранения логов
        boolean pageload = false;       //индикатор загрузки страницы

        //проверяю логи на наличие события "url opened"
        //Проверять буду 3 раза с интервалом 5 секунд
        for(int i = 0; i < 3; i++) {

            //Жду 5 секунд
            TestHelper.ControlWait(driver, 5);

            //беру у драйвера логи
            logEntryList = (List<LogEntry>) driver.manage().logs().get("logcat").filter(Level.ALL);

            //Создаю обьект LogParser передаю в него массив логов и время тапа
            LogParser lp = new LogParser(logEntryList, starttime);
            //Запускаю поиск. если находим "url opened" то выходим
            if(lp.FindStringInLog("url opened")){
                i=3;
                pageload =true;
            }
            else
                i++;
        }
        //Проверка
        assertTrue(pageload);

        /*Старый вариант
        //Статус загрузки страницы буду проверять по кнопке "обновить страницу" в омнибоксе
        //жду Stop. загрузка началась
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageButton' and @content-desc='Stop']")));
        //жду Reload загрузка закончена
        waitDriver.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='android.widget.ImageButton' and @content-desc='Reload']")));
        */
    }
}


