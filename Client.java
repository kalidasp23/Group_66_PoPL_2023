import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "localhost"; // Change this to the IP address or hostname of your server
        int portNumber = 5555;
        int numClients = 5; // Adjust the number of clients

        for (int i = 0; i < numClients; i++) {
            // Create and start each client in a separate thread
            new Thread(() -> runClient(serverAddress, portNumber)).start();
        }
    }

    private static void runClient(String serverAddress, int portNumber) {
        try (
            Socket socket = new Socket(serverAddress, portNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            // Send a request to the server
            out.println("Hello, Server!");

            // Receive and print the server's response
            String response = in.readLine();
            System.out.println("Server Response: " + response);

            // Print memory utilization
            printMemoryUtilization();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printMemoryUtilization() {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        long maxMemory = runtime.maxMemory();


        System.out.println("Free Memory: " + (freeMemory / (1024 * 1024)) + " MB");
        System.out.println("Total Memory: " + (totalMemory / (1024 * 1024)) + " MB");
        System.out.println("Max Memory: " + (maxMemory / (1024 * 1024)) + " MB");
    }
}
