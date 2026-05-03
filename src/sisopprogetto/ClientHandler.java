package sisopprogetto;

import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class ClientHandler implements Runnable {
	
  private Socket socket;
  private ServerSocket serverSocket;
  private ExecutorService pool;
  
  public ClientHandler(Socket socket, ServerSocket serverSocket, ExecutorService pool) {
      this.socket = socket;
      this.serverSocket = serverSocket;
      this.pool = pool;
  }
  
  @Override
  public void run() {
      try (
          Socket s = socket;
          BufferedReader in = new BufferedReader(
              new InputStreamReader(s.getInputStream()));
          BufferedWriter out = new BufferedWriter(
              new OutputStreamWriter(s.getOutputStream()))
      ) {
          String requestLine = in.readLine();
          Logger.log("Richiesta: " + requestLine);
         
          try {
       	    Thread.sleep(5000); // simula una richiesta lenta (5 secondi)
       	} catch (InterruptedException e) {
       	    e.printStackTrace();
       	}
          //  comando di shutdown
          if (requestLine != null && requestLine.contains("GET /exit")) {
              String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/html\r\n\r\n" +
                                "<h1>Server in chiusura...</h1>";
              out.write(response);
              out.flush();
              MainServer.stopServer(serverSocket, pool);
              return;
          }
          RequestParser parser = new RequestParser(requestLine);
          String response = ResponseBuilder.buildResponse(parser);
          out.write(response);
          out.flush();
      } catch (IOException e) {
          Logger.log("Errore: " + e.getMessage());
      }
  }
}
