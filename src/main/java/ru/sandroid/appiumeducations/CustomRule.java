package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class CustomRule extends TestWatcher {

    private AppiumDriver driver;
    private AppiumDriverSteps steps;
    BrowserMobProxyServer server;

    CustomRule(AppiumDriver driver, AppiumDriverSteps steps){
        this.driver = driver;
        this.steps = steps;
    }

    CustomRule(AppiumDriver driver, AppiumDriverSteps steps,BrowserMobProxyServer server){
        this.driver = driver;
        this.steps = steps;
        this.server = server;
    }

    @Override
    protected void finished(Description description) {
        if(server != null) {
            server.stop();
        }

        if(driver != null) {
            driver.quit();
        }
    }

    @Override
    protected void failed(Throwable e, Description description) {
        steps.makeScreenshot();
        driver.quit();
    }
}
