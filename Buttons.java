/*
Buttons-klassen är en uppräkning (enum) som listar olika typer av knappar som
kan användas i applikationen. Uppräkningar är i princip klasser som har ett fast antal instanser,
och dessa instanser definieras inom själva klassen. Buttons-enumet inkluderar
Connect, Disconnect, Send, Contacts, SendContacts, AddContact och ContactsRemove.
Dessa instanser representerar olika funktioner som en knapp kan utföra i applikationen
och gör det lättare att hänvisa till dessa knappar på ett typsäkert sätt i resten av koden.
 */
public enum Buttons {
    Connect,
    Disconnect,
    Send,
    Contacts,
    SendContacts,
    AddContact,
    ContactsRemove
}