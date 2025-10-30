import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import greenfoot.Color;

/**
 * Aktor untuk menampilkan satu digit angka (0-9) dari aset.
 */
public class DigitActor extends Actor
{
    private int digitWidth = 16;  
    private int digitHeight = 16; 
    private int scaleFactor = 2; 

    /**
     * Constructor.
     */
    public DigitActor(int digit)
    {
        if (digit < 0 || digit > 9) digit = 0; 
        String filename = "num_" + digit + ".png"; 

        try {
            GreenfootImage img = new GreenfootImage(filename);
            img.scale(img.getWidth() * scaleFactor, img.getHeight() * scaleFactor); 
            digitWidth = img.getWidth(); 
            digitHeight = img.getHeight();
            setImage(img);
        } catch (IllegalArgumentException e) {
            System.err.println("Error memuat gambar digit '" + filename + "': " + e.getMessage());
            setImage(new GreenfootImage("?", 24, Color.RED, new Color(0,0,0,0))); 
        }
    }

    public int getDigitWidth() {
        return digitWidth;
    }
}