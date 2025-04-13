import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
/*
Denna klass representerar en chattserver i en chattapplikation.
Den är ansvarig för att lyssna på inkommande klientanslutningar och
hantera kommunikationen mellan klienter.
Det gör detta genom att underhålla en ServerSocket för att lyssna på inkommande
anslutningar och använda en HashMap för att lagra anslutna klienter.
Den har också metoder för att hantera sändning och mottagning av meddelanden.
 */
public class Server implements Runnable {
    private int port; //porten där server lyssnar på anslutningar
    private HashMap<User, ClientHandler> clients; // En HashMap som lagrar referenser till alla klienter kopplade till servern. Nyckeln är användarobjektet och värdet är en instans av ClientHandler-klassen.
    private UnsentMessages unsent; //Ett objekt av typen UnsentMessages som används för att hantera olästa meddelanden som ska levereras till klienter.
    private DataLogger dataLogger;//Ett objekt av typen Historik som hanterar lagring av historikdata.
    private ArrayList<User> users; //En lista som lagrar referenser till alla användare som är anslutna till servern.
    private ConcurrentHashMap<String, ArrayList<Message>> unsentMessages = new ConcurrentHashMap<>(); // En ConcurrentHashMap som används för att hantera olästa meddelanden som ännu inte har levererats till mottagarna.

    public Server(int port) {
        this.port = port; // Portnumret där servern lyssnar efter anslutningar.
        this.clients = new HashMap<>(); //En HashMap som lagrar referenser till alla klienter som är anslutna till servern. Nyckeln är ett objekt av typen User och värdet är en instans av ClientHandler.
        this.users = new ArrayList<>();//En ArrayList som lagrar referenser till alla användare som är anslutna till servern.
        this.unsent = new UnsentMessages(); //En instans av klassen UnsentMessages, används för att hantera olästa meddelanden som ska levereras till klienterna.
        this.dataLogger = new DataLogger(); //En instans av klassen DataLogger, används för att hantera lagringen av historiska data.
        this.dataLogger.dataLoggerGUI();
        new Thread(this).start();
    }
    //Konstruktorn skapar en ny serverinstans och initierar variablerna. Den startar också en ny tråd för att köra servern.

    public synchronized void putClient(User user, ClientHandler client) {
        this.clients.put(user, client);
    }

    public synchronized ClientHandler getClient(User user) {
        return this.clients.get(user);
    }

    public synchronized boolean containsClientKey(User user) {
        return this.clients.containsKey(user);
    }

    public synchronized void removeClient(User user) {
        this.clients.remove(user);
    }
    //Metoderna putClient(), getClient(), containsClientKey() och removeClient()
// används för att lägga till, hämta, kontrollera och ta bort klienter från clients-hashmappen.
    @Override
    public void run() {
        System.out.println("Server startad");

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Server ansulten till klient");
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Metoden run() är huvudmetoden för servertråden. Den startar en serverlyssnarsockel och väntar på
// anslutningar från klienter. När en anslutning accepteras, skapas en ny instans av ClientHandler för att hantera kommunikationen med klienten.

    //Skickar User object till alla anslutna klienter
    public void broadcastUser(User user) throws IOException {
        for (User u: users) {
            getClient(u).getOos().writeObject(user);
        }
    }
    //Tar bort User från både "clients" map och users list
    public void removeUser(User user){
        removeClient(user);
        users.remove(user);
    }

    public class ClientHandler extends Thread {
        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private User user;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        }
        //Klassen ClientHandler är en inre klass som representerar en klienthanterare för varje anslutning. Den ansvarar för att hantera inkommande och utgående data för en specifik klient.
        // Vid anslutning skickas användarobjektet till servern och sprids till alla anslutna klienter.
        // Den kontrollerar också om det finns olästa meddelanden för den anslutna användaren och skickar dem vid behov.
        // I en oändlig loop läser den inkommande data och skickar utgående data till respektive klient.
        // Om en användare kopplar från tas den bort från servern och meddelas till alla anslutna klienter.

        public ObjectOutputStream getOos() {
            return oos;
        }
        //Denna metod returnerar en referens till ObjectOutputStream för den aktuella klienten. Det används för att skicka utgående data till klienten.

        @Override
        public void run() {
            try {
                user = (User) ois.readObject();
                broadcastUser(user);
                checkNewMessages(user);
                unsent.clearMessages();
                putClient(user, this);
                users.add(user);
                for (User u : users) {
                    oos.writeObject(u);
                }
                // Logga när en klient ansluts
                String username = user.getUsername();
                String address = socket.getInetAddress().toString();
                String connectLog = username + " (" + address + ") connected to the server.";
                dataLogger.loggingText(connectLog);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                while (true) {
                    Object obj = ois.readObject();
                    if (obj instanceof User) {
                        if (((User) obj).isConnected()) {
                            putClient((User) obj, this);
                            users.add((User) obj);
                            for (User u : users) {
                                oos.writeObject(u);
                            }
                        } else {
                            removeUser((User) obj);
                            broadcastUser((User) obj);
                            break;
                        }
                    } else if (obj instanceof Message) {
                        ((Message) obj).setReceived();
                        deliverMessage((Message) obj);
                    }
                }
                close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            // Logga när en klient nerkopplas
            String disconnectLog = user.getUsername() + " (" + socket.getInetAddress().toString() + ") disconnected from the server.";
            dataLogger.loggingText(disconnectLog);
            System.out.println("Klient nerkopplad");
        }

        //Detta är trådmetoden som körs när en klient ansluter till servern. Metoden ansvarar för att hantera inkommande och utgående data för klienten.
        // Vid anslutning läses användarobjektet från ObjectInputStream och sprids till alla anslutna klienter genom att anropa metoden broadcastUser().
        // Metoden checkNewMessages() används för att kontrollera om det finns olästa meddelanden för den anslutna användaren och skickar dem vid behov.
        // Användaren läggs också till i listorna clients och users. Därefter skickas användarobjekten för alla anslutna användare till den nya klienten.
        // I en oändlig loop läser metoden inkommande objekt och kontrollerar om det är en användarobjekt eller ett meddelande.
        // Beroende på objekttypen uppdateras listorna över klienter och användare eller ett meddelande levereras till mottagarna genom att anropa deliverMessage().
        // Om en användare kopplar från tas den bort från listorna och detta meddelas till alla anslutna klienter genom att anropa removeUser() och broadcastUser().
        // Till sist stängs strömmar och anslutningar genom att anropa close().

        private void close() {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(socket != null) {
                    socket.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Metoden close() stänger strömmar och anslutningar för en klient.
        private void checkNewMessages(User user) {
            ArrayList<Message> messages = unsent.get(user.getUsername());

            if (messages != null) {
                for (Message message : messages) {
                    try {
                        Message msg = new Message(message.getSender(), message.getReceivers(),"Oläst meddelande : " + message.getText(), message.getImage());
                        msg.setDelivered();
                        oos.writeObject(msg);
                        dataLogger.MessageLogging(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void deliverMessage(Message message) {
            for (User receiver : message.getReceivers()) {
                for (User u : users) {
                    if (u.equals(receiver)) {
                        try {
                            getClient(u).getOos().writeObject(message);
                            message.setDelivered();
                            dataLogger.MessageLogging(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        unsent.put(receiver.getUsername(), message);
                    }
                }
            }
        }
    }
}
//metoden som skapar en ny instans av servern och startar den på den angivna porten.
