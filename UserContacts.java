import java.io.*;
import java.util.ArrayList;
/*
Denna klass hanterar en användares kontakter.
Den håller två listor: en för online-kontakter och en för sparade kontakter.
Den tillhandahåller metoder för att lägga till eller ta bort kontakter,
samt för att spara och ladda kontakter från en fil.
 */
public class UserContacts {
    private ArrayList<User> onlineKontakter;
    private ArrayList<User> savedKontakter;

    public UserContacts() {
        onlineKontakter = new ArrayList<>(); //En ArrayList av User-objekt som representerar de online-kontakterna.
        savedKontakter = new ArrayList<>(); //En ArrayList av User-objekt som representerar de sparade kontakterna.
    }
    public void Online(User user) { // Lägger till en User i onlineKontakter om de inte redan finns där.
        if(!onlineKontakter.contains(user)) {
            onlineKontakter.add(user);
        }
    }
    public void savedContact(User user) { //Lägger till en User i savedKontakter om de inte redan finns där.
        if(!savedKontakter.contains(user)) {
            savedKontakter.add(user);
        }
    }
    public void removeContactOnline(User user) { //Tar bort en User från onlineKontakter om de finns där.
        ArrayList<User> contactsToRemove = new ArrayList<>();
        for (User u : onlineKontakter) {
            if (u.equals(user)) {
                contactsToRemove.add(u);
            }
        }
        onlineKontakter.removeAll(contactsToRemove);
    }
    public void removeContact(int index) {
        savedKontakter.remove(index);
    }//Tar bort en sparad kontakt från listan baserat på angivet index.
    public void addToFile(String filename) {// Skriver User-objekten(sparade kontakter) i savedKontakter till en fil som specifieras av Filnamn mha objectoutputstream
        try(ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            out.writeInt(savedKontakter.size());
            for (User u: savedKontakter) {
                out.writeObject(u);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } ;
    }
    public void readFile(String filename) {//  Läser in User-objekt från en fil som specifieras av Filnamn och lägger till dem i savedKontakter.
        File file = new File(filename);
        if(file.length() > 0) {
            try {
                ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)));
                int n = in.readInt();
                while(n > 0) {
                    savedContact((User)in.readObject());
                    n--;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public ArrayList<User> getContactsOnline() {
        return onlineKontakter;
    }
    //Returnerar en ArrayListen med onlinekontakter.
    public ArrayList<User> getContactsSaved() {
        return savedKontakter;
    }
    //Returnerar en ArrayListen med sparade kontakter.

    public User contactIndex(int index) {
        return onlineKontakter.get(index);
    }
    // Returnerar en användare från listan med onlinekontakter baserat på angivet index.
    public User indexSavedContact(int index) {
        return savedKontakter.get(index);
    }
}
//Returnerar en sparad kontakt baserat på angivet index.