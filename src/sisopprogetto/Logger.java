package sisopprogetto;

import java.time.LocalDateTime;

public class Logger {
	
   public static void log(String message) {
   	System.out.println("[" + LocalDateTime.now() + "] ["
   		    + Thread.currentThread().getName() + "] " + message);    }
}
// http://localhost:8080/
// http://localhost:8080/ciao
// http://localhost:8080/qualsiasi
// http://localhost:8080/exit
