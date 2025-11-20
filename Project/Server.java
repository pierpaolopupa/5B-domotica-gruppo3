package lavoropupa;

public class Server {

	 private static final int PORT = 5000;
    private static Map<String, String> datiSensori = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Server monitoraggio pazienti in esecuzione sulla porta " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new GestoreClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class GestoreClient implements Runnable {
        private Socket socket;

        public GestoreClient(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String messaggio = in.readLine();
                if (messaggio != null) {
                    System.out.println("Ricevuto: " + messaggio);
                    String[] parti = messaggio.split(":");
                    if (parti.length == 2) {
                        String tipo = parti[0];
                        String valore = parti[1];
                        datiSensori.put(tipo, valore);
                        String risposta = analizzaDati(tipo, valore);
                        out.println(risposta);
                    } else {
                        out.println("Formato non valido. Usa tipo:valore");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String analizzaDati(String tipo, String valore) {
            String allarme = "OK";
            try {
                switch (tipo) {
                    case "temperatura":
                        double temp = Double.parseDouble(valore);
                        if (temp < 35.0 || temp > 38.0)
                            allarme = "Temperatura anomala: " + temp + "°C";
                        break;
                    case "movimento":
                        int mov = Integer.parseInt(valore);
                        if (mov < 5)
                            allarme = "Nessun movimento rilevato";
                        break;
                    case "contatto":
                        boolean contatto = Boolean.parseBoolean(valore);
                        if (!contatto)
                            allarme = "Contatto perso con il paziente";
                        break;
                }
            } catch (Exception e) {
                allarme = "Errore analisi dati";
            }

            System.out.println("Stato sensori: " + datiSensori);
            return allarme;
        }
    }
}