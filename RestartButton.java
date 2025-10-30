import greenfoot.*;
import java.util.List;

public class RestartButton extends Button
{
    public RestartButton() {
        super("Restart"); 
    }

    /**
     * Method act() mendeteksi klik.
     */
    @Override 
    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            World world = getWorld();
            if (world != null && world instanceof PacmanWorld) {
                List<PauseMenuPopup> popups = world.getObjects(PauseMenuPopup.class);
                if (!popups.isEmpty()) {
                    popups.get(0).closePopup();
                }
            
                ((PacmanWorld)world).restartGame(); 
            }
        }
    }

    @Override
    public void onClick() {

    }
}