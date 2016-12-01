package ru.sandroid.appiumeducations;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import ru.yandex.qatools.matchers.decorators.MatcherDecorators;
import ru.yandex.qatools.matchers.decorators.MatcherDecoratorsBuilder;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.hamcrest.core.IsEqual.equalTo;
import static ru.sandroid.appiumeducations.TestHelper.findWebElementColor;
import static ru.yandex.qatools.matchers.decorators.MatcherDecorators.timeoutHasExpired;


class MyMatchers {

   static Matcher<BufferedImage> hasColor(final Color color) {
        return new FeatureMatcher<BufferedImage, Color>(equalTo(color), "Элемент имеет цвет - ", "цвет -") {
            @Override
            protected Color  featureValueOf(BufferedImage bufferedImage) {

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

    static Matcher<Request> hasRefererQuery(final String item) {
        return new FeatureMatcher<Request, Boolean>(equalTo(true), "expected - ", "actual - ") {
            @Override
            protected Boolean featureValueOf(Request actual) {
                if(actual.refererIsNull()){
                    return false;
                }
                return actual.getReferer().getQuery().contains(item);
            }
        };
    }
}
