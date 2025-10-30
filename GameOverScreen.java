import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import greenfoot.Color;

/**
 * Aktor pop-up yang menampilkan layar Game Over dan tombol.
 */
public class GameOverScreen extends Actor
{
    private GreenfootSound gameOverMusic;
    
    /**
     * Constructor untuk GameOverScreen.
     */
    public GameOverScreen()
    {
        int popupWidth = 300; 
        int popupHeight = 150; 
        GreenfootImage background = new GreenfootImage(popupWidth, popupHeight);
       
        background.setColor(new Color(0, 0, 0, 180)); 
        background.fill();
      
        GreenfootImage textImage = new GreenfootImage("GAME OVER", 36, Color.RED, new Color(0,0,0,0)); 
        
        int textX = (popupWidth - textImage.getWidth()) / 2;
        int textY = 20; 
        background.drawImage(textImage, textX, textY);

        setImage(background);
    }

    /**
     * Dipanggil saat aktor ini ditambahkan ke dunia.
     */
    @Override
    protected void addedToWorld(World world)
    {
        if (world != null) {
            int buttonYOffset = 50; 
            int buttonXOffset = 70; 
            
            world.addObject(new RestartButton(), getX() - buttonXOffset, getY() + buttonYOffset);
            world.addObject(new HomeButton(), getX() + buttonXOffset, getY() + buttonYOffset);
        }
    }
    
    public void stopMusic() {
        if (gameOverMusic != null) {
            gameOverMusic.stop();
        }
    }
}