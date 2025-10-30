import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class dasar ABSTRAK untuk semua hantu.
 * Berisi logika umum seperti animasi, deteksi dinding, dan variabel dasar.
 */
public abstract class Ghost extends Actor
{
    protected int speed = 1;
    protected int animationCounter = 0;
    protected int animationSpeed = 10;
    protected int ghostSize = 26; 

    protected GreenfootImage[] imagesRight;
    protected GreenfootImage[] imagesLeft;
    protected GreenfootImage[] imagesUp;
    protected GreenfootImage[] imagesDown;

    protected GreenfootImage[] currentImages;
    protected int currentFrame = 0;          
    protected Actor target;                 
    protected int lastDX = 0;            
    protected int lastDY = 0;                

    /**
     * Constructor dasar 
     */
    public Ghost(String color)
    {
        initializeImages(color); 
        
        currentImages = imagesRight; 
        if (currentImages != null && currentImages.length > 0) {
            setImage(currentImages[0]);
        }
    }

    /**
     * Dipanggil saat ditambahkan ke dunia (sama untuk semua hantu).
     */
    @Override
    protected void addedToWorld(World world) {
        List<Pacman> pacmans = world.getObjects(Pacman.class);
        if (!pacmans.isEmpty()) {
            target = pacmans.get(0);
        } else {
             System.out.println("PERINGATAN: " + getClass().getName() + " tidak menemukan Pacman!");
        }
    }

    /**
     * Memuat gambar animasi untuk warna hantu yang spesifik.
     */
    protected void initializeImages(String color)
    {
        try {
            imagesRight = new GreenfootImage[2];
            imagesRight[0] = new GreenfootImage("ghost_" + color + "_right1.png");
            imagesRight[1] = new GreenfootImage("ghost_" + color + "_right2.png");
            imagesRight[0].scale(ghostSize, ghostSize);
            imagesRight[1].scale(ghostSize, ghostSize);

            imagesLeft = new GreenfootImage[2];
            imagesLeft[0] = new GreenfootImage("ghost_" + color + "_left1.png");
            imagesLeft[1] = new GreenfootImage("ghost_" + color + "_left2.png");
            imagesLeft[0].scale(ghostSize, ghostSize);
            imagesLeft[1].scale(ghostSize, ghostSize);

            imagesUp = new GreenfootImage[2];
            imagesUp[0] = new GreenfootImage("ghost_" + color + "_up1.png");
            imagesUp[1] = new GreenfootImage("ghost_" + color + "_up2.png");
            imagesUp[0].scale(ghostSize, ghostSize);
            imagesUp[1].scale(ghostSize, ghostSize);

            imagesDown = new GreenfootImage[2];
            imagesDown[0] = new GreenfootImage("ghost_" + color + "_down1.png");
            imagesDown[1] = new GreenfootImage("ghost_" + color + "_down2.png");
            imagesDown[0].scale(ghostSize, ghostSize);
            imagesDown[1].scale(ghostSize, ghostSize);
        } catch (IllegalArgumentException e) {
             System.err.println("Error memuat gambar untuk hantu " + color + ": " + e.getMessage());
     
             imagesRight = imagesLeft = imagesUp = imagesDown = null;
        }
    }

    /**
     * Method act()
     */
    public void act()
    {
        PacmanWorld world = (PacmanWorld) getWorld();
        if (world != null && world.isPaused()) {
            return; 
        }
        
    
        if (target == null) return; 

    
        if (target.getWorld() == null) {
            return;
        }

        boolean moved = decideMove(); 

        if (moved) {
            animate(); 
        } else {
             animationCounter = 0;
             if(currentImages != null && currentImages.length > 0) {
                setImage(currentImages[0]);
             }
        }
    }

    /**
     * METHOD ABSTRAK
     */
    protected abstract boolean decideMove();
    
    protected List<int[]> getPossibleMoves() {
        List<int[]> moves = new ArrayList<>();
        if (canMove(speed, 0)) moves.add(new int[]{speed, 0}); // Kanan
        if (canMove(-speed, 0)) moves.add(new int[]{-speed, 0}); // Kiri
        if (canMove(0, speed)) moves.add(new int[]{0, speed});   // Bawah
        if (canMove(0, -speed)) moves.add(new int[]{0, -speed}); // Atas
        return moves;
    }

    /** Mengecek arah kebalikan */
    protected boolean isReverse(int dx, int dy) {
        return dx == -lastDX && dy == -lastDY;
    }

