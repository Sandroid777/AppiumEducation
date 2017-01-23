package ru.sandroid.appiumeducations;

import io.appium.java_client.android.AndroidDriver;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import ru.yandex.qatools.allure.annotations.Title;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.List;
import java.util.ArrayList;
import static ru.sandroid.appiumeducations.TestHelper.fileToString;

public class T008_Control_Task_Zen_Test {
    private AndroidDriver driver;
    private AppiumDriverSteps steps;
    private BrowserMobProxyServer server;
    private TestPreparation testPreparation;
    private MainPageObject mainPageObject;
    private List<Response> responseList;


    public final int PORT = 8989; //порт для прокси сервера
    public final String ZEN_EXPORT = "api/v2/android/export_ob";
    public final String ZEN_MORE = "api/v2/android/more";
    public final String ZEN_SIMLAR = "/api/v2/android/similar";

    @Before
    public void setUp() throws Exception {

        //настройка параметров
        testPreparation = new TestPreparation();
        testPreparation.createDriverAndSteps();

        server = testPreparation.getProxyServer();
        driver = testPreparation.getDriver();
        steps = testPreparation.getSteps();
        mainPageObject = new MainPageObject(driver);
    }


    @Rule
    public TestRule watchman = new CustomRule(driver, steps, server);

    @Title("В меню отзыва, пункт обозначенный main=True выделен синим цветом")
    @Test
    public void feedbackItemWithMainTrueHasBlueColor() throws Exception {

        steps.copyProxyFile(PORT);
        steps.coldStartBrowser();

        ArrayList<String> HostList = new ArrayList();
        HostList.add(ZEN_EXPORT);
        HostList.add(ZEN_MORE);

        ArrayList<String>JsonList = new ArrayList();
        JsonList.add(fileToString("resources\\zenCards.json"));
        JsonList.add(fileToString("resources\\zenCards.json"));

        testPreparation.createProxy();
        testPreparation.replaceJsonInResponse(HostList, JsonList);
        testPreparation.startProxy(PORT);

        steps.closeTutorial();
        steps.openZenStripe();
        steps.openFeedbackMenu(0);
        steps.findElementColor(mainPageObject.zenFeedbackMenu.get(2), new Color(0, 118, 255));
    }

    @Title("Карточки похожих статей, по которым был осуществлен переход, не отмечаются прочтенным")
    @Test
    public void similarityCardNotChangeColorWhenRead() throws Exception {

        steps.copyProxyFile(PORT);
        steps.coldStartBrowser();

        ArrayList<String> HostList = new ArrayList();
        HostList.add(ZEN_EXPORT);
        HostList.add(ZEN_MORE);
        HostList.add(ZEN_SIMLAR);

        ArrayList<String>JsonList = new ArrayList();
        JsonList.add(fileToString("resources\\zenSingleCard.json"));
        JsonList.add(fileToString("resources\\zenSingleCard.json"));
        JsonList.add(fileToString("resources\\similar.json"));

        testPreparation.createProxy();
        testPreparation.replaceJsonInResponse(HostList, JsonList);
        testPreparation.startProxy(PORT);

        responseList = testPreparation.getResponseList();

        steps.closeTutorial();
        steps.openZenStripe();

        steps.tapToZenCard(0);
        steps.waitSimlarResponce(responseList, ZEN_SIMLAR);
        steps.waitRecordInLog("url opened", driver);
        steps.tapBack();
        steps.waitSimlarVisible();
        BufferedImage similarImageBeforeTap = steps.getElementScreenshot(mainPageObject.zenSimilarityCard.image);

        steps.tapToFirstSimilarityCard();
        steps.tapBack();
        steps.waitSimlarVisible();
        BufferedImage similarImageAfterTap = steps.getElementScreenshot(mainPageObject.zenSimilarityCard.image);

        Boolean expectedResult = true;
        steps.compareBufferedImage(expectedResult, similarImageBeforeTap, similarImageAfterTap);
    }
}
