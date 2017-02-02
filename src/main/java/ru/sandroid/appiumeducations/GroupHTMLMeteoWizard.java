package ru.sandroid.appiumeducations;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

@Name("Search form")
@FindBy(id = "bro_suggest_wizard")
public class GroupHTMLMeteoWizard extends HtmlElement {

    @Name("калдунщик")
    @FindBy(id = "bro_common_omnibox_wizard_text")
    public HtmlElement wizard;

    @Name("текст в саджесте")
    @FindBy(id = "bro_common_omnibox_text")
    public HtmlElement text;

    @Name("кнопка подстановки")
    @FindBy(id = "bro_common_omnibox_button")
    public HtmlElement autoCompletBtn;
}
