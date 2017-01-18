package ru.sandroid.appiumeducations;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

@AndroidFindBy(id = "zen_ribbon_image_similarity_card")
public class ZenSimilarityCard extends AndroidElement{

    @AndroidFindBy(id = "zen_similarity_header")
    public AndroidElement header;

    @AndroidFindBy(id = "zen_similarity_body")
    public AndroidElement body;

    @AndroidFindBy(id = "zen_similarity_image")
    public AndroidElement image;

}
