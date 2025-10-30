import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import greenfoot.Color;
import java.util.ArrayList; 

/**
 * Aktor Pacman.
 */
public class Pacman extends Actor
{
    private int speed = 2;
    private int animationCounter = 0;
    private int animationSpeed = 5;
    private int pacmanSize = 26;
    private int lastPacmanDX = speed;
    private int lastPacmanDY = 0;
    private GreenfootImage[] imagesRight, imagesLeft, imagesUp, imagesDown;
    private GreenfootImage[] currentImages;
    private int currentFrame = 0;
    private ArrayList<GreenfootImage> deathImages;
    private boolean isDying = false;
    private int deathFrameCounter = 0;
    private int currentDeathFrame = 0;
    private int deathAnimationSpeed = 10;
    private int totalDeathFrames = 10;
    
    /** Constructor */
    public Pacman()
    {
        initializeImages();
        currentImages = imagesRight;
        if (currentImages != null && currentImages.length > currentFrame) {
             setImage(currentImages[currentFrame]);
        } else {
             System.err.println("Error: Gambar awal Pacman tidak bisa di-set!");
        }
    }

    /** Memuat SEMUA gambar */
    private void initializeImages()
    {
        try {
            imagesRight = new GreenfootImage[2];
            imagesRight[0] = new GreenfootImage("pacman_right_half.png");
            imagesRight[1] = new GreenfootImage("pacman_right_open.png");
            imagesRight[0].scale(pacmanSize, pacmanSize);
            imagesRight[1].scale(pacmanSize, pacmanSize);

            imagesLeft = new GreenfootImage[2];
            imagesLeft[0] = new GreenfootImage("pacman_left_half.png");
            imagesLeft[1] = new GreenfootImage("pacman_left_open.png");
            imagesLeft[0].scale(pacmanSize, pacmanSize);
            imagesLeft[1].scale(pacmanSize, pacmanSize);

            imagesUp = new GreenfootImage[2];
            imagesUp[0] = new GreenfootImage("pacman_up_half.png");
            imagesUp[1] = new GreenfootImage("pacman_up_open.png");
            imagesUp[0].scale(pacmanSize, pacmanSize);
            imagesUp[1].scale(pacmanSize, pacmanSize);

            imagesDown = new GreenfootImage[2];
            imagesDown[0] = new GreenfootImage("pacman_down_half.png");
            imagesDown[1] = new GreenfootImage("pacman_down_open.png");
            imagesDown[0].scale(pacmanSize, pacmanSize);
            imagesDown[1].scale(pacmanSize, pacmanSize);

        } catch (IllegalArgumentException e) {
             System.err.println("Error memuat gambar gerak Pacman: " + e.getMessage());
             imagesRight = imagesLeft = imagesUp = imagesDown = null;
        }

        deathImages = new ArrayList<GreenfootImage>();
        int totalDeathFrames = 1;
        for (int i = 1; i <= totalDeathFrames; i++) {
            String filename = String.format("pacman_death.png", i); 
             try {
                GreenfootImage img = new GreenfootImage(filename);
                img.scale(pacmanSize, pacmanSize);
                deathImages.add(img);
             } catch (IllegalArgumentException e) {
                 System.err.println("Error memuat gambar kematian Pacman " + filename + ": " + e.getMessage());
                 break;
             }
        }
        if (deathImages.size() < totalDeathFrames) {
            System.err.println("Peringatan: Animasi kematian Pacman tidak lengkap! Hanya " + deathImages.size() + "/" + totalDeathFrames + " frame ditemukan.");
            deathImages = null; 
        }
    }

    /** Method act() - Logika utama */
    public void act()
    {
        PacmanWorld world = (PacmanWorld) getWorld();
        
        if (world != null && world.isPaused()) {
            return; 
        }
        if (isDying) {
            animateDeath();
            return;
        }
        if (checkGhostCollision()) {
             return;
        }
        handleMovement();
        checkWrapAround();
        checkEating();
    }

