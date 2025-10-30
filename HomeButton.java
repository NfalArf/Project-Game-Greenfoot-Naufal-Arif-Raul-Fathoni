import greenfoot.*;
import java.util.List;

public class HomeButton extends Button
{
    public HomeButton() {
        super("Home"); 
    }

    /**
     * Method act() SEKARANG mendeteksi klik.
     */
    @Override 
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            World world = getWorld();
            if (world != null) {
                List<PauseMenuPopup> popups = world.getObjects(PauseMenuPopup.class);
                if (!popups.isEmpty()) {
                    popups.get(0).closePopup();
                }
             
                if (world instanceof PacmanWorld) {
                     ((PacmanWorld)world).goHome();
                } else {
                     Greenfoot.setWorld(new StartScreen());
                }
            }
        }
    }

    @Override
    public void onClick() {
     
    }
}