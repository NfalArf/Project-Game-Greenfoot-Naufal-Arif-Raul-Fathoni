import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)

/**
 * Aktor untuk menampilkan ikon nyawa Pac-Man.
 */
public class LifeIcon extends Actor
{
    /**
     * Constructor untuk LifeIcon.
     * Mengatur gambar ikon nyawa.
     */
    public LifeIcon()
    {
        GreenfootImage img = new GreenfootImage("icon_life.png");

        setImage(img);
    }
}