package ru.sandroid.appiumeducations;

import java.awt.*;
import java.awt.image.BufferedImage;
import  org.openqa.selenium.Point;

public class TestedItem {

    private Color color;
    private BufferedImage screenShot;
    private Point location;

    public TestedItem(BufferedImage screenShot, Point location ){

        this.screenShot = screenShot;
        this.location = location;
        findColor(screenShot, location);
    }

    public  Color getItemColor(){return  color;}
    private void setColor(Color c){color = c;}

    //Search color
    public void findColor(BufferedImage bufferedImage, Point p ){
        //Search color in a square 20x20 pixels
        for(int y= 0; y<20; y++ ){
            for(int x= 0; x<20; x++){

                Color color = new Color(bufferedImage.getRGB(p.getX()+ x, p.getY()+ y));
                if(color.getRed() == 6 && color.getGreen() == 112 && color.getBlue() == 193){
                    setColor(Color.BLUE);
                }
                if(color.getRed() == 147 && color.getGreen() == 147 && color.getBlue() == 147 ){
                    setColor(Color.GRAY);
                }
            }
        }

    }
}
