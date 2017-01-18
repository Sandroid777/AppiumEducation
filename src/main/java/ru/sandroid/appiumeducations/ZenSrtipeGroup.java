package ru.sandroid.appiumeducations;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;


@AndroidFindBy(id = "bro_zen_recyclerview")
public class ZenSrtipeGroup  extends AndroidElement {
    public List<ZenCard> zenSrtipe;
}