    /** Mencoba bergerak & simpan arah */
    protected boolean tryMove(int tryDX, int tryDY) {
        int directionIndex = -1;
        if (tryDX > 0) directionIndex = 0;      
        else if (tryDX < 0) directionIndex = 1; 
        else if (tryDY < 0) directionIndex = 2; 
        else if (tryDY > 0) directionIndex = 3; 
        
        GreenfootImage[] images = null;
        if (directionIndex == 0) images = imagesRight;
        else if (directionIndex == 1) images = imagesLeft;
        else if (directionIndex == 2) images = imagesUp;
        else if (directionIndex == 3) images = imagesDown;

        if (images != null && canMove(tryDX, tryDY)) {
             setDirection(images); 
             setLocation(getX() + tryDX, getY() + tryDY); 
             lastDX = tryDX; 
             lastDY = tryDY; 
             return true; 
        }
        return false; 
    }

    /** Mengatur arah gambar (hanya jika arah berubah) */
    protected void setDirection(GreenfootImage[] images) {
         if (currentImages != images) 
        {
            currentImages = images;
            currentFrame = 0;
            if (currentImages != null && currentImages.length > currentFrame) {
                setImage(currentImages[currentFrame]);
            }
        }
    }

    /** Menganimasikan gambar */
    protected void animate() {
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

    /**
     * Mengecek apakah Hantu bisa bergerak ke offset (dx, dy).
     * Mengecek Wall dan Hantu Lain.
     */
    protected boolean canMove(int dx, int dy) {
        int halfSize = ghostSize / 2;
        Actor wall1 = null, wall2 = null, wall3 = null;
        Actor ghost1 = null, ghost2 = null, ghost3 = null; 

        int x1=0, y1=0, x2=0, y2=0, x3=0, y3=0;
        if (dx > 0) {  } else if (dx < 0) {  } else if (dy < 0) {  } else if (dy > 0) {  } else { return true; } 
        
         if (dx > 0) { x1=halfSize+dx; y1=0; x2=halfSize+dx; y2=-halfSize; x3=halfSize+dx; y3=halfSize; }
         else if (dx < 0) { x1=-halfSize+dx; y1=0; x2=-halfSize+dx; y2=-halfSize; x3=-halfSize+dx; y3=halfSize; }
         else if (dy < 0) { x1=0; y1=-halfSize+dy; x2=-halfSize; y2=-halfSize+dy; x3=halfSize; y3=-halfSize+dy; }
         else { x1=0; y1=halfSize+dy; x2=-halfSize; y2=halfSize+dy; x3=halfSize; y3=halfSize+dy; }

        wall1 = getOneObjectAtOffset(x1, y1, Wall.class);
        wall2 = getOneObjectAtOffset(x2, y2, Wall.class);
        wall3 = getOneObjectAtOffset(x3, y3, Wall.class);

        if (wall1 != null || wall2 != null || wall3 != null) {
            return false;
        }

        ghost1 = getOneObjectAtOffset(dx, dy, Ghost.class); 
        if (ghost1 != null && ghost1 != this) { 
             return false; 
        }
        return true;
    }

    protected boolean chaseTowards(int targetX, int targetY) {
        int currentX = getX();
        int currentY = getY();

        List<int[]> possibleMoves = getPossibleMoves();
        if (possibleMoves.isEmpty()) return false; 

        if (possibleMoves.size() > 1) { 
            possibleMoves.removeIf(move -> isReverse(move[0], move[1])); 
            if (possibleMoves.isEmpty()) possibleMoves = getPossibleMoves(); 
             if (possibleMoves.isEmpty()) return false; 
        }

        int bestDX = 0, bestDY = 0;
        double minDistance = Double.MAX_VALUE;
        Collections.shuffle(possibleMoves); 

        for (int[] move : possibleMoves) {
            int dx = move[0];
            int dy = move[1];
            int nextX = currentX + dx;
            int nextY = currentY + dy;
     
            double distanceSq = Math.pow(targetX - nextX, 2) + Math.pow(targetY - nextY, 2);

            if (distanceSq < minDistance) {
                minDistance = distanceSq;
                bestDX = dx;
                bestDY = dy;
            }
        }

        if (bestDX == 0 && bestDY == 0 && !possibleMoves.isEmpty()) {
             bestDX = possibleMoves.get(0)[0]; 
             bestDY = possibleMoves.get(0)[1];
        }

        if (bestDX == 0 && bestDY == 0) return false;

        return tryMove(bestDX, bestDY);
    }
} 