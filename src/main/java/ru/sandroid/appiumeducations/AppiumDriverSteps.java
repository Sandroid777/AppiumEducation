package ru.sandroid.appiumeducations;

import static java.net.InetAddress.getLocalHost;
import io.appium.java_client.android.AndroidDriver;
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

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertTrue;

import static ru.sandroid.appiumeducations.MyMatchers.hasColor;
import static ru.sandroid.appiumeducations.MyMatchers.hasRefererQuery;
import static ru.sandroid.appiumeducations.MyMatchers.hasURL;
import static ru.sandroid.appiumeducations.MyMatchers.withWaitFor;
import static ru.sandroid.appiumeducations.TestHelper.elementFound;
import static ru.sandroid.appiumeducations.TestHelper.exists;


final class AppiumDriverSteps {

    private MainPageObject mainPageObject;
    private MainPageHTML mainPageHTML;
    private AndroidDriver driver;
    private final int TENSECONDS = 10000;

    public AppiumDriverSteps (AndroidDriver driver) {
        this.driver = driver;
        mainPageObject = new MainPageObject(driver);
        mainPageHTML = new MainPageHTML(driver);
    }

    @Step
    public void copyProxyFile(int port) throws UnknownHostException {

            String fileString = "yandex --proxy-server=" + getLocalHost().getHostAddress() + ":" + port;
            driver.pushFile("/data/local/tmp/yandex-browser-command-line", fileString.getBytes());

    }

    @Step
    public void coldStartBrowser(){
        driver.resetApp();
    }

    @Step("Stop browser")
    public void forceStop() {
        driver.stopApp();
    }

    @Step
    public void closeTutorial() {

        WebDriverWait waitDriver = new WebDriverWait(driver, 10);

        try {
            waitDriver.until(ExpectedConditions.elementToBeClickable(mainPageObject.sentry_bar));
        }
        catch (Exception s){
            waitDriver.until(ExpectedConditions.elementToBeClickable(mainPageObject.closeTutorialBtn)).click();
        }
    }

    @Step
    public void clickToOmnibox() {
        mainPageObject.sentry_bar.click();
    }

    @Step
    public void clickOmniboxOnWebPage() {
        mainPageObject.omniboxWebPaje.click();
    }

    @Step
    public void clickOmniboxButton() {
        mainPageObject.omniboxButton.click();
    }

    @Step
    public void clickHistorySuggest() {
        mainPageObject.historySuggest.click();
    }


    @Step
    public void sendKeys(String string) {
        mainPageObject.omni_edittext.sendKeys(string);
    }

    @Step //устаревший вариант для работы старых тестов
    private void suggestN3Click() {
        mainPageObject.suggestN3.click();
    }

    @Step
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

    @Step
    public void checkSuggestSizeOver(int i) {
        assertThat(mainPageObject.suggestList, hasSize(greaterThan(i)));
    }

    @Step
    public void checkColorInHistorySuggest(WebElement webElement, Color findColor1, Color findColor2 ){

        //Make Screenshot
        File screanShot  = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(screanShot);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Make subimage WebElement
        Point location = webElement.getLocation();
        int width = webElement.getSize().getWidth();
        int height = webElement.getSize().getHeight();
        BufferedImage elementImage = bufferedImage.getSubimage(location.getX(), location.getY(), width, height);

        assertThat(elementImage, both(hasColor(findColor1)).and(hasColor(findColor2)));
    }

    @Step
    public void checkingMeteoWizard() {
        assertThat("Нет калдунщика погоды", exists(mainPageObject.groupMeteoWizard.wizard));
        assertThat("В кондунщике нет температуры", mainPageObject.groupMeteoWizard.wizard.getText().contains("°C"));
        assertThat("Паника Часы в колдунщике погоды ", !elementFound(mainPageObject.groupMeteoWizard, "bro_common_omnibox_image"));
    }

    @Step
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

        assertThat(requestList, withWaitFor(hasItem(both(hasURL(navigationUrl)).and(hasRefererQuery("android"))), TENSECONDS));

    }

    //Степ для отладки
    @Step
    public void failedDebugStep() {
        assertTrue(false);
    }

    @Attachment(type = "image/png")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    //Получаем текст из навигационника в омнибоксе
    @Step
    public  String getTextOmniBlueLink() {
        return mainPageObject.omniBlueLink.getText();
    }

}
