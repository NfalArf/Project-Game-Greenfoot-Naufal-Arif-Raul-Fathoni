import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import greenfoot.Color;
import java.util.List;     
import java.util.ArrayList;  
import greenfoot.UserInfo; 

/**
 * Ini adalah "Home Page" atau layar menu utama.
 */

public class StartScreen extends World

{

    private GreenfootSound startMusic;

    public static int highScore = 0; 

    private List<Actor> highScoreDisplay = new ArrayList<>();

    /**
     * Constructor untuk StartScreen.
     */

    public StartScreen()

    {

        super(446, 560, 1); 

        
        GreenfootImage bg = getBackground();

        bg.setColor(Color.BLACK);

        bg.fill();

        

        loadHighScore();

        prepare();

        showHighScore();

        prepare();

        startMusic = new GreenfootSound("StartScreen.wav");
        startMusic.playLoop();
    }

    /**
     * Method 'act' - Mengecek input.
     */

    public void act()

    {
        if (Greenfoot.isKeyDown("enter") || Greenfoot.isKeyDown("space") || Greenfoot.mouseClicked(null))
        {
            if (startMusic != null) {
                startMusic.stop(); 
            }
            PacmanWorld gameWorld = new PacmanWorld();

            Greenfoot.setWorld(gameWorld);

        }
    }



    /**
     * Menyiapkan layar dengan menambahkan semua Aktor teks.
     */
    
    private void prepare()

    {
        addObject(new TextActor("text_1up.png"), 70, 20);
        addObject(new TextActor("text_high_score.png"), 223, 20);
        addObject(new TextActor("text_push_start.png"), 223, 350);

        TextActor superPacLogo = new TextActor("Logo.png"); 

        int logoX = getWidth() / 2;
        int logoY = 250; 

        addObject(superPacLogo, logoX, logoY);
    }
    
    private void loadHighScore() {
        if (UserInfo.isStorageAvailable()) {
        UserInfo myInfo = UserInfo.getMyInfo();
            if (myInfo != null) {
                highScore = myInfo.getScore(); 
            }
        }
    }

    private void showHighScore() {
        removeObjects(highScoreDisplay);

        highScoreDisplay.clear();

        TextActor highScoreLabel = new TextActor("text_high_score.png");
        int labelX = getWidth() / 2;
        int labelY = 20;

        addObject(highScoreLabel, labelX, labelY);
        highScoreDisplay.add(highScoreLabel);

        String scoreString = Integer.toString(highScore); 

        int digitY = labelY + 20;
        int digitSpacing = 2;
        int totalDigitWidth = 0;
        int sampleDigitWidth = 16; 
        try { 
            DigitActor sampleDigit = new DigitActor(0); 
            sampleDigitWidth = sampleDigit.getDigitWidth(); 
        } catch (Exception e) {  }
        totalDigitWidth = scoreString.length() * sampleDigitWidth + (scoreString.length() -1) * digitSpacing;
        int firstDigitX = labelX - (totalDigitWidth / 2); 
        int currentX = firstDigitX;
        for (int i = 0; i < scoreString.length(); i++) {
             int digitValue = Character.getNumericValue(scoreString.charAt(i));
             DigitActor digit = new DigitActor(digitValue);
             int digitWidth = digit.getDigitWidth();

             addObject(digit, currentX + (digitWidth/2), digitY);

             highScoreDisplay.add(digit);

             currentX += digitWidth + digitSpacing;
        }
    }

    public static int getHighScore() {
        return highScore; 
    }
}



