package ru.sandroid.appiumeducations;

import io.appium.java_client.android.AndroidDriver;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.openqa.selenium.logging.LogEntry;
import ru.yandex.qatools.matchers.decorators.MatcherDecorators;
import ru.yandex.qatools.matchers.decorators.MatcherDecoratorsBuilder;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
import static ru.sandroid.appiumeducations.TestHelper.controlWait;
import static ru.sandroid.appiumeducations.TestHelper.findWebElementColor;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.timeoutHasExpired;


class MyMatchers {

    static Matcher<BufferedImage> hasColor(final Color color) {
        return new FeatureMatcher<BufferedImage, Color>(equalTo(color), "Элемент имеет цвет - ", "цвет -") {
            @Override
            protected Color featureValueOf(BufferedImage bufferedImage) {

                return findWebElementColor(bufferedImage, color);
            }
        };
    }

    //Стырил у автоматизаторов
    public static <T> MatcherDecoratorsBuilder withWaitFor(Matcher<? super T> matcher, long ms) {
        return MatcherDecorators.should(matcher).whileWaitingUntil(timeoutHasExpired(ms));
    }

    static Matcher<Request> hasURL(String item) {
        return new FeatureMatcher<Request, String>(equalTo(item), "expected URL - ", "actual URL - ") {
            @Override
            protected String featureValueOf(Request actual) {
                return actual.getRequestURL().getHost();
            }
        };
    }

    static Matcher<Response> hasResponceURL(final String item) {
        return new FeatureMatcher<Response, Boolean>(equalTo(true), "expected - ", "actual - ") {
            @Override
            protected Boolean featureValueOf(Response actual) {
                return actual.getResponseURL().toString().contains(item);
            }
        };
    }

    static Matcher<Request> hasRefererQuery(final String item) {
        return new FeatureMatcher<Request, Boolean>(equalTo(true), "expected - ", "actual - ") {
            @Override
            protected Boolean featureValueOf(Request actual) {
                if (actual.refererIsNull()) {
                    return false;
                }
                return actual.getReferer().getQuery().contains(item);
            }
        };
    }

    public static Matcher<BufferedImage> compareImage(final BufferedImage image, Boolean expectedResult) {
        return new FeatureMatcher<BufferedImage, Boolean>(equalTo(expectedResult), "expected - ", "actual - ") {
            @Override
            protected Boolean featureValueOf(BufferedImage actualImage) {
                if (actualImage.getWidth() == image.getWidth() && actualImage.getHeight() == image.getHeight()) {
                    for (int x = 0; x < actualImage.getWidth(); x++) {
                        for (int y = 0; y < image.getHeight(); y++) {
                            if (actualImage.getRGB(x, y) != image.getRGB(x, y))
                                return false;
                        }
                    }
                } else {
                    return false;
                }
                return true;
            }
        };
    }

    public static Matcher<AndroidDriver> logContainsString(final String logString){
        return new FeatureMatcher<AndroidDriver, Boolean>(is(true), "Should be containts: " + logString, "Containts") {
            @Override
            protected Boolean featureValueOf(AndroidDriver driver) {

                List<LogEntry> logEntryList = driver.manage().logs().get("logcat").filter(Level.ALL);
                LogParser lp = new LogParser(logEntryList);
                if(lp.findStringInLogWithoutTimer(logString)){
                    return true;
                }
                controlWait(driver, 1);
                return false;
            }
        };
    }
}
