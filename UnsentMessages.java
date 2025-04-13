import java.util.ArrayList;
import java.util.HashMap;

/*
Denna klass hanterar en lista över oskickade Message-objekt.
Detta kan vara användbart om klienten förlorar sin anslutning till servern eller om
ett meddelande misslyckas med att skickas av någon annan anledning.
Dessa meddelanden kan sedan försökas skickas igen eller hanteras på annat sätt.
 */
public class UnsentMessages {
    private HashMap<String, ArrayList<Message>> unsent = new HashMap<>();//Detta är det privata fältet som håller den hashmapp som innehåller användarnamn som nycklar och listor över olästa meddelanden som värden.

    //Unsent: En HashMap där nyckeln är en sträng som representerar en användares användarnamn och värdet är en ArrayList av Message-objekt som
    // representerar meddelandena som ännu inte har levererats till den användaren.
    public synchronized void put(String username, Message message) {
        if(get(username) == null) {
            unsent.put(username, new ArrayList<Message>());
        }
        get(username).add(message);
    }
    //Denna metod används för att lägga till ett oläst meddelande för en specifik användare. Om användarnamnet inte finns i hashmappen skapas en ny lista för att lagra meddelandena.
    // Därefter läggs meddelandet till i listan för det givna användarnamnet.

    public synchronized void clearMessages() {
        unsent.clear();
    }
//enna metod används för att rensa alla olästa meddelanden. Den tömmer helt enkelt hashmappen.

    public synchronized ArrayList<Message> get(String username) {
        return unsent.get(username);
    }
}
//Denna metod används för att hämta listan över olästa meddelanden för en specifik användare. Den tar emot användarnamnet som parameter och returnerar motsvarande lista av meddelanden från hashmappen.

//Alla metoder här är deklarerade som synchronized och detta för att man enkelt skall kunna modifiera hash-mappen samt så att
//så att krockar inte sker av trådar där hash mappen används av flera trådar samtidigt.