package ru.sandroid.appiumeducations;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

@AndroidFindBy(id = "bro_suggest_search_history")
public class GroupHistorySuggest extends AndroidElement {

    //иконка часов
    @AndroidFindBy(id = "bro_common_omnibox_image")
    public AndroidElement historyClock;

    //текст в саджесте
    @AndroidFindBy(id = "bro_common_omnibox_text")
    public AndroidElement text;

    //кнопка подстановки
    @AndroidFindBy(id = "bro_common_omnibox_button")
    public AndroidElement autoCompletBtn;


}
