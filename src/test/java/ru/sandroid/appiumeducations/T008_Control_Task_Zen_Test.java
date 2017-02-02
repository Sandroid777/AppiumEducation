package ru.sandroid.appiumeducations;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Title;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ArrayList;

import static ru.sandroid.appiumeducations.TestHelper.fileToString;

public class T008_Control_Task_Zen_Test {
    private AndroidDriver driver;
    private AppiumDriverSteps steps;
    private AndridProxy server;
    private MainPageObject mainPageObject;
    private List<Response> responseList;

    public final static String ZEN_EXPORT = "api/v2/android/export_ob";
    public final static String ZEN_MORE = "api/v2/android/more";
    public final static String ZEN_SIMLAR = "/api/v2/android/similar";

    private final Color feedbackMenuBlueText = new Color(0, 118, 255);

    @Rule
    public AndroidCustomRule androidCustomRule = new AndroidCustomRule();

    @Before
    public void setUp() throws UnknownHostException{

        driver = androidCustomRule.getDriver();
        steps = new AppiumDriverSteps(driver);
        mainPageObject = new MainPageObject(driver);
        server = new AndridProxy();
        server.startProxy();

        steps.copyProxyFile(server.getProxyServer().getPort());
        steps.coldStartBrowser();
    }

    @Title("В меню отзыва, пункт обозначенный main=True выделен синим цветом")
    @Test
    public void feedbackItemWithMainTrueHasBlueColor() throws UnknownHostException {

        ArrayList<String> hostList = new ArrayList();
        hostList.add(ZEN_EXPORT);
        hostList.add(ZEN_MORE);

        ArrayList<String>jsonList = new ArrayList();
        jsonList.add(fileToString("resources\\zenCards.json"));
        jsonList.add(fileToString("resources\\zenCards.json"));

        server.replaceJsonInResponse(hostList, jsonList);

        steps.closeTutorial();
        steps.openZenStripe();
        steps.openFeedbackMenu(0);
        steps.findElementColor(mainPageObject.zenFeedbackMenu.get(2), feedbackMenuBlueText);
    }

    @Title("Карточки похожих статей, по которым был осуществлен переход, не отмечаются прочтенным")
    @Test
    public void similarityCardNotChangeColorWhenRead() throws UnknownHostException {

        ArrayList<String> handleList = new ArrayList();
        handleList.add(ZEN_EXPORT);
        handleList.add(ZEN_MORE);
        handleList.add(ZEN_SIMLAR);

        ArrayList<String>jsonList = new ArrayList();
        jsonList.add(fileToString("resources\\zenSingleCard.json"));
        jsonList.add(fileToString("resources\\zenSingleCard.json"));
        jsonList.add(fileToString("resources\\similar.json"));
        server.replaceJsonInResponse(handleList, jsonList);
        responseList = server.getResponseList();

        steps.closeTutorial();
        steps.openZenStripe();

        steps.tapToZenCard(0);
        steps.waitSimilarResponce(responseList, ZEN_SIMLAR);
        steps.waitRecordInLog("url opened", driver);
        steps.tapBack();
        steps.waitSimilarVisible();
        BufferedImage similarImageBeforeTap = steps.getElementScreenshot(mainPageObject.zenSimilarityCard.image);

        steps.tapToFirstSimilarityCard();
        steps.tapBack();
        steps.waitSimilarVisible();
        BufferedImage similarImageAfterTap = steps.getElementScreenshot(mainPageObject.zenSimilarityCard.image);

        Boolean expectedResult = true;
        steps.compareBufferedImage(expectedResult, similarImageBeforeTap, similarImageAfterTap);
    }
}
