package ru.sandroid.appiumeducations;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.awt.*;
import static org.hamcrest.core.IsEqual.equalTo;


class MyMatchers {

    static Matcher<SuggestItem> navHasColor(Color color) {
        return new FeatureMatcher<SuggestItem, Color>(equalTo(color), "Навигационник имеет цвет - ", "цвет -") {
            @Override
            protected Color  featureValueOf(SuggestItem suggestItem) {
                return suggestItem.getNavColor();
            }
        };
    }

    static Matcher<SuggestItem> titleHasColor(Color color) {
        return new FeatureMatcher<SuggestItem, Color>(equalTo(color), "Тайттл имеет цвет - ", "цвет -") {
            @Override
            protected Color  featureValueOf(SuggestItem suggestItem) {
                return suggestItem.getTitleColor();
            }
        };
    }


}
