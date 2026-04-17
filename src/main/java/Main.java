import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args) {
        Socket clientSocket = null;
        int port = 6379;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.printf("Listening to port %d%n", port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            while (true) {
              // Wait for connection from client.
              clientSocket = serverSocket.accept();
                try {
                    InputStream input = clientSocket.getInputStream();
                    int readBytesCount;
                    byte[] buffer = new byte[1024];
                    while ((readBytesCount = input.read(buffer)) != -1) {
                        String inputString = new String(buffer, 0, readBytesCount).trim();
                        System.out.printf("inputString is %s%n", inputString);
                        OutputStream outputStream = clientSocket.getOutputStream();
                        outputStream.write("+PONG\r\n".getBytes());
                        System.out.println("Wrote PONG");
                    }
                } catch (Exception e) {
                    System.out.println("Connection closed: " + e.getMessage());
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
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
