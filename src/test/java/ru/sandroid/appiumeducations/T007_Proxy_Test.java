package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import ru.yandex.qatools.allure.annotations.Title;
import java.util.LinkedList;



public class T007_Proxy_Test {

    private AppiumDriver driver;
    private AppiumDriverSteps steps;
    private BrowserMobProxyServer server;
    private LinkedList<Request> requestList;
    private TestPreparation testPreparation;

    public final int PORT = 8989; //порт для прокси сервера

    @Before
    public void setUp() throws Exception {

        //настройка параметров
        testPreparation = new TestPreparation();
        testPreparation.createDriverAndSteps();

        server = testPreparation.getProxyServer();
        driver = testPreparation.getDriver();
        steps = testPreparation.getSteps();
    }

    @Rule
    public TestRule watchman = new CustomRule(driver, steps, server);

    @Title("Реферер содержит from с подстрокой android ")
    @Test
    public void refererInOmniNavigateLinkContainsFromAndroid() throws Exception {

        steps.copyProxyFile(PORT);
        steps.coldStartBrowser();

        testPreparation.addProxyServer(PORT);
        requestList = testPreparation.getRequestList();
        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("wikip");
        String navigationUrl = steps.getTextOmniBlueLink();
        steps.clickNavigateInOmnibox();
        steps.containtsAndroidInReferer(requestList, navigationUrl);
        }
}
