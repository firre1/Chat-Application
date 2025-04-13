import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
/*
Denna klass representerar ett meddelande i en chattapplikation.
Den håller information om meddelandet, som avsändaren (User-objekt),
meddelandeinnehållet (String) och mottagaren (User-objekt).
Den håller också reda på om meddelandet har skickats eller inte.
 */
public class Message implements Serializable {//Serializable så att info går at föras över med sockets
    private User sender; //Detta är en instans av klassen User som representerar avsändaren av meddelandet.
    private ArrayList<User> receivers;//Detta är en ArrayList med instanser av klassen User som representerar mottagarna av meddelandet
    private String text;//Textinnehållet i meddelandet
    private ImageIcon image; //Bilden i meddelandet (valfri enl instrukitoner)
    private Date received;//Tidpunkt då meddelandet mottogs
    private Date delivered;//Tidpunkt då meddelandet levererades till mottagaren

    public Message(User sender, ArrayList<User> receivers, String text, ImageIcon image) {//Klassens konstruktor
        this.sender = sender;
        this.receivers = receivers;
        this.text = text;
        this.image = image;
    }

    public User getSender() {
        return sender;
    } //Denna metod returnerar avsändaren av meddelandet.

    public ArrayList<User> getReceivers() {
        return receivers;
    } //Denna metod returnerar mottagarna av meddelandet.
    public void setReceived() {
        this.received = new Date();
    } //Denna metod sätter received-attributet till aktuellt datum och tid.

    public void setDelivered() {
        this.delivered = new Date();
    }
    //Denna metod sätter delivered-attributet till aktuellt datum och tid.

    public Date getDelivered() {
        return delivered;
    } //Denna metod returnerar tiden då meddelandet levererades.

    public ImageIcon getImage() {
        return image;
    } //Denna metod returnerar bilden i meddelandet.

    public String getText() {
        return text;
    } //Denna metod returnerar texten i meddelandet
}