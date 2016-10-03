package ru.sandroid.appiumeducations;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.image.BufferedImage;

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
}
