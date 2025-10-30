import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)

/**
 * Aktor Buah (seperti Ceri) yang muncul sebagai bonus.
 */
public class Fruit extends Actor
{
    private int fruitSize = 26; 
    
    /**
     * Constructor untuk Fruit.
     */
    public Fruit()
    {
        GreenfootImage img = new GreenfootImage("buah.png");
        
        img.scale(14, 14);
        
        setImage(img);
    }
}