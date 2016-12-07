package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import static junit.framework.TestCase.assertTrue;

public class T002_BrowserStarter_PageObjectTestOLD {

        //URL appium сервера
        private static final String TESTOBJECT = "http://127.0.0.1:4723/wd/hub";

        private AndroidDriver driver;
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

            //Подключаю PageObject
            mainPageObject = new MainPageObject(driver);
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
                waitDriver.until(ExpectedConditions.elementToBeClickable(mainPageObject.sentry_bar));
            }
            catch (Exception s){
                //закрываю экран приветствия если появился
                waitDriver.until(ExpectedConditions.elementToBeClickable(mainPageObject.closeTutorialBtn)).click();
            }

            //тап в омнибокс
            mainPageObject.sentry_bar.click();

            //ввожу cat в строку поиска
            mainPageObject.omni_edittext.sendKeys("cat");

            //нахожу 3 строку в саджесте и тапаю
            mainPageObject.suggestN3.click();

            //Опредиление загрузки страницы по логам
            Date starttime = new Date();    //фиксирую время тапа
            List<LogEntry> logEntryList;    //массив для хранения логов
            boolean pageload = false;       //индикатор загрузки страницы

            //проверяю логи на наличие события "url opened"
            //Проверять буду 3 раза с интервалом 5 секунд
            for(int i = 0; i < 3; i++) {

                //Жду 5 секунд. Использую тут метод вспомогательного класса TestHelper
                TestHelper.controlWait(driver, 5);

                //беру у драйвера логи
                logEntryList = driver.manage().logs().get("logcat").filter(Level.ALL);

                //Создаю обьект LogParser передаю в него массив логов и время тапа
                LogParser lp = new LogParser(logEntryList, starttime);
                //Запускаю поиск. если находим "url opened" то выходим
                if(lp.findStringInLog("url opened")){
                    i=3;
                    pageload = true;
                }
                else
                    i++;
            }
            //Проверка
            assertTrue(pageload);

        }


    }


