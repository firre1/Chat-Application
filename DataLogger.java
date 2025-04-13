import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
/*
Denna klass är ansvarig för att hantera dataloggning till en fil.
Den använder PrintWriter för att skriva logginlägg till en fil.
Detta användas för att hålla en logg över händelser eller åtgärder
som utförs inom applikationen.
 */
public class DataLogger{
    String filename;

    public DataLogger(){
        this( "src/files/logger.txt" );
    }
    public DataLogger(String filename )
    {
        this.filename = filename;
    }

    //skapar konstruktor, en för filnamnet o en för txt filen.

    /*
    Denna metod loggar en given rad. Raden loggas tillsammans med den aktuella tidsstämpeln,
    och metoden ser till att huvudtråden inte blockeras vid skrivning till filen.
     */
    public void loggingText(String line ) {
        String time = LocalDateTime.now().withNano( 0 ).toString();
        String output = time + "\t " + line + "\n";
        new Thread( new DataLoggerWriter( filename, output ) ).start();
    }
    //används för att spara text i historik loggen, kollar även nuvarande tid o när den e klar skapar den en ny tråd som skickar filename o output som parametrar till skrivhistorik metoden
    // detta hindrar även att blockera huvud tråden då den skriver "Asynchrrounously"

    /*
    Denna metod loggar en meddelandehändelse. Den formaterar informationen
    om meddelandesändaren, mottagarna och meddelandets text och loggar sedan det.
     */
    public void MessageLogging(Message message){
        String username = message.getSender().getUsername();
        String[] recievers = new String[message.getReceivers().size()];
        for( int i = 0; i < recievers.length; i++ )
        {
            recievers[i] = message.getReceivers().get(i).getUsername();
        }
        String text = message.getText();
        String line = "Message from " + username + " to " + Arrays.toString( recievers ) + ": " + text;
        loggingText( line );
    }

    //metoderna ovan fixar så att historiken sparas i loggen, Lagrameddelande logggar en message event, lagradisconnect en connection event, lagradisconnect en disconnected event
    //de fixar även formaterade strängar som skickas t lagra metoden o sparar det i logggen.
    public static <T> T DefaultValue(T value, T valueDefault)
    {
        return value == null ? valueDefault : value;
    }

    public LocalDateTime localDate(String line){
        try
        {
            return LocalDateTime.parse( line.split( "\t" )[0] );
        }
        catch( Exception ignored ) { }

        return null;
    }
    /*
    Denna metod returnerar innehållet i loggen mellan två specifika tidsstämplar.
     */
    public ArrayList<String> Content(String lower, String upper){
        ArrayList<String> data = new ArrayList<String>( );
        LocalDateTime lowerDate = DefaultValue( localDate( lower ), LocalDateTime.MIN );
        LocalDateTime upperDate = DefaultValue( localDate( upper ), LocalDateTime.MAX );

        try
        {
            File file = new File(filename);
            BufferedReader br = new BufferedReader( new FileReader( file ) );
            String line;
            while( ( line = br.readLine( ) ) != null )
            {
                LocalDateTime date = localDate( line );
                if( ( date.isAfter( lowerDate ) || date.isEqual( lowerDate ) ) && ( date.isBefore( upperDate ) || date.isEqual( upperDate ) ) )
                {
                    data.add( line );
                }
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace( );
        }

        return data;
    }
    public ArrayList<String> Content()
    {
        return Content( null, null );
    }
    //metoden ovan hämtar det som är lagrat i historiken under en specifik tidpunkt, första metoden tar o letar efter tidstämplingen, letar lite i en log o lägger in i Data listan

    /*
    DateTransaction(), fileLastContent():
    Dessa metoder returnerar tidsstämpeln för den första respektive sista loggraden.
     */
    public LocalDateTime DateTransaction(){
        try
        {
            ArrayList<String> data = Content();
            String line = data.get( 0 ).split( "\t" )[0];
            return LocalDateTime.parse( line );
        }
        catch( Exception e ) { }

        return null;
    }
    public LocalDateTime fileLastContent(){
        try
        {
            ArrayList<String> data = Content();
            String line = data.get( data.size() - 1 ).split( "\t" )[0];
            return LocalDateTime.parse( line );
        }
        catch( Exception e ) { }

        return null;
    }
//metoderna ovan hämtar tidsstämpel för första o sista stämpeln i historik loggen, sedan kallas getfilecontact metoden o extraherar tidsstämpen o parsear in de i localdatetime.
    /*
    Denna metod skapar en instans av klassen DataLoggerGUI och visar GUI:n för att visa loggarna.
     */
    public void dataLoggerGUI(){
        DataLoggerGUI dataLoggerGUI = new DataLoggerGUI( this );
    }
    //skapar instans av historikGUI klassen kallar sedan createframe metoden av historikGUI instansen o visar sedan i interfacet.

    /*
    Den implementerar gränssnittet Runnable och är ansvarig för att skriva loggar till en fil.
    Den tar filnamnet och loggraden som argument i sin konstruktor.
    När dess tråd startas skapar metoden run() en ny fil om den inte redan finns,
    skriver loggraden till filen och stänger sedan utdatatströmmen.
     */
    private class DataLoggerWriter implements Runnable{
        String filename;
        String line;

        public DataLoggerWriter(String filename, String line){
            this.filename = filename;
            this.line = line;
        }

        @Override
        public void run(){
            try
            {
                File file = new File( filename );
                file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream( filename, true );
                outputStream.write( line.getBytes() );
                outputStream.close();
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }
}
//skapar en instans av historik o lagrar medelandet i lagrameddelande metoden, skapar sedan en gui interface för att visa historik loggen genom o kalla skapahistorikGUI
