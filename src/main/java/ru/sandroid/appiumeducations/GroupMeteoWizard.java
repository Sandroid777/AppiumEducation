package ru.sandroid.appiumeducations;
import io.appium.java_client.android.AndroidElement;

import io.appium.java_client.pagefactory.AndroidFindBy;

@AndroidFindBy(id = "bro_suggest_wizard")
public class GroupMeteoWizard extends AndroidElement {

    //сам калдунщик
    @AndroidFindBy(id = "bro_common_omnibox_wizard_text")
    public AndroidElement wizard;

    //текст в саджесте
    @AndroidFindBy(id = "bro_common_omnibox_text")
    public AndroidElement text;

    //кнопка подстановки
    @AndroidFindBy(id = "bro_common_omnibox_button")
    public AndroidElement autoCompletBtn;

}
