package ru.sandroid.appiumeducations;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.awt.*;

import static org.hamcrest.core.IsEqual.equalTo;


public class MyMatchers {

    public static Matcher<TestedItem> textHasColor(Color color) {
        return new FeatureMatcher<TestedItem, Color>(equalTo(color), "элемент имеет цвет - ", "цвет -") {
            @Override
            protected Color  featureValueOf(TestedItem testedItem) {
                return testedItem.getItemColor();
            }
        };
    }



}
