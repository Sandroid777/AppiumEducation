package ru.sandroid.appiumeducations;

import static java.net.InetAddress.getLocalHost;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertTrue;

import static ru.sandroid.appiumeducations.MyMatchers.compareImage;
import static ru.sandroid.appiumeducations.MyMatchers.hasColor;
import static ru.sandroid.appiumeducations.MyMatchers.hasRefererQuery;
import static ru.sandroid.appiumeducations.MyMatchers.hasResponceURL;
import static ru.sandroid.appiumeducations.MyMatchers.hasURL;
import static ru.sandroid.appiumeducations.MyMatchers.logContainsString;
import static ru.sandroid.appiumeducations.MyMatchers.withWaitFor;
import static ru.sandroid.appiumeducations.TestHelper.elementFound;
import static ru.sandroid.appiumeducations.TestHelper.exists;


final class AppiumDriverSteps {

    private MainPageObject mainPageObject;
    private MainPageHTML mainPageHTML;
    private AndroidDriver driver;
    private final static int TEN_SECONDS = 10000;
    private final static int THIRTY_SECONDS = 30000;

    public AppiumDriverSteps(AndroidDriver driver) {
        this.driver = driver;
        mainPageObject = new MainPageObject(driver);
        mainPageHTML = new MainPageHTML(driver);
    }

    @Step("Копирую на устройство файл с настройками прокси. Прокси порт:{0}")
    public void copyProxyFile(int port) throws UnknownHostException {

            String fileString = "yandex --proxy-server=" + getLocalHost().getHostAddress() + ":" + port;
            driver.pushFile("/data/local/tmp/yandex-browser-command-line", fileString.getBytes());
    }

    @Step("Рестарт браузера")
    public void coldStartBrowser(){
        driver.resetApp();
    }

    @Step("Stop browser")
    public void forceStop() {
        driver.stopApp();
    }

    @Step("Закрытие экрана обучения")
    public void closeTutorial() {

        WebDriverWait waitDriver = new WebDriverWait(driver, 10);

        try {
            waitDriver.until(ExpectedConditions.elementToBeClickable(mainPageObject.sentryBar));
        }
        catch (Exception s){
            waitDriver.until(ExpectedConditions.elementToBeClickable(mainPageObject.closeTutorialBtn)).click();
        }
    }

    @Step("Тап в омнибокс")
    public void clickToOmnibox() {
        mainPageObject.sentryBar.click();
    }

    @Step("Тап в омнибокс на вэбвкладке")
    public void clickOmniboxOnWebPage() {
        mainPageObject.omniboxWebPage.click();
    }

    @Step("Тап по кнопке ввода омнибокса")
    public void clickOmniboxButton() {
        mainPageObject.omniboxButton.click();
    }

    @Step("Тап по историческому саджесту")
    public void clickHistorySuggest() {
        mainPageObject.historySuggest.click();
    }

    @Step("Send keys: {0}")
    public void sendKeys(String string) {
        mainPageObject.omniTextEdit.sendKeys(string);
    }

    //устаревший вариант для работы старых тестов
    @Step("Тап по саджесту №3")
    private void suggestN3Click() {
        mainPageObject.suggestN3.click();
    }

    @Step("Тап по саджесту № {0}")
    public void suggestClick(int suggestNumber){

        int n = mainPageObject.suggestList.size() - suggestNumber;

        if(n > 0){
            mainPageObject.suggestList.get(n).click();
            System.out.print("Click suggest #" + suggestNumber + "\n");
        }
        else {
            System.out.print("No item #" + suggestNumber + "in the list \n");
            assertTrue(false);
        }
    }

