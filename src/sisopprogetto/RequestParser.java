package sisopprogetto;

public class RequestParser {
	
   private String method;
   private String path;
   public RequestParser(String requestLine) {
       parse(requestLine);
   }
   private void parse(String requestLine) {
       if (requestLine == null || requestLine.isEmpty()) return;
       String[] parts = requestLine.split(" ");
       if (parts.length >= 2) {
           method = parts[0];
           path = parts[1];
       }
   }
   public String getMethod() {
       return method;
   }
   public String getPath() {
       return path;
   }
}