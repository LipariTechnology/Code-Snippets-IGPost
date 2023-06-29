public class Server {
    public static void main(String[] args){
        final int port = 3000;

        try {
            //Creazione del server socket 
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server in ascolto sulla porta " + port);

            //Ciclo infinito per l'attesa di connessioni
            while(true){
                //accettazione la connessione
                Socket client = server.accept();
                System.out.println("Connessione accettata da " + client.getInetAddress());

                //Creazione di un nuovo thread per gestire la connessione
                Thread clientThread = new Thread(()=>{
                    try {
                        //creazione di un buffer per la ricezione del messaggio
                        BufferReader input = new BufferReader(new InputStreamReader(client.getInputStream()));
                        PrintWriter output = new PrintWriter(client.getOutputStream(), true);

                        String inputLine;

                        //ciclo di lettura del messaggio
                        while((inputLine = input.readLine()) != null){
                            System.out.println("Messaggio ricevuto: " + inputLine);
                            output.println("Risposta dal server: Messaggio ricevuto");
                        }

                        //chiusura degli input 
                        input.close();
                        output.close();
                        client.close();
                        System.out.println("Connessione con " + client.getInetAddress() + " chiusa");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                clientThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}