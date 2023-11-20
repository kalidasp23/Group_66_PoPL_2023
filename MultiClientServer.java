import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiClientServer {

    private static Map<Socket, Long> clientStartTimes = new HashMap<>();

    public static void main(String[] args) {
        int portNumber = 5555;
        ExecutorService executor = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server is running and listening on port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clientStartTimes.put(clientSocket, System.currentTimeMillis());
                executor.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final String clientId;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            this.clientId = getClientId(clientSocket);
        }

        @Override
        public void run() {
            try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                // Simulate some processing
                Thread.sleep(1000);

                synchronized (System.out) {
                    out.println("Server response");

                    long endTime = System.currentTimeMillis();
                    long responseTime = endTime - clientStartTimes.get(clientSocket);

                    System.out.println("Response time for client " + clientId + " is " + responseTime + " ms");

                    // Print memory utilization
                    printMemoryUtilization();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    clientStartTimes.remove(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String getClientId(Socket socket) {
            InetAddress address = socket.getInetAddress();
            int port = socket.getPort();
            return address.getHostAddress() + ":" + port;
        }

        private void printMemoryUtilization() {
            Runtime runtime = Runtime.getRuntime();
            long freeMemory = runtime.freeMemory();
            long totalMemory = runtime.totalMemory();
            long maxMemory = runtime.maxMemory();

            System.out.println("Memory Utilization:");
            System.out.println("Free Memory: " + (freeMemory / (1024 * 1024)) + " MB");
            System.out.println("Total Memory: " + (totalMemory / (1024 * 1024)) + " MB");
            System.out.println("Max Memory: " + (maxMemory / (1024 * 1024)) + " MB\n");
        }
    }
}

