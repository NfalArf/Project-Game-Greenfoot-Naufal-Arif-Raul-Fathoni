import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)

/**
 * Power Pellet (titik besar) yang dimakan Pacman.
 */
public class PowerPellet extends Actor
{
    private final int RESPAWN_DELAY = 600;
    private boolean isEaten = false;
    private int respawnTimer = 0;
    private GreenfootImage originalImage;
    private GreenfootImage blankImage;

    /**
     * Constructor untuk PowerPellet.
     */
    public PowerPellet()
    {
        GreenfootImage img = new GreenfootImage("dot_power.png");

        int pelletSize = 20; 
        img.scale(pelletSize, pelletSize);

        originalImage = img;
        setImage(originalImage);
        blankImage = new GreenfootImage(1, 1);
        blankImage.setTransparency(0);
    }

    /**
     * Method act() - Mengatur logika timer respawn.
     */
    public void act()
    {
        if (isEaten)
        {
            if (respawnTimer > 0)
            {
                respawnTimer--;
            }
            else
            {
                respawn();
            }
        }
    }

    /**
     * Dipanggil oleh Pacman saat menyentuh Power Pellet.
     */
    public boolean eat() 
    {
        if (!isEaten)
        {
            isEaten = true;
            setImage(blankImage);
            respawnTimer = RESPAWN_DELAY;
            return true;
        }
        return false;
    }

    /**
     * Memunculkan kembali Power Pellet.
     */
    private void respawn()
    {
        isEaten = false;
        setImage(originalImage);
        respawnTimer = 0;
    }

    /**
     * Mengecek status.
     */
    public boolean isEaten()
    {
        return isEaten;
    }
}