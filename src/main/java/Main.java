import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args){
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    int port = 6379;
    try {
      serverSocket = new ServerSocket(port);
      System.out.printf("Listening to port %d%n", port);
      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);

      // Wait for connection from client.
      clientSocket = serverSocket.accept();
      System.out.println("Conection accepted");
      while (clientSocket.isConnected()) {
        OutputStream outputStream = clientSocket.getOutputStream();

        String output = "+PONG\r\n";
        outputStream.write(output.getBytes());
        System.out.println("Wrote PONG");
      }


    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    } finally {
      try {
        if (clientSocket != null) {
          clientSocket.close();
        }
      } catch (IOException e) {
        System.out.println("IOException: " + e.getMessage());
      }
    }
  }
}
