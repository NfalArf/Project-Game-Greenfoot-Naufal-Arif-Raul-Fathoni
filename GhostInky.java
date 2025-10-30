import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import java.util.List;
import java.util.Collections;

/**
 * Hantu Inky (Cyan).
 */
public class GhostInky extends Ghost
{
    /**
     * Constructor untuk Inky.
     */
    public GhostInky()
    {
        super("cyan"); 
        lastDX = speed; 
        lastDY = 0;
    }

    @Override
    protected boolean decideMove()
    {
        if (target == null) return false;

        int targetX = target.getX();
        int targetY = target.getY();
        int currentX = getX();
        int currentY = getY();

        double distanceToPacman = Math.sqrt(Math.pow(targetX - currentX, 2) + Math.pow(targetY - currentY, 2));

        int tileWidth = 8 * 2; 
        double closeDistance = tileWidth * 8; 

        if (distanceToPacman > closeDistance) {
            List<int[]> possibleMoves = getPossibleMoves();
            if (possibleMoves.isEmpty()) return false;

            if (possibleMoves.size() > 1) {
                possibleMoves.removeIf(move -> isReverse(move[0], move[1]));
                if (possibleMoves.isEmpty()) possibleMoves = getPossibleMoves();
                 if (possibleMoves.isEmpty()) return false;
            }

            Collections.shuffle(possibleMoves);
            int[] randomMove = possibleMoves.get(0);
            return tryMove(randomMove[0], randomMove[1]);
        }
        else {
            return chaseTowards(targetX, targetY);
        }
    }
}