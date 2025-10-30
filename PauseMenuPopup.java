import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import greenfoot.Color;

/**
 * Aktor pop-up yang muncul saat game dipause via MenuButton.
 * Menampilkan tombol Restart dan Home.
 */
public class PauseMenuPopup extends Actor
{
    private RestartButton restartBtn; 
    private HomeButton homeBtn;       

    /**
     * Constructor untuk PauseMenuPopup.
     */
    public PauseMenuPopup()
    {
        int popupWidth = 180;  
        int popupHeight = 135; 
        GreenfootImage background = new GreenfootImage(popupWidth, popupHeight);
        background.setColor(new Color(50, 50, 50, 200));
        background.fill();
        background.setColor(Color.WHITE);
        background.drawRect(0, 0, popupWidth - 1, popupHeight - 1);

        GreenfootImage pausedText = new GreenfootImage("PAUSED", 24, Color.YELLOW, new Color(0,0,0,0));
        int textX = (popupWidth - pausedText.getWidth()) / 2;
        int textY = 15; 
        background.drawImage(pausedText, textX, textY);

        setImage(background);
    }

    /**
     * Dipanggil saat aktor ini ditambahkan ke dunia.
     * Menambahkan tombol Restart dan Home.
     */
    @Override
    protected void addedToWorld(World world)
    {
        if (world != null) {
            int popupHeight = getImage().getHeight(); 
            int topY = getY() - popupHeight / 2; 
            int textYInPopup = 15; 
            int spaceBelowText = 50; 
            int buttonSpacing = 35;  
            int btnYRestart = topY + textYInPopup + spaceBelowText;
            int btnYHome = btnYRestart + buttonSpacing;

            restartBtn = new RestartButton();
            world.addObject(restartBtn, getX(), btnYRestart); 
            homeBtn = new HomeButton();
            world.addObject(homeBtn, getX(), btnYHome); 
        }
    }
    
    /**
     * Method untuk menghapus pop-up ini DAN tombol-tombolnya.
     * Dipanggil oleh MenuButton atau tombol itu sendiri.
     */
    public void closePopup() {
        World world = getWorld();
        if (world == null) return;

        if (restartBtn != null && restartBtn.getWorld() != null) {
            world.removeObject(restartBtn);
        }
        if (homeBtn != null && homeBtn.getWorld() != null) {
            world.removeObject(homeBtn);
        }
        world.removeObject(this);
    }
    
    public void act() {
    }
}