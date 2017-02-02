package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

class MainPageObject {

    //Омнибокс
    @AndroidFindBy(id = "bro_sentry_bar_fake")
    public WebElement sentryBar;

    //Омнибокс на вэбвкладке
    @AndroidFindBy(id = "bro_omnibar_address_title_text")
    public WebElement omniboxWebPage;

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
    public WebElement omniTextEdit;

    //устаревший вариант для работы старых тестов
    @AndroidFindBy(xpath = "//*[@class='android.widget.ListView' and @index = '2']")
    public WebElement suggestN3;

    //саджест
    @AndroidFindBy(id = "bro_common_omnibox_text_layout")
    public List<WebElement> suggestList;

    //Навигационник в омнибоксе
    @AndroidFindBy(id = "bro_sentry_bar_input_blue_link")
    public AndroidElement omniBlueLink;

    public  GroupMeteoWizard groupMeteoWizard;
    public  GroupHistorySuggest groupHistorySuggest;

    public ZenSrtipeGroup zenSrtipeGroup;

    @AndroidFindBy(id = "zen_ribbon_text_similarity_card")
    public AndroidElement similarityText;

    //Фидкек меню дзена
    @AndroidFindBy(id = "text")
    public List<AndroidElement>  zenFeedbackMenu;

    @AndroidFindBy(id = "zen_ribbon_image_similarity_card")
    public ZenSimilarityCard zenSimilarityCard;

    MainPageObject(AppiumDriver driver){
        PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
    }
}


