import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
/*
Denna klass representerar en chattklient i en chattapplikation. Den är ansvarig för
 att etablera en anslutning till servern, skicka meddelanden till servern och ta emot
 meddelanden från servern. Det gör detta genom att underhålla en Socket för att
 kommunicera med servern och använda ObjectOutputStream och ObjectInputStream för
 att skicka och ta emot Message-objekt.
 */
public class Client {
    private ClientGUI gui; //for user interface
    private Loggin loggin; //login relaterade funktioner
    private Socket socket; //TCP socket anslutning
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean running; //kolla ifall servern är igång
    private UserContacts contacts; //hantera kontakter
    private ArrayList<User> mottagareLista = new ArrayList<>(); //spara ned mottagare osv av mottagarlista

    public Client() { //konstruktor för att initialisera loggin objekt
        new Server(3000);
        loggin = new Loggin(this);
        /*
            Window listener sätts till loggin fönstret.
            När fönstret stängs ner kollas det om det har anslutit sig.
            e, skapade WindowEvent efter att ha stängt fönstret.
         */
        loggin.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (loggin.connect()) {
                    contacts = new UserContacts();
                    gui = new ClientGUI(Client.this);
                    button(Buttons.Connect);
                }
            }
        });
    }

    public void newConnection(){ //finns för att skapa en ny anslutning, skapar en ny loggin objekt och ifall en ny anslutning sker o ifall anslutningen sker så kallas kontakter o button pressed
        loggin = new Loggin(this);
        if (loggin.connect()){
            contacts = new UserContacts();
            button(Buttons.Connect);
        }
    }
    /*
    Denna metod hanterar olika åtgärder baserat på vilken knapp som klickas.
    Varje knappåtgärd utför olika uppgifter, som att ansluta/frånkoppla från servern,
    skicka ett meddelande, uppdatera kontaktlistor och mer.
     */

    public void button(Buttons button) { //meotden är kallad när en knapp är tryckt i GUIT, den ansvarar då för olika funktioner för olika knapp tryck detta genom en switch sats, varje knapptryck har en unik funktion osv
        switch (button) {
            case Connect:
                connect();
                contacts.readFile("src/files/kontakter.dat");
                break;
            case Disconnect:
                contacts.addToFile("src/files/kontakter.dat");
                disconnect();
                break;
            case Send:
                SendMessage(getMessageFromViewer());
                for (int i = 0; i < mottagareLista.size(); i++) {
                    mottagareLista.remove(i);
                }
                break;
            case Contacts:
                String[] onlineUsers = getUsernames(contacts.getContactsOnline());
                String[] savedContacts = getUsernames(contacts.getContactsSaved());
                gui.setOnlineUserList(onlineUsers);
                gui.setSavedContactList(savedContacts);
                break;
            case SendContacts:
                UpdateList();
                break;
            case AddContact:
                int index = gui.getListIndex();
                User u = contacts.contactIndex(index);
                contacts.savedContact(u);
                break;
            case ContactsRemove:
                int i = gui.getContactListIndex();
                contacts.removeContact(i);
        }

    }
    /*
    Denna metod etablerar en anslutning till servern,
    skapar en ny Socket med hjälp av ip adress och porten från loggin-objektet,
    initierar in- och utdatatrömmar, sätter användaren som ansluten,
    skickar användarinformationen till servern och startar en ny tråd för att
    lyssna på inkommande meddelanden från servern.
     */
    private void connect() { //fixar en anslutning till servern, får ip och port från loggin objektet och skapar en TCP socket anslutning och initialiserar en input/output stream för kommunikation
        //hämtar info om user från GUI o lägger användaren som ansluten sedan skickas detta till servern o sedan startas en ny tråd för att lyssna på meddelanden från servern
        String host = loggin.getServerip();
        int port = loggin.getPort();
        System.out.println(port + " " + host);
        try {
            socket = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = getUserFromViewer();
        user.setConnected(true);
        UserServer(user);
        new ServerListener().start();
    }
    /*
    Denna metod hanterar frånkopplingen från servern.
    Den sätter running till false, uppdaterar användarens anslutningsstatus,
    skickar den uppdaterade användarinformationen till servern,
    återställer GUI:n och stänger in- och utdatatrömmar samt socketen.
     */
    private void disconnect() { //metoden hanterar disconnect funktionen o lägger boolean running till false så att tråden slutar lyssna, uppdaterar användarens connection status o skickar de uppdaterade
        //användarna user object till servern, resetar GUI o clearar chatboxen, stänger även objektoutput o input streams samt socket.
        setRunning(false);
        User user = getUserFromViewer();
        user.setConnected(false);
        UserServer(user);
        gui.resetGUI();
        gui.getTaChatbox().setText("");
        gui.getTaChatbox().append("Du har lämnat chatten\n");
    }


    public void changeIndication(int index) {
    }
    /*
    getUserFromViewer() och getMessageFromViewer():
    Dessa metoder hämtar användarinformation och meddelanden från GUI:n.
     */
    private User getUserFromViewer() {
        String username = loggin.getUsername();
        ImageIcon profilePicture = loggin.getPfp();
        User user = new User(username, profilePicture);
        return user;
    }
    private Message getMessageFromViewer() {
        Message message = null;
        String text = gui.getTfMessage().getText();
        ImageIcon icon = gui.getMessageImage();
        User user = getUserFromViewer();
        if(mottagareLista.isEmpty()) {
            message = new Message(user, contacts.getContactsOnline(), text, icon);
        } else {
            ArrayList<User> newReceiverList = new ArrayList<>(mottagareLista);
            message = new Message(user, newReceiverList, text, icon);
            gui.getLblSendTo().setText("Send to: ");
            gui.resetLabel();
        }
        return message;
    }
    /*
     UserServer(User user) och SendMessage(Message message):
     Dessa metoder skriver användarinformation och meddelanden
     till utdatatströmmen för att skicka dem till servern.
     */
    private void UserServer(User user) {
        try {
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void SendMessage(Message message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    Denna metod uppdaterar listan över mottagare av meddelanden baserat på den markerade kontakten.
     */
    private void UpdateList() {
        int index = gui.getListIndex();
        if(index > -1) {
            User receiver = contacts.contactIndex(index);
            gui.setTextForLabel(receiver.getUsername());
            mottagareLista.add(receiver);
        } else {
            int contactIndex = gui.getContactListIndex();
            User receiver = contacts.indexSavedContact(contactIndex);
            gui.setTextForLabel(receiver.getUsername());
            mottagareLista.add(receiver);
        }
    }

//Metoderna ovanför hanterar olika saker, till mesta dels relaterade till user interface och message hantering
    // ContactListIndicatesChanged() kollar ifall det har skett nån förändring, GetUsersFromView() hämtar user information (namn o profilbild)
    //från loggin objekt o skapar en user instans av detta. GetMessageFromView() hämtar message, text o icon samt userinfo från GUI o skapar en message instans
    //sendusertoserver o sendmessage skickar user o mesage objekt till servern genom objectoutputstream
    //uppdateReceiverList uppdaterar kontakter o liknand.

    public void setRunning(boolean running) {
        this.running = running;
    }
    public boolean getRunning(){
        return running;
    }
    /*
    Denna metod returnerar en array av användarnamn från en given lista med User-objekt.
     */
    private String[] getUsernames(ArrayList<User> users) {
        String[] username = new String[users.size()];
        for(int i = 0; i < users.size(); i++) {
            username[i] = users.get(i).getUsername();
        }
        return username;
    }
    //Utillity metoder som sätter setrunning variabel för att kontrollera serverns running state, getusernames hämtar en array av usernames från en lista av user objects.
    /*
     Denna inre klass utökar Thread och lyssnar på inkommande objekt från servern.
     Den uppdaterar kontaktförteckningen och GUI:n baserat på om det
     mottagna objektet är en User eller ett Message.
     */
    private class ServerListener extends Thread {

        public void run() {
            running = true;
            while (running) {
                try {
                    Object obj = in.readObject();
                    if (obj instanceof User) {
                        if (((User) obj).isConnected()) {
                            contacts.Online((User) obj);
                            gui.displayUser(ImageGUI.createUserLabel(((User) obj).getImage(), ((User) obj).getUsername()));
                        } else {
                            contacts.removeContactOnline((User) obj);
                            gui.removeUser(ImageGUI.createUserLabel(((User) obj).getImage(), ((User) obj).getUsername()));
                        }
                    } else if (obj instanceof Message) {
                        Message message = (Message) obj;
                        System.out.println(message.getText());
                        if (message.getImage() == null) {
                            gui.getTaChatbox().append(
                                    message.getSender().getUsername() + ": " + message.getText() + '(' + message.getDelivered() + ')' + "\n");
                        } else {
                            gui.displayImage(message.getSender().getUsername(), message.getText(), message.getImage());
                        }
                    }
                } catch (EOFException e) {
                    running = false;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //inner klass listentoserver som extends thread o ansvarar för att lyssna på kommande meddanden från servern, den kör en separat tråd o letar utan stopp för något av objekten från input stream
    //om ett objekt kommer fram exempelvis Message så visar den meddelandet i GUIT o ifall de är en arraylist av users så uppdaterar den online contacts list i kontakter klassen, ifall nått går snett så sätts running till false
    //tråden stoppas i sådanafall.
}
//startas en client metod som startar applikationen.
