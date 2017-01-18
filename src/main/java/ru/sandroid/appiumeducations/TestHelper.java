package ru.sandroid.appiumeducations;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


class TestHelper {

    //Контролируемое ожидание
    public static void controlWait(AndroidDriver driver, int i){
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
        //return  new Color(0, 0, 0); //TODO проверить как будет работать с возвратом null
        return null;
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

    public static String fileToString(String path){

        StringBuffer stringBuffer = new StringBuffer();

        try{
            FileInputStream fileStream = new FileInputStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileStream, "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuffer.append(line);
                line = bufferedReader.readLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }
}
