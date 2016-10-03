package ru.sandroid.appiumeducations;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.hamcrest.core.IsEqual.equalTo;
import static ru.sandroid.appiumeducations.TestHelper.findWebElementColor;


class MyMatchers {

   static Matcher<BufferedImage> hasColor(final Color color) {
        return new FeatureMatcher<BufferedImage, Color>(equalTo(color), "Элемент имеет цвет - ", "цвет -") {
            @Override
            protected Color  featureValueOf(BufferedImage bufferedImage) {

                return findWebElementColor(bufferedImage, color);
            }
        };
    }


}
