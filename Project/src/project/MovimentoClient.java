package project;

import java.io.*;
import java.net.*;
import java.util.Random;

public class MovimentoClient {

} 

public class MultiClient {
  String nomeServer ="localhost";                  // indirizzo del server che poi è la nostra macchina
  int portaServer = 80;                            // porta x servizio data e ora
  Socket miosocket;                                 
  BufferedReader tastiera;                         // input da tastiera
  String stringaUtente;                            // stringa inserita da utente
  String stringaRicevutaDalServer;                 // stringa ricevuta dal server
  DataOutputStream outVersoServer;                 // stream di output
  BufferedReader inDalServer;                      // stream di input 

  public void comunica() {
    for (;;)                                     // ciclo infinito: termina con END
    try{
      System.out.println("Movimenti anomali recenti");
      stringaUtente = tastiera.readLine();
      //la spedisco al server 
      System.out.println("invio movimenti al server");
      outVersoServer.writeBytes( stringaUtente+'\n');
      //leggo la risposta dal server 
      stringaRicevutaDalServer=inDalServer.readLine();
      System.out.println("Risposta"+'\n'+stringaRicevutaDalServer );
      if  (stringaUtente.equals("END")) { 
        System.out.println("8 CLIENT: termina elaborazione e chiude connessione" );
        miosocket.close();                             // chiusura
        break; 
      }
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
      System.out.println("Errore durante la comunicazione col server!");
      System.exit(1);
    }
  }
  
  public Socket connetti(){
    System.out.println("2 CLIENT partito in esecuzione ...");
    try{
      // input da tastiera
      tastiera = new BufferedReader(new InputStreamReader(System.in));
      //  miosocket = new Socket(InetAddress.getLocalHost(), 6789);
      miosocket = new Socket(nomeServer,portaServer);
      // associo due oggetti al socket per effettuare la scrittura e la lettura 
      outVersoServer = new DataOutputStream(miosocket.getOutputStream());
      inDalServer    = new BufferedReader(new InputStreamReader (miosocket.getInputStream()));
    } 
    catch (UnknownHostException e){
      System.err.println("Host sconosciuto"); } 
    catch (Exception e){
      System.out.println(e.getMessage());
      System.out.println("Errore durante la connessione!");
      System.exit(1);
    }
    return miosocket;
  }

  public static void main(String args[]) {
    MultiClient cliente = new MultiClient();
    cliente.connetti();
    cliente.comunica();
  }   
}