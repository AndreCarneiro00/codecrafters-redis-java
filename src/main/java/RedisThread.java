import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;


public class RedisThread implements Runnable {
    Socket clientSocket;

    public RedisThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
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
}