import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import greenfoot.Color;

/**
 * Aktor Wall (Dinding) yang tak terlihat. 
 * Aktor lain tidak akan bisa menembusnya.
 */
public class Wall extends Actor
{
    /**
     * Constructor untuk Wall.
     * Membuat sebuah kotak penghalang.
     */
    public Wall()
    {
        GreenfootImage img = new GreenfootImage(13, 13);
    
        img.setColor(new Color(0, 100, 255, 0));
        img.fill(); 
        setImage(img);
    }
}