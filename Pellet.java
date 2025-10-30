import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)

/**
 * Pellet kecil yang dimakan Pacman.
 */
public class Pellet extends Actor
{
    private final int RESPAWN_DELAY = 300; 
    private boolean isEaten = false;      
    private int respawnTimer = 0;         
    private GreenfootImage originalImage; 
    private GreenfootImage blankImage;    
    
    /**
     * Constructor untuk Pellet.
     */
    public Pellet()
    {
        GreenfootImage img = new GreenfootImage("dot_kecil1.png");

        int pelletSize = 25;
        img.scale(pelletSize, pelletSize);

        originalImage = img;
        setImage(originalImage);
        blankImage = new GreenfootImage(1, 1);
        blankImage.setTransparency(0); 
    }

    /**
     * Method act() - Dijalankan terus menerus oleh Greenfoot.
     * Mengatur logika timer respawn.
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
     * Method ini dipanggil oleh Pacman saat dia menyentuh pellet ini.
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
     * Method ini dipanggil oleh 'act()' saat timer respawn habis.
     * Memunculkan kembali pellet.
     */
    private void respawn()
    {
        isEaten = false;          
        setImage(originalImage); 
        respawnTimer = 0;         
    }

    /**
     * Method untuk mengecek status apakah pellet sedang dimakan (tak terlihat).
     */
    public boolean isEaten()
    {
        return isEaten;
    }
}