import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 6379;
        try {
            serverSocket = new ServerSocket(port);
            System.out.printf("Listening to port %d%n", port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            while (true) {
              // Wait for connection from client.
              clientSocket = serverSocket.accept();
              RedisThread redisThread = new RedisThread(clientSocket);
              Thread thread = new Thread(redisThread);
              System.out.println("Conection accepted");

              thread.start();
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
