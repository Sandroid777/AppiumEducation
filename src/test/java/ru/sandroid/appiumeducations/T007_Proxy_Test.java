package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import ru.yandex.qatools.allure.annotations.Title;
import java.net.MalformedURLException;

public class T007_Proxy_Test {

    private AppiumDriver driver;
    private AppiumDriverSteps steps;
    private BrowserMobProxyServer server;

    @Before
    public void setUp() throws Exception {

        //настройка параметров
        TestPreparation testPreparation= new TestPreparation();
        //Добавляю проксисервер
        testPreparation.addProxyServer(8989);

        server = testPreparation.getProxyServer();
        driver = testPreparation.getDriver();
        steps = testPreparation.getSteps();
    }

    @Rule
    public TestRule watchman = new CustomRule(driver, steps, server);

    @Title("Реферер содержит from с подстрокой android ")
    @Test
    public void refererInOmniNavigateLinkContainsFromAndroid() throws MalformedURLException {

        steps.closeTutorial();
        steps.clickToOmnibox();
        steps.sendKeys("wikip");
        String navigationUrl = steps.getTextOmniBlueLink();
        steps.clickNavigateInOmnibox();
        steps.waitLoadPage(30);
        steps.containtsAndroidInReferer(server.getHar(), navigationUrl);
        }
}
