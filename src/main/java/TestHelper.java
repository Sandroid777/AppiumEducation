import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class TestHelper {

    //Контролируемое ожидание
    //Принимает аппиумный драйвер и int(сколько секунд будем ждать)
    public static void ControlWait(AppiumDriver driver, int i){
        WebDriverWait wdw = new WebDriverWait(driver, i);
        try {
            //Пытаемся найти несуществующий элимент i секунд
            wdw.until(ExpectedConditions.elementToBeClickable(By.id("NONEXISTENT ELEMENT")));
        }
        catch (Exception s){
            //Ничего не делаем
        }
    }
}