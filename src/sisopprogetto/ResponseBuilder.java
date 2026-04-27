package sisopprogetto;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
	
  private static final Map<String, String> routes = new HashMap<>();
  
  static {
      routes.put("/", "src/webserver/index.html");
      routes.put("/ciao", "src/webserver/ciao.html");
  }
  
  public static String buildResponse(RequestParser request) {
      if (request.getPath() == null) {
          return httpResponse("400 Bad Request", "<h1>400 Bad Request</h1>");
      }
      String filePath = routes.get(request.getPath());
      if (filePath == null) {
          return httpResponse("404 Not Found", loadFile("src/webserver/404.html"));
      }
      String body = loadFile(filePath);
      return httpResponse("200 OK", body);
  }
  
  private static String loadFile(String path) {
      try {
          return Files.readString(Paths.get(path));
      } catch (IOException e) {
          return "<h1>Errore nel caricamento file</h1>";
      }
  }
  
  private static String httpResponse(String status, String body) {
      byte[] bytes = body.getBytes();
      return "HTTP/1.1 " + status + "\r\n" +
             "Content-Type: text/html\r\n" +
             "Content-Length: " + bytes.length + "\r\n" +
             "\r\n" +
             body;
  }
}
