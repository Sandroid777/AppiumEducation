package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class MainPageHTML {

    public MainPageHTML(AppiumDriver driver) {
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)), this);
    }

    public  GroupHTMLMeteoWizard groupHTMLMeteoWizard;

}
