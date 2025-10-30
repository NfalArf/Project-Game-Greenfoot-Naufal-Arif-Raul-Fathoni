import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import greenfoot.Color;

/**
 * Aktor ini menampilkan gambar statis (huruf, ikon, teks jadi).
 */
public class TextActor extends Actor
{
    private int scaleFactor = 2;

    /**
     * Constructor yang menerima nama file gambar.
     */
    public TextActor(String imageName)
    {
        try {
            GreenfootImage img = new GreenfootImage(imageName);
            img.scale(img.getWidth() * scaleFactor, img.getHeight() * scaleFactor);
            setImage(img);
        } catch (IllegalArgumentException e) {
             System.err.println("Error memuat TextActor '" + imageName + "': " + e.getMessage());
             setImage(new GreenfootImage("!", 24, Color.RED, new Color(0,0,0,0)));
        }
    }

     /**
     * Constructor Kosong
     */
    public TextActor() {
        setImage(new GreenfootImage(1, 1));
    }

    public int getTextWidth() {
        return getImage().getWidth();
    }
}