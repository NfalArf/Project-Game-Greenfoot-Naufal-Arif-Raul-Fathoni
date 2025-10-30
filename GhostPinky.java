import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import java.util.List;
import java.util.Collections;

/**
 * Hantu Pinky (Pink).
 */
public class GhostPinky extends Ghost
{
    /**
     * Constructor untuk Pinky.
     */
    public GhostPinky()
    {
        super("pink"); 
        lastDX = -speed; 
        lastDY = 0;
    }

    @Override
    protected boolean decideMove()
    {
        if (target == null) return false;

        int targetX = target.getX();
        int targetY = target.getY();
        int pacmanDX = 0;
        int pacmanDY = 0;

        if (target instanceof Pacman) {
             Pacman pacmanTarget = (Pacman) target;
             pacmanDX = pacmanTarget.getPacmanDX();
             pacmanDY = pacmanTarget.getPacmanDY();
        }
    
        if(pacmanDX == 0 && pacmanDY == 0) {
             pacmanDX = lastDX;
             pacmanDY = lastDY;
            
             if(pacmanDX == 0 && pacmanDY == 0) pacmanDX = speed;
        }

        int tileWidth = 8 * 2; 
        int offset = tileWidth * 4; 
        int finalTargetX = targetX + (pacmanDX / speed * offset); 
        int finalTargetY = targetY + (pacmanDY / speed * offset); 

        if (pacmanDY < 0) { 
            finalTargetX -= offset;
        }

        return chaseTowards(finalTargetX, finalTargetY);
    }
}