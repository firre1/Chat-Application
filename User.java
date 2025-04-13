import javax.swing.*;
import java.io.Serializable;
/*
Denna klass representerar en användare i en chattapplikation.
Den håller information om användaren, som användarnamn, lösenord och online-status.
Den har också metoder för att ändra användarens online-status och lösenord.
 */
public class User implements Serializable {// Klassen är märkt som Serializable för att möjliggöra att dess instanser serialiseras och skickas över nätverket eller sparas i en fil.
    private String username; //Detta är det privata fältet som lagrar användarnamnet för användaren.
    private ImageIcon image; // Detta är det privata fältet som lagrar profilbilden för användaren som en ImageIcon.
    private boolean isConnected; // Detta är det privata fältet som indikerar om användaren är ansluten till servern eller inte.

    public User(String username, ImageIcon image) {
        this.username = username;
        this.image = image;
    }
    //Detta är konstruktorn för User-klassen. Den tar emot användarnamnet och profilbilden som parametrar och skapar en ny användarinstans med dessa värden.

    public String getUsername() {
        return username;
    }
    //Denna metod används för att hämta användarnamnet för en användare.

    public ImageIcon getImage() {
        return image;
    }
    //Denna metod används för att hämta profilbilden för en användare som en ImageIcon.

    public int HaschCode() {
        return username.hashCode();
    }
    //Denna metod returnerar en hashkod för användarnamnet. Detta är användbart när man vill använda en User-instans som nyckel i en hashtabell eller liknande datastrukturer.
    public boolean isConnected() {
        return isConnected;
    }
    //Denna metod returnerar true om användaren är ansluten till servern, annars false.

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
    // Denna metod används för att ställa in anslutningsstatusen för användaren.

    public boolean equals(Object obj) {
        if(obj != null && obj instanceof User) {
            return username.equals(((User)obj).getUsername());
        }
        return false;
    }
}
//Denna metod jämför en User-instans med en annan objektinstans. Den returnerar true om objektet är en User-instans med samma användarnamn, annars false.