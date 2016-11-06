package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;

class TestHelper {

    //Ищим реферер в HarEntry
    public  static  URL getRefererInEntry(HarEntry entry) throws MalformedURLException {

        List<HarNameValuePair> headersValue =  entry.getRequest().getHeaders();
            for(HarNameValuePair value : headersValue)
            {
                if(value.getName().equals("Referer")){
                    return new URL(value.getValue());
                }
            }
        return null;
    }

    //Контролируемое ожидание
    //Принимает аппиумный драйвер и int(сколько секунд будем ждать)
    public static void controlWait(AppiumDriver driver, int i){
        WebDriverWait wdw = new WebDriverWait(driver, i);
        try {
            //Пытаемся найти несуществующий элимент i секунд
            wdw.until(ExpectedConditions.elementToBeClickable(By.id("NONEXISTENT ELEMENT")));
        }
        catch (Exception s){
            //Ничего не делаем
        }
    }

    public static Color findWebElementColor(BufferedImage bufferedImage, Color color){

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        for(int y= 0; y<height; y++ ){
            for(int x= 0; x<width; x++){

                Color takenColor = new Color(bufferedImage.getRGB(x, y));
                if(takenColor.getRGB() == color.getRGB()) {
                    return takenColor;
                }
            }
        }
        return  new Color(0, 0, 0);
    }

    public static boolean exists(AndroidElement mainElement){

        try {
            mainElement.isDisplayed();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean exists(HtmlElement mainElement){

        try {
            mainElement.isDisplayed();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    public static boolean elementFound(AndroidElement androidElement, String string){

        try {
            androidElement.findElement(By.id(string));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean elementFound(HtmlElement htmlElement, String string){

        try {
            htmlElement.findElement(By.id(string));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