    /** Mengatur logika gerakan & simpan arah */
    private void handleMovement() {
        boolean keyWasPressed = false;
        int currentX = getX();
        int currentY = getY();
        int tryDX = 0;
        int tryDY = 0;
        GreenfootImage[] targetImages = currentImages;

        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            targetImages = imagesRight; tryDX = speed; tryDY = 0; keyWasPressed = true;
        } else if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
            targetImages = imagesLeft; tryDX = -speed; tryDY = 0; keyWasPressed = true;
        } else if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("w")) {
            targetImages = imagesUp; tryDX = 0; tryDY = -speed; keyWasPressed = true;
        } else if (Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("s")) {
            targetImages = imagesDown; tryDX = 0; tryDY = speed; keyWasPressed = true;
        }

        if (keyWasPressed) {
            setDirection(targetImages);
            if (canMove(tryDX, tryDY)) {
                setLocation(currentX + tryDX, currentY + tryDY);
                lastPacmanDX = tryDX; lastPacmanDY = tryDY;
                animate();
            } else if (canMove(lastPacmanDX, lastPacmanDY)) {
                setLocation(getX() + lastPacmanDX, getY() + lastPacmanDY);
                animate();
            } else {
                 resetAnimation();
            }
        } else {
             if (canMove(lastPacmanDX, lastPacmanDY)) {
                 setLocation(getX() + lastPacmanDX, getY() + lastPacmanDY);
                 animate();
             } else {
                  resetAnimation();
             }
        }
    }

    /** Mengatur arah gambar */
    private void setDirection(GreenfootImage[] images) {
         if (currentImages != images && images != null)
        {
            currentImages = images;
            currentFrame = 0;
            if (currentImages.length > currentFrame) {
                setImage(currentImages[currentFrame]);
            }
        }
    }

    /** Menganimasikan gambar normal */
    private void animate() {
        animationCounter++;
        if (animationCounter >= animationSpeed)
        {
            animationCounter = 0;
            currentFrame = (currentFrame + 1) % 2;
             if(currentImages != null && currentImages.length > currentFrame) {
                setImage(currentImages[currentFrame]);
             }
        }
    }

     /** Mereset animasi ke frame pertama */
    private void resetAnimation() {
        animationCounter = 0;
        currentFrame = 0;
        if(currentImages != null && currentImages.length > 0){
            setImage(currentImages[0]);
        }
    }

    /**
     * Mengecek dinding.
     */
    private boolean canMove(int dx, int dy) {
        if (dx == 0 && dy == 0) return true;

        int currentPacmanSize = getImage().getWidth();
        int halfSize = currentPacmanSize / 2;
        Actor wall1 = null, wall2 = null, wall3 = null;

         if (dx > 0) { 
            wall1 = getOneObjectAtOffset(halfSize + dx, 0, Wall.class);
            wall2 = getOneObjectAtOffset(halfSize + dx, -halfSize, Wall.class);
            wall3 = getOneObjectAtOffset(halfSize + dx, halfSize, Wall.class);
        } else if (dx < 0) { 
            wall1 = getOneObjectAtOffset(-halfSize + dx, 0, Wall.class);
            wall2 = getOneObjectAtOffset(-halfSize + dx, -halfSize, Wall.class);
            wall3 = getOneObjectAtOffset(-halfSize + dx, halfSize, Wall.class);
        } else if (dy < 0) { 
            wall1 = getOneObjectAtOffset(0, -halfSize + dy, Wall.class);
            wall2 = getOneObjectAtOffset(-halfSize, -halfSize + dy, Wall.class);
            wall3 = getOneObjectAtOffset(halfSize, -halfSize + dy, Wall.class);
        } else { 
            wall1 = getOneObjectAtOffset(0, halfSize + dy, Wall.class);
            wall2 = getOneObjectAtOffset(-halfSize, halfSize + dy, Wall.class);
            wall3 = getOneObjectAtOffset(halfSize, halfSize + dy, Wall.class);
        }
        return wall1 == null && wall2 == null && wall3 == null; 
    }

    /** Mengecek terowongan */
    private void checkWrapAround() {
        int worldWidth = getWorld().getWidth();
        if (getX() >= worldWidth - 1) {
            setLocation(0, getY());
        } else if (getX() <= 0) {
            setLocation(worldWidth - 1, getY());
        }
    }

    /** Mengecek makan pellet/power pellet */
  private void checkEating()
    {
        Actor pellet = getOneIntersectingObject(Pellet.class);
        if (pellet != null)
        {
            if ( ((Pellet)pellet).eat() ) 
            {
                PacmanWorld world = (PacmanWorld) getWorld();
                world.addScore(10);
                Greenfoot.playSound("Pellet.wav");
            }
        }

        Actor powerPellet = getOneIntersectingObject(PowerPellet.class);
        if (powerPellet != null)
        {
            if ( ((PowerPellet)powerPellet).eat() ) 
            {
                PacmanWorld world = (PacmanWorld) getWorld();
                world.addScore(50);
                Greenfoot.playSound("Power Pellet.wav");
            }
        }
        
        if (isTouching(Fruit.class))
        {
            removeTouching(Fruit.class);
            PacmanWorld world = (PacmanWorld) getWorld();
            world.addScore(100);
            
            Greenfoot.playSound("Fruit.wav");
        }
    }

    /** Mengembalikan perubahan X terakhir */
    public int getPacmanDX() {
        return lastPacmanDX; 
    }

    /** Mengembalikan perubahan Y terakhir */
    public int getPacmanDY() {
        return lastPacmanDY; 
    }

    /** Mengecek tabrakan dengan Hantu */
    private boolean checkGhostCollision() {
    Actor collidingGhost = getOneIntersectingObject(Ghost.class);
    if (collidingGhost != null && !isDying) {
        isDying = true; 

        Greenfoot.playSound("Death.wav");

        deathFrameCounter = 0; 
        currentDeathFrame = 0; 

        if (deathImages != null && !deathImages.isEmpty()) {
            setImage(deathImages.get(currentDeathFrame));
        } else {
            handleDeathFinished();
        }
        return true; 
    }
    return false; 
}

    /** Menjalankan animasi kematian */
    private void animateDeath() {
        if (deathImages == null || deathImages.isEmpty()) {
            handleDeathFinished();
            return;
        }
        deathFrameCounter++;
        if (deathFrameCounter >= deathAnimationSpeed) {
            deathFrameCounter = 0;
            currentDeathFrame++;
            if (currentDeathFrame < deathImages.size()) {
                setImage(deathImages.get(currentDeathFrame));
            } else {
                handleDeathFinished();
            }
        }
    }

    /** Dipanggil setelah animasi kematian selesai */
    private void handleDeathFinished() {
        PacmanWorld world = (PacmanWorld) getWorld();
        if (world != null) {
            world.pacmanDied(); 
        }
        if (getWorld() != null) {
            getWorld().removeObject(this); 
        }
    }
} 