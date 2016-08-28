import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

class MainPageObject {

    public MainPageObject(AppiumDriver driver){
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    //Омнибокс
    @AndroidFindBy(id = "bro_sentry_bar_fake")
    public WebElement sentry_bar;

    //Кнопка закрытия туториала
    @AndroidFindBy(id = "activity_tutorial_close_button")
    public WebElement closeTutorialBtn;

    //Строка ввода в омнибоксе
    @AndroidFindBy(id = "bro_sentry_bar_input_edittext")
    public WebElement omni_edittext;

    //3 элемент саджеста
    @AndroidFindBy(xpath = "//*[@class='android.widget.RelativeLayout' and @index = '2']")
    public WebElement suggestN3;




}
