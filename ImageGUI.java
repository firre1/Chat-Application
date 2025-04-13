import javax.swing.*;
import java.awt.*;
/*
Denna klass är ett Swing-grafiskt användargränssnitt (GUI) för att visa en bild.
Bilden laddas från en fil och läggs sedan till i en JLabel som sedan läggs
till i en JPanel. Denna klass tillhandahåller också en metod
för att uppdatera den visade bilden.
 */
public class ImageGUI {

    /*
     Denna metod skapar ett ImageIcon-objekt från en fil som finns på en angiven sökväg.
     Sedan skalas bilden till den angivna bredden och höjden med hjälp av metoden getScaledInstance.
     Den skalade bilden returneras som en ImageIcon.
     */
    public static ImageIcon scaleImage(String filepath, int w, int h) {
        ImageIcon temp = new ImageIcon(filepath);
        Image img = temp.getImage();
        Image scaled = img.getScaledInstance(w,h, Image.SCALE_SMOOTH);
        ImageIcon scaledPic = new ImageIcon(scaled);
        return scaledPic;
    }
    //filepath med parametrarna width o height o skapar en imageicon objekt av den filen, använder GetScaledInstance för att specifiera width o height korrrekt
    //skapar en ny imageicon av de objektet o returnerar det sedan som en imageicon.
    /*
     Liknande som ovanstående metod, tar denna metod ett Image-objekt som inmatning och
     skalas till den angivna bredden och höjden med hjälp av metoden getScaledInstance.
     Den skalade bilden returneras som ett Image-objekt.
     */
    public static Image scaleImage(Image image, int w, int h) {
        return image.getScaledInstance(w,h,Image.SCALE_SMOOTH);
    }
    //tar en imageobject width o height parametrar o skalar input images efter den specifika width o height genom o använda GetScaledInstance metoden o får tbx den som en Image objekt
    /*
    Denna metod kontrollerar om en fil som finns på den angivna sökvägen är en bildfil.
    Den kontrollerar om filen har en av följande filändelser: .jpg, .png, .jpeg eller .gif.
    Om filen har en av dessa filändelser returnerar metoden true, annars returnerar den false.
     */
    public static boolean isImage(String filepath) {
        return filepath.endsWith(".jpg") || filepath.endsWith(".png") || filepath.endsWith(".jpeg") || filepath.endsWith(".gif");
    }
    //filepath, hämtar o kollar ifall filen är supported dvs antingen .jpg eller .png sedan får man true eller false.
    /*
    Denna metod visar en bild tillsammans med lite text i en JOptionPane meddelanderuta.
    Titeln på rutan är "Bild från: " följt av avsändarens namn. Texten och bilden visas i rutan.
     */
    public static void displayImage(String sender, String text, ImageIcon image) {
        JOptionPane.showMessageDialog(null, text, "Bild från: " + sender, JOptionPane.INFORMATION_MESSAGE, image);
    }
    //visar en bild genom att använda message delen för bilder, visar sedan namn o text samt ImageIcon dvs bilden som en parameter
    /*
    Denna metod skapar en JLabel med en specificerad ImageIcon och text.
    ImageIcon sätts som etikettens ikon och namnet sätts som etikettens text.
    Den skapade JLabel returneras sedan.
     */
    public static JLabel createUserLabel(ImageIcon icon, String name) {
        JLabel lbl = new JLabel(icon);
        lbl.setText(name);
        return lbl;
    }
}
//skapar en Jlabel objekt me en imageicon, lägger Icon som bilden o name som en string dvs text o så returnerar den en skapad label.

