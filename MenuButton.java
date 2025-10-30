import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)

/**
 * Tombol menu (garis tiga).
 * Saat diklik: Pause game, tampilkan tombol Restart/Home.
 * Saat diklik lagi: Resume game, sembunyikan tombol.
 */
public class MenuButton extends Actor
{
    private boolean menuOpen = false;
    private RestartButton restartBtn;
    private HomeButton homeBtn;
    private PauseMenuPopup pausePopup; 

    /**
     * Constructor
     */
    public MenuButton()
    {
        setImage("MenuBotton.png"); 
    }

    /**
     * Method act() - Mengecek klik mouse.
     */
    public void act()
    {
            if (Greenfoot.mouseClicked(this))
            {
                menuOpen = !menuOpen; 
                toggleMenu();         
            }
        }
    

    /**
     * Menampilkan/menyembunyikan tombol & Pause/Resume game.
     */
   private void toggleMenu()
    {
        World world = getWorld();
        if (world == null || !(world instanceof PacmanWorld)) return;
        PacmanWorld pw = (PacmanWorld) world;

        if (menuOpen) {
            pw.pauseGame(); 

            pausePopup = new PauseMenuPopup();
            world.addObject(pausePopup, world.getWidth() / 2, world.getHeight() / 2);

        } else {
            if (pausePopup != null && pausePopup.getWorld() != null) {
                 pausePopup.closePopup();
            }
            pausePopup = null;

            pw.resumeGame(); 
        }
    }

    /**
     * Method ini bisa dipanggil dari luar (misal: saat Game Over)
     * untuk memastikan menu tertutup dan game berhenti.
     */
    public void closeMenuAndStop() {
         World world = getWorld();
         if (world == null) return;

         if (menuOpen) {
             if (restartBtn != null && restartBtn.getWorld() != null) world.removeObject(restartBtn);
             if (homeBtn != null && homeBtn.getWorld() != null) world.removeObject(homeBtn);
             restartBtn = null;
             homeBtn = null;
             menuOpen = false;
         }
         Greenfoot.stop(); 
    }
}