package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

class MainPageObject {

    public MainPageObject(AppiumDriver driver){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    //Омнибокс
    @AndroidFindBy(id = "bro_sentry_bar_fake")
    public WebElement sentry_bar;

    //Омнибокс на вэбвкладке
    @AndroidFindBy(id = "bro_omnibar_address_title")
    public WebElement omniboxWebPaje;

    @AndroidFindBy(id = "bro_sentry_bar_input_button")
    public WebElement omniboxButton;

    //History suggest
    @AndroidFindBy(id = "bro_suggest_history")
    public WebElement historySuggest;

    //Кнопка закрытия туториала
    @AndroidFindBy(id = "activity_tutorial_close_button")
    public WebElement closeTutorialBtn;

    //Строка ввода в омнибоксе
    @AndroidFindBy(id = "bro_sentry_bar_input_edittext")
    public WebElement omni_edittext;

    //устаревший вариант для работы старых тестов
    @AndroidFindBy(xpath = "//*[@class='android.widget.ListView' and @index = '2']")
    public WebElement suggestN3;

    //саджест
    @AndroidFindBy(id = "bro_common_omnibox_text_layout")
    public List<WebElement> suggestList;

}