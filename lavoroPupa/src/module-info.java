/**
 * 
 */
/**
 * 
 */
module lavoroPupa {
	import java.io.*;
	import java.net.*;
	import java.util.Random;

	public class ClientTemperatura {
	    public static void main(String[] args) {
	        String host = "127.0.0.1";
	        int port = 5000;
	        Random rand = new Random();

	        try {
	            while (true) {
	                double temperatura = 34.0 + rand.nextDouble() * 6.0; // 34–40°C
	                try (Socket socket = new Socket(host, port);
	                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

	                    String messaggio = "temperatura:" + String.format("%.1f", temperatura);
	                    out.println(messaggio);
	                    String risposta = in.readLine();
	                    System.out.println("[TEMP] Inviato: " + messaggio + " → Risposta: " + risposta);
	                }
	                Thread.sleep(4000);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

}