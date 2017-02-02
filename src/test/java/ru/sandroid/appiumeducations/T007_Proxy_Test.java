package ru.sandroid.appiumeducations;


import io.appium.java_client.android.AndroidDriver;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.List;


public class T007_Proxy_Test {

    private AndroidDriver driver;
    private AppiumDriverSteps steps;
    private AndridProxy server;
    private List<Request> requestList;

    public final int PORT = 8989; //порт для прокси сервера

    @Before
    public void setUp() throws Exception {

        driver = androidCustomRule.getDriver();
        steps = new AppiumDriverSteps(driver);

        server = new AndridProxy();
        server.addInterceptRequest();
        server.startProxy();
    }

    @Rule
    public AndroidCustomRule androidCustomRule = new AndroidCustomRule();

    @Title("Реферер содержит from с подстрокой android ")
    @Test
    public void refererInOmniNavigateLinkContainsFromAndroid() throws Exception {

        steps.copyProxyFile(PORT);
        steps.coldStartBrowser();

        requestList = server.getRequestList();
        steps.closeTutorial();

        steps.clickToOmnibox();
        steps.sendKeys("wikip");
        String navigationUrl = steps.getTextOmniBlueLink();
        steps.clickNavigateInOmnibox();
        steps.containtsAndroidInReferer(requestList, navigationUrl);
        }
}