    //Старый вариант ожидания загрузки страницы.
    @Step("Ожидание загрузки строницы. Ждём {0} сек")
    public void waitLoadPage(int waitTime){

        Date startTime =new Date();
        //время ожидания 30сек от тапа
        Date finalFindTime = new Date(startTime.getTime() + waitTime * 1000);

        boolean loadPage = false;
        //проверяю логи на наличие события "url opened"
        while(finalFindTime.getTime() > System.currentTimeMillis()){

            List<LogEntry> logEntryList = driver.manage().logs().get("logcat").filter(Level.ALL);
            LogParser lp = new LogParser(logEntryList, startTime);

            //Запускаю поиск. если находим "url opened" то выходим
            if(lp.findStringInLog("url opened")){
                loadPage = true;
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        assertTrue(loadPage);
    }

    @Step("Ожидание записи в логе: {0}")
    public void waitRecordInLog(String logString, AndroidDriver driver){
        assertThat("Не дождались в логе записи: " + logString, driver, withWaitFor(logContainsString(logString), THIRTY_SECONDS));
    }

    @Step("Количество элементов в саджесте > {0}")
    public void checkSuggestSizeOver(int i) {
        assertThat(mainPageObject.suggestList, hasSize(greaterThan(i)));
    }

    @Step("Количество элементов в фидбэкменю > {0}")
    public void checkFeedbackMenuSizeOver(int i) {
        assertThat(mainPageObject.zenFeedbackMenu, hasSize(greaterThan(i)));
    }

    @Step("Проверка цвета в историческом саджесте")
    public void checkColorInHistorySuggest(WebElement webElement, Color findColor1, Color findColor2 ){

        BufferedImage bufferedImage = makeScreenshotAll();

        //Make subimage WebElement
        Point location = webElement.getLocation();
        int width = webElement.getSize().getWidth();
        int height = webElement.getSize().getHeight();
        BufferedImage elementImage = bufferedImage.getSubimage(location.getX(), location.getY(), width, height);

        assertThat(elementImage, both(hasColor(findColor1)).and(hasColor(findColor2)));
    }

    @Step("Поиск цвета (\"{1}\") в элементе \"{0}\"")
    public void findElementColor(AndroidElement androidElement, Color findColor){
        BufferedImage bufferedImage = makeScreenshotAll();

        //Make subimage WebElement
        Point location = androidElement.getLocation();
        int width = androidElement.getSize().getWidth();
        int height = androidElement.getSize().getHeight();
        BufferedImage elementImage = bufferedImage.getSubimage(location.getX(), location.getY(), width, height);

        assertThat(elementImage, hasColor(findColor));
    }

    @Step("Общаяя проверка калдунщика погоды")
    public void checkingMeteoWizard() {
        assertThat("Нет калдунщика погоды", exists(mainPageObject.groupMeteoWizard.wizard));
        assertThat("В кондунщике нет температуры", mainPageObject.groupMeteoWizard.wizard.getText().contains("°C"));
        assertThat("Паника Часы в колдунщике погоды ", !elementFound(mainPageObject.groupMeteoWizard, "bro_common_omnibox_image"));
    }

    @Step("Общаяя проверка калдунщика погоды")
    public void checkMeteoWizardHTML() {
        assertThat("Нет калдунщика погоды", exists(mainPageHTML.groupHTMLMeteoWizard.wizard));
        assertThat("В кондунщике нет температуры", mainPageObject.groupMeteoWizard.wizard.getText().contains("°C"));
        assertThat("Паника Часы в колдунщике погоды ", !elementFound(mainPageObject.groupMeteoWizard, "bro_common_omnibox_image"));
    }

    @Step("Тап по навигационнику в омнибоксе")
    public void clickNavigateInOmnibox() {
        mainPageObject.omniBlueLink.click();
    }

    @Step("Реферер содержит в поле from текст = android")
    public void containtsAndroidInReferer(List<Request> requestList, String navigationUrl){

        assertThat(requestList, withWaitFor(hasItem(both(hasURL(navigationUrl)).and(hasRefererQuery("android"))), TEN_SECONDS));
    }

    //Получаем текст из навигационника в омнибоксе
    @Step("Get text from omnibox blue link")
    public  String getTextOmniBlueLink() {
        return mainPageObject.omniBlueLink.getText();
    }

    @Step("Открытие ленты дзен")
    public void openZenStripe(){
        assertThat("Нет дзена", exists(mainPageObject.zenSrtipeGroup));
        int topY = getTopY(mainPageObject.zenSrtipeGroup) + 1;
        int centerX = getCenterX(mainPageObject.zenSrtipeGroup);
        driver.swipe(centerX, topY, centerX, topY-130, 1000);
    }

    @Step("Ждём появления похожих")
    public void waitSimilarVisible(){
        assertThat("Нет похожих", exists(mainPageObject.similarityText));
    }

    @Step("Ждём ответа ручки Similar")
    public void waitSimilarResponce(List<Response> responseList, String similarUrl){
        assertThat(responseList, withWaitFor(hasItem(hasResponceURL(similarUrl)), TEN_SECONDS));
    }

    @Step("Открытие меню отзыва на карточке дзена")
    public void openFeedbackMenu(int index){
        mainPageObject.zenSrtipeGroup.zenSrtipe.get(index).zenFeedbackButton.click();
    }

    @Step("Тап по карточке дзена {0}")
    public void tapToZenCard(int index){
        int CenterY = getCenterY(mainPageObject.zenSrtipeGroup.zenSrtipe.get(index));
        int CenterX = getCenterX(mainPageObject.zenSrtipeGroup.zenSrtipe.get(index));
        driver.tap(1, CenterX, CenterY, 100);
    }

    @Step("Тап по превой карточке похожих")
    public void tapToFirstSimilarityCard(){
        int CenterY = getCenterY(mainPageObject.zenSimilarityCard);
        int CenterX = getCenterX(mainPageObject.zenSimilarityCard);
        driver.tap(1, CenterX, CenterY, 100);
    }


    @Step("Беру у элемента \"{0}\" верхнюю точку координат")
    public int getTopY(AndroidElement element) {
        return element.getLocation().getY();
    }

    @Step("Беру у элемента \"{0}\" нижнюю точку координат")
    public int getBottomY(AndroidElement element) {
        int topY = element.getLocation().getY();
        int height = element.getSize().getHeight();
        return topY + height;
    }

    @Step("Сравнение двух изображения ожидается: {0}")
    public void compareBufferedImage(Boolean expectedResult, BufferedImage image1, BufferedImage image2){
        assertThat(image1, compareImage(image2, expectedResult));
    }

    @Step("Беру у элемента \"{0}\" левую точку координат")
    public int getLeftX(AndroidElement element) {
        return element.getLocation().getX();
    }

    @Step("Беру у элемента \"{0}\" правую точку координат")
    public int getRightX(WebElement element) {
        int leftX = element.getLocation().getX();
        int width = element.getSize().getWidth();
        return leftX + width;
    }

    @Step("Беру у элемента \"{0}\" центральную точку по Х")
    public int getCenterX(AndroidElement element) {
        int leftX = element.getLocation().getX();
        int width = element.getSize().getWidth();
        return leftX + width / 2;
    }

    @Step("Беру у элемента \"{0}\" центральную точку по У")
    public int getCenterY(AndroidElement element) {
        int topY = element.getLocation().getY();
        int height = element.getSize().getHeight();
        return topY + height / 2;
    }

    @Step("Тап назад(BACK)")
    public void tapBack(){
        driver.pressKeyCode(AndroidKeyCode.BACK);
    }

    @Step("Взять скриншот элемента \"{0}\"")
    public BufferedImage getElementScreenshot(AndroidElement androidElement){
        BufferedImage bufferedImage = makeScreenshotAll();

        //Make subimage AndroidElement
        Point location = androidElement.getLocation();
        int width = androidElement.getSize().getWidth();
        int height = androidElement.getSize().getHeight();
        return bufferedImage.getSubimage(location.getX(), location.getY(), width, height);
    }

    //Степы для отладки
    @Step("Отладка: падающий степ ")
    public void failedDebugStep() {
        assertTrue(false);
    }

    @Attachment(type = "image/png")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    //Внутренние методы
    private   BufferedImage makeScreenshotAll() {

        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return bufferedImage;
        }
    }
}
