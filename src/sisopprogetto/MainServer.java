package sisopprogetto;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
	
  private static final int PORT = 8080;
  private static final int THREADS = 10;
  private static volatile boolean running = true;
  
  public static void main(String[] args) {
      System.out.println("Server avviato sulla porta " + PORT);
      ExecutorService pool = Executors.newFixedThreadPool(THREADS);
      
      try {
          ServerSocket serverSocket = new ServerSocket(PORT);
          while (running) {
              try {
                  Socket clientSocket = serverSocket.accept();
                  Logger.log("Nuova connessione: " + clientSocket.getInetAddress());
                  pool.execute(new ClientHandler(clientSocket, serverSocket, pool));
              } catch (IOException e) {
                  if (running) {
                      e.printStackTrace();
                  }
              }
          }
          System.out.println("Server terminato.");
          pool.shutdown();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
  // Metodo chiamato da /exit
  public static void stopServer(ServerSocket serverSocket, ExecutorService pool) {
      System.out.println("Shutdown richiesto via /exit...");
      running = false;
      try {
          serverSocket.close(); // sblocca accept()
      } catch (IOException e) {
          e.printStackTrace();
      }
      pool.shutdown();
  }
}
