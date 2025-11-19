
import java.io.*;
import java.net.*;
import java.util.Random;

public class ClientBattito {
	private static final String SERVER_ADDRESS = "localhost";
	private static final int SERVER_PORT = 5000;

	public static void main(String[] args) {
		Random random = new Random();

	        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
	             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

	            System.out.println("Client Battito connesso al server di monitoraggio.");

	            // Simula invio dati per 15 volte
	            for (int i = 0; i < 15; i++) {
	                int battito = 40 + random.nextInt(80); // Valori 40–120 bpm
	                String messaggio = "battito:" + battito;

	                // Invia al server
	                out.println(messaggio);
	                System.out.println("Inviato: " + messaggio);

	                // Riceve risposta
	                String risposta = in.readLine();
	                if (risposta != null)
	                    System.out.println("Risposta server: " + risposta);

	                Thread.sleep(1000); // 1 secondo tra i campioni
	            }

	            System.out.println("Fine invio dati battito.");

	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
