package ru.sandroid.appiumeducations;

import static org.hamcrest.Matchers.both;
import static ru.sandroid.appiumeducations.MyMatchers.*;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;



class AppiumDriverSteps {

        private MainPageObject mainPageObject;

        public AppiumDriver driver;

        public AppiumDriverSteps (AppiumDriver driver) {

            this.driver = driver;
            mainPageObject = new MainPageObject(driver);
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
    public void suggestN3Click() {
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

    @Step
    public void waitLoadPage(int waitTime){

        Date startTime =new Date();
        Date finalFindTime = new Date(startTime.getTime() + waitTime * 1000); //время ожидания 30сек от тапа

        boolean loadPage = false;
        //проверяю логи на наличие события "url opened"
        while(finalFindTime.getTime() > System.currentTimeMillis()){

            List<LogEntry> logEntryList = driver.manage().logs().get("logcat").filter(Level.ALL);
            LogParser lp = new LogParser(logEntryList, startTime);

            //Запускаю поиск. если находим "url opened" то выходим
            if(lp.FindStringInLog("url opened")){
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

    //Степ для отладки
    @Step
    public void failedDebugStep() {
         assertTrue(false);
    }

    @Attachment(type = "image/png")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


}


