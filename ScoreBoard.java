import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import java.util.List;
import java.util.ArrayList;

/**
 * Aktor ScoreBoard. Mengatur huruf "SCORE " dan DigitActor.
 * Posisi di kiri bawah.
 */
public class ScoreBoard extends Actor
{
    private final int LABEL_X = 60; 
    private final int X_START = 10;
    private final int Y_POS = 540;  
    private final int DIGIT_X_START = 120; 
    private final int LETTER_SPACING = 1; 
    private final int SCORE_LABEL_TO_DIGIT_SPACING = 5; 
    private final int DIGIT_SPACING = 2;
    private List<Actor> scoreDisplayActors = new ArrayList<>(); 

    /**
     * Constructor
     */
    public ScoreBoard()
    {
        setImage(new GreenfootImage(1, 1));
        getImage().setTransparency(0);
    }

    @Override
    protected void addedToWorld(World world)
    {
        if (world != null) {
             updateScore(0); 
        }
    }

    /**
     * Memperbarui tampilan skor (huruf + digit).
     */
    public void updateScore(int newScore)
    {
        World world = getWorld();
        if (world == null) return;

        world.removeObjects(scoreDisplayActors);
        scoreDisplayActors.clear();

        String label = "SCORE"; 
        int currentX = X_START;
        int lastActorWidth = 0; 

        for (int i = 0; i < label.length(); i++)
        {
            char letterChar = label.charAt(i);
            String filename = "char_" + Character.toLowerCase(letterChar) + ".png";
            TextActor letterActor = new TextActor(filename);

            lastActorWidth = letterActor.getImage().getWidth(); 
            world.addObject(letterActor, currentX + (lastActorWidth / 2), Y_POS);
            scoreDisplayActors.add(letterActor); 
            currentX += lastActorWidth + LETTER_SPACING;
        }

        currentX += SCORE_LABEL_TO_DIGIT_SPACING; 
        String scoreString = Integer.toString(newScore); 
        int digitWidth = 0;

        for (int i = 0; i < scoreString.length(); i++) 
        {
            int digitValue = Character.getNumericValue(scoreString.charAt(i));
            DigitActor digit = new DigitActor(digitValue); 
            digitWidth = digit.getDigitWidth();

            world.addObject(digit, currentX + (digitWidth / 2), Y_POS);
            scoreDisplayActors.add(digit); 

            currentX += digitWidth + DIGIT_SPACING;
        }
    }
}