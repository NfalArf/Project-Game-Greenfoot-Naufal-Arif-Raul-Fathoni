import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot)
import greenfoot.Color;

/**
 * Class dasar untuk tombol yang bisa diklik.
 * Menampilkan teks dan bereaksi terhadap mouse.
 */
public class Button extends Actor
{
    private String text;
    private int fontSize = 24; 
    private Color textColor = Color.YELLOW; 
    private Color bgColor = new Color(0, 0, 0, 0); 
    private Color hoverColor = Color.ORANGE; 
    private boolean mouseOver = false; 

    /**
     * Constructor untuk Button.
     */
    public Button(String buttonText)
    {
        this.text = buttonText;
        updateImage(); 
    }

    /**
     * Membuat atau memperbarui gambar tampilan tombol.
     */
    private void updateImage()
    {
        Color currentTextColor = mouseOver ? hoverColor : textColor;
        
        GreenfootImage img = new GreenfootImage(text, fontSize, currentTextColor, bgColor);
        setImage(img); 
    }

    /**
     * Method act() - Dijalankan terus menerus.
     * Cek posisi mouse dan klik.
     */
    public void act()
    {        
        if (!mouseOver && Greenfoot.mouseMoved(this))
        {
            mouseOver = true;
            updateImage(); 
        }
    
        if (mouseOver && Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this))
        {
            mouseOver = false;
            updateImage(); 
        }

        if (Greenfoot.mouseClicked(this))
        {
            onClick(); 
        }
    }

    /**
     * Aksi yang dilakukan saat tombol diklik.
     */
    public void onClick()
    {
       
    }
}