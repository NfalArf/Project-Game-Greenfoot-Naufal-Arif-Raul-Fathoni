import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import java.util.List;
import java.util.Collections;

/**
 * Hantu Blinky (Merah).
 */
public class GhostBlinky extends Ghost
{
    /**
     * Constructor untuk Blinky.
     */
    public GhostBlinky()
    {
        super("red");
        
        lastDX = speed; 
        lastDY = 0;
    }

    /**
     * Logika Blinky: Pilih arah terdekat ke target.
     */
    @Override
    protected boolean decideMove()
    {
        if (target == null) return false;
        
        return chaseTowards(target.getX(), target.getY());
}
}