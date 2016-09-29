package ru.sandroid.appiumeducations;

/**
 * Created by sandrin on 30.09.2016.
 */

import org.openqa.selenium.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import org.openqa.selenium.By;
import  org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

class  SuggestItem{

    private WebElement suggestItem;
    private Color navColor;
    private Color titleColor;
    private BufferedImage screenShot;
    private org.openqa.selenium.Point navLocation;
    private org.openqa.selenium.Point titleLocation;

    Color getNavColor(){return  navColor;}
    Color getTitleColor(){return  titleColor;}

    //Конструктор
    public SuggestItem(WebElement suggestItem, BufferedImage screenShot) {
        this.suggestItem = suggestItem;
        this.screenShot = screenShot;

        //Поиск позиции элементов в саджесте
        navLocation = suggestItem.findElement(By.id("bro_common_omnibox_text")).getLocation();
        titleLocation = suggestItem.findElement(By.id("bro_common_omnibox_wizard_text")).getLocation();

        //Нахожу цвет элементов
        navColor= findColor(navLocation);
        titleColor = findColor(titleLocation);
    }

    //Search color
    public Color findColor(org.openqa.selenium.Point p){
        //Search color in a square 20x20 pixels
        for(int y= 0; y<20; y++ ){
            for(int x= 0; x<20; x++){

                Color takenColor = new Color(screenShot.getRGB(p.getX()+ x, p.getY()+ y));
                if(takenColor.getRed() == 6 && takenColor.getGreen() == 112 && takenColor.getBlue() == 193){
                    return Color.BLUE;
                }
                if(takenColor.getRed() == 147 && takenColor.getGreen() == 147 && takenColor.getBlue() == 147 ){
                    return Color.GRAY;
                }
            }
        }
        return new Color(0, 0, 0);
    }
}


