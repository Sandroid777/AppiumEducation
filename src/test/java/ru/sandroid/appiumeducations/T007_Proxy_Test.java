package ru.sandroid.appiumeducations;


import io.appium.java_client.android.AndroidDriver;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import ru.yandex.qatools.allure.annotations.Title;
import java.util.LinkedList;
import java.util.List;


public class T007_Proxy_Test {

    private AndroidDriver driver;
    private AppiumDriverSteps steps;
    private BrowserMobProxyServer server;
    private List<Request> requestList;
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

        testPreparation.createProxy();
        testPreparation.addInterceptRequest();
        testPreparation.startProxy(PORT);

        requestList = testPreparation.getRequestList();
        steps.closeTutorial();

        steps.clickToOmnibox();
        steps.sendKeys("wikip");
        String navigationUrl = steps.getTextOmniBlueLink();
        steps.clickNavigateInOmnibox();
        steps.containtsAndroidInReferer(requestList, navigationUrl);
        }
}
