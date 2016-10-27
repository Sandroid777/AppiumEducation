package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * Created by sandrin on 26.10.2016.
 */
public class CustomRule extends TestWatcher {

    private AppiumDriver driver;
    private AppiumDriverSteps steps;

    CustomRule(AppiumDriver driver, AppiumDriverSteps steps){
        this.driver = driver;
        this.steps = steps;
    }

    @Override
    protected void finished(Description description) {
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
