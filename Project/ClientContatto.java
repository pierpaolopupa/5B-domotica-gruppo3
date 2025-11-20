import java.io.*;
import java.net.*;
import java.util.Random;

public class ClientContatto {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 5000;
        Random rand = new Random();

        try {
            while (true) {
                boolean contatto = rand.nextInt(10) != 0; // 10% di possibilità di contatto perso
                try (Socket socket = new Socket(host, port);
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    String messaggio = "contatto:" + contatto;
                    out.println(messaggio);
                    String risposta = in.readLine();
                    System.out.println("[CONTATTO] Inviato: " + messaggio + " → Risposta: " + risposta);
                }
                Thread.sleep(4000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